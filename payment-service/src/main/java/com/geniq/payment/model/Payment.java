package com.geniq.payment.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    private String reference;

    private String planId;
    private String price;

    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerRole;
    private String ageGroup;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String provider;

    // Gateway-issued order identifier (e.g. Razorpay order_id or Stripe
    // PaymentIntent id). The amount is stored in the smallest currency unit
    // (paise for INR, cents for USD).
    private String gatewayOrderId;
    private Long amount;
    private String currency;

    // Set once the gateway confirms the payment.
    private String gatewayPaymentId;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();

    public Payment() {
    }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
    public String getPlanId() { return planId; }
    public void setPlanId(String planId) { this.planId = planId; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerRole() { return customerRole; }
    public void setCustomerRole(String customerRole) { this.customerRole = customerRole; }
    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getGatewayOrderId() { return gatewayOrderId; }
    public void setGatewayOrderId(String gatewayOrderId) { this.gatewayOrderId = gatewayOrderId; }
    public Long getAmount() { return amount; }
    public void setAmount(Long amount) { this.amount = amount; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public String getGatewayPaymentId() { return gatewayPaymentId; }
    public void setGatewayPaymentId(String gatewayPaymentId) { this.gatewayPaymentId = gatewayPaymentId; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
