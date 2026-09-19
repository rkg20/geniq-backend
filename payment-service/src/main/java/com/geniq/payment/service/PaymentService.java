package com.geniq.payment.service;

import com.geniq.payment.config.PaymentProperties;
import com.geniq.payment.dto.CheckoutRequest;
import com.geniq.payment.gateway.GatewayOrder;
import com.geniq.payment.gateway.PaymentGatewayService;
import com.geniq.payment.model.Payment;
import com.geniq.payment.model.PaymentStatus;
import com.geniq.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayService gateway;
    private final PaymentProperties props;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentGatewayService gateway,
                          PaymentProperties props) {
        this.paymentRepository = paymentRepository;
        this.gateway = gateway;
        this.props = props;
    }

    /**
     * Persists the payment record and creates an order with the configured
     * gateway (Razorpay/Stripe test mode). Returns the stored payment enriched
     * with the gateway order details the browser needs to open checkout.
     */
    public CheckoutResult createCheckout(CheckoutRequest req) {
        long amount = toSmallestUnit(req.price);
        String currency = props.getCurrency();

        Payment payment = new Payment();
        String reference = "pay_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        payment.setReference(reference);
        payment.setPlanId(req.plan);
        payment.setPrice(req.price);
        payment.setCustomerName(req.name);
        payment.setCustomerEmail(req.email);
        payment.setCustomerPhone(req.phone);
        payment.setCustomerRole(req.role);
        payment.setAgeGroup(req.ageGroup);
        payment.setAmount(amount);
        payment.setCurrency(currency);
        payment.setStatus(PaymentStatus.CREATED);

        GatewayOrder order = gateway.createOrder(amount, currency, reference);
        payment.setProvider(order.provider);
        payment.setGatewayOrderId(order.gatewayOrderId);

        Payment saved = paymentRepository.save(payment);
        return new CheckoutResult(saved, order);
    }

    public Payment getByReference(String reference) {
        return paymentRepository.findById(reference).orElse(null);
    }

    /**
     * Verifies the Razorpay signature and, when valid, marks the payment paid.
     * Returns the updated payment, or null when not found / verification fails.
     */
    public Payment confirmRazorpay(String reference, String razorpayOrderId,
                                   String razorpayPaymentId, String razorpaySignature) {
        Payment payment = paymentRepository.findById(reference).orElse(null);
        if (payment == null) return null;

        boolean valid = gateway.verifyRazorpaySignature(
                razorpayOrderId, razorpayPaymentId, razorpaySignature);
        if (!valid) {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setUpdatedAt(Instant.now());
            paymentRepository.save(payment);
            return null;
        }

        payment.setGatewayPaymentId(razorpayPaymentId);
        payment.setStatus(PaymentStatus.PAID);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    /**
     * Marks a payment as paid without provider verification. Used by the
     * simulated webhook / mock provider only.
     */
    public Payment markPaid(String reference) {
        Payment payment = paymentRepository.findById(reference).orElse(null);
        if (payment == null) return null;
        payment.setStatus(PaymentStatus.PAID);
        payment.setUpdatedAt(Instant.now());
        return paymentRepository.save(payment);
    }

    /**
     * Converts a display price such as "₹8,999" to the smallest currency unit
     * (paise). Falls back to 0 when no digits are present.
     */
    private long toSmallestUnit(String price) {
        if (price == null) return 0L;
        String digits = price.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) return 0L;
        return Long.parseLong(digits) * 100L;
    }
}
