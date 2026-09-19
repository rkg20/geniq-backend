package com.geniq.payment.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Checkout request coming from the frontend. Matches the payload built in
 * Checkout.jsx: { plan, price, name, email, phone, role, ageGroup }.
 */
public class CheckoutRequest {

    @NotBlank
    public String plan;

    public String price;

    @NotBlank
    public String name;

    @NotBlank
    public String email;

    public String phone;
    public String role;
    public String ageGroup;
}
