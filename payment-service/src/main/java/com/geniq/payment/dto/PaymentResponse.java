package com.geniq.payment.dto;

import com.geniq.payment.model.Payment;

public class PaymentResponse {
    public String status;
    public String reference;
    public String provider;
    public String planId;
    public String price;

    // Gateway details the browser needs to open checkout. The publicKey is the
    // Razorpay key_id / Stripe publishable key — safe to expose. Never the secret.
    public String gatewayOrderId;
    public Long amount;
    public String currency;
    public String publicKey;
    public String clientSecret;

    public PaymentResponse(Payment payment) {
        this.status = payment.getStatus().name().toLowerCase();
        this.reference = payment.getReference();
        this.provider = payment.getProvider();
        this.planId = payment.getPlanId();
        this.price = payment.getPrice();
        this.gatewayOrderId = payment.getGatewayOrderId();
        this.amount = payment.getAmount();
        this.currency = payment.getCurrency();
    }
}
