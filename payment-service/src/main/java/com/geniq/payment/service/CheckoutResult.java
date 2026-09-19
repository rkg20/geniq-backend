package com.geniq.payment.service;

import com.geniq.payment.gateway.GatewayOrder;
import com.geniq.payment.model.Payment;

/**
 * Bundles the persisted payment with the gateway order data the browser needs
 * to open checkout (public key, and Stripe client secret when applicable).
 */
public class CheckoutResult {
    public final Payment payment;
    public final GatewayOrder gatewayOrder;

    public CheckoutResult(Payment payment, GatewayOrder gatewayOrder) {
        this.payment = payment;
        this.gatewayOrder = gatewayOrder;
    }
}
