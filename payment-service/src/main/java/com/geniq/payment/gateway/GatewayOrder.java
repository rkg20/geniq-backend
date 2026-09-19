package com.geniq.payment.gateway;

/**
 * Result of creating an order/intent with a payment gateway. Only the fields
 * safe to expose to the browser are included (never the secret key).
 */
public class GatewayOrder {
    public String provider;
    public String gatewayOrderId;
    public long amount;      // smallest currency unit (paise / cents)
    public String currency;
    public String publicKey; // Razorpay key_id or Stripe publishable key
    public String clientSecret; // Stripe PaymentIntent client secret (null for Razorpay)

    public GatewayOrder(String provider, String gatewayOrderId, long amount, String currency,
                        String publicKey, String clientSecret) {
        this.provider = provider;
        this.gatewayOrderId = gatewayOrderId;
        this.amount = amount;
        this.currency = currency;
        this.publicKey = publicKey;
        this.clientSecret = clientSecret;
    }
}
