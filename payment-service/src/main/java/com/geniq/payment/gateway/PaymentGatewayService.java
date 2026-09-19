package com.geniq.payment.gateway;

import com.geniq.payment.config.PaymentProperties;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import com.stripe.StripeClient;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Talks to the configured payment gateway (Razorpay or Stripe) using TEST
 * credentials. Creates orders/intents and verifies payment signatures.
 * Secrets never leave this service.
 */
@Service
public class PaymentGatewayService {

    private static final Logger log = LoggerFactory.getLogger(PaymentGatewayService.class);

    private final PaymentProperties props;

    public PaymentGatewayService(PaymentProperties props) {
        this.props = props;
    }

    public String provider() {
        return props.getProvider();
    }

    /**
     * Creates an order/intent with the active gateway.
     *
     * @param amount   amount in the smallest currency unit (paise / cents)
     * @param currency ISO currency code (e.g. INR, USD)
     * @param receipt  merchant-side reference for reconciliation
     */
    public GatewayOrder createOrder(long amount, String currency, String receipt) {
        String provider = props.getProvider();
        try {
            switch (provider.toLowerCase()) {
                case "razorpay":
                    return createRazorpayOrder(amount, currency, receipt);
                case "stripe":
                    return createStripeIntent(amount, currency, receipt);
                default:
                    // mock — no external call, useful for local dev without keys.
                    return new GatewayOrder("mock", "mock_" + receipt, amount, currency, null, null);
            }
        } catch (Exception e) {
            log.error("Gateway order creation failed for provider {}: {}", provider, e.getMessage());
            throw new GatewayException("Unable to create payment order with " + provider, e);
        }
    }

    private GatewayOrder createRazorpayOrder(long amount, String currency, String receipt) throws Exception {
        RazorpayClient client = new RazorpayClient(
                props.getRazorpay().getKeyId(), props.getRazorpay().getKeySecret());
        JSONObject request = new JSONObject();
        request.put("amount", amount);
        request.put("currency", currency);
        request.put("receipt", receipt);
        com.razorpay.Order order = client.orders.create(request);
        String orderId = order.get("id");
        return new GatewayOrder("razorpay", orderId, amount, currency,
                props.getRazorpay().getKeyId(), null);
    }

    private GatewayOrder createStripeIntent(long amount, String currency, String receipt) throws Exception {
        StripeClient client = new StripeClient(props.getStripe().getSecretKey());
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency.toLowerCase())
                .putMetadata("receipt", receipt)
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                .setEnabled(true).build())
                .build();
        PaymentIntent intent = client.paymentIntents().create(params);
        return new GatewayOrder("stripe", intent.getId(), amount, currency,
                props.getStripe().getPublishableKey(), intent.getClientSecret());
    }

    /**
     * Verifies the payment signature returned by the gateway after the user
     * completes checkout in the browser. Returns true when authentic.
     */
    public boolean verifyRazorpaySignature(String orderId, String paymentId, String signature) {
        try {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("razorpay_order_id", orderId);
            attributes.put("razorpay_payment_id", paymentId);
            attributes.put("razorpay_signature", signature);
            JSONObject options = new JSONObject(attributes);
            return Utils.verifyPaymentSignature(options, props.getRazorpay().getKeySecret());
        } catch (Exception e) {
            log.warn("Razorpay signature verification failed: {}", e.getMessage());
            return false;
        }
    }

    public static class GatewayException extends RuntimeException {
        public GatewayException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
