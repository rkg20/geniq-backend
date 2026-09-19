package com.geniq.payment.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Confirmation payload sent by the browser after Razorpay checkout completes.
 * The signature is verified server-side before the payment is marked paid.
 */
public class VerifyRequest {

    @NotBlank
    public String reference;

    @NotBlank
    public String razorpayOrderId;

    @NotBlank
    public String razorpayPaymentId;

    @NotBlank
    public String razorpaySignature;
}
