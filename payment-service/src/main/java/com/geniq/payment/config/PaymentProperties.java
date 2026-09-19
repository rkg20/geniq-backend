package com.geniq.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Strongly-typed payment configuration bound from `app.payment.*`.
 * All credentials are injected from environment variables (see application.yml)
 * and must be TEST keys in non-production environments.
 */
@Component
@ConfigurationProperties(prefix = "app.payment")
public class PaymentProperties {

    private String provider = "razorpay";
    private String currency = "INR";
    private Razorpay razorpay = new Razorpay();
    private Stripe stripe = new Stripe();

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Razorpay getRazorpay() { return razorpay; }
    public void setRazorpay(Razorpay razorpay) { this.razorpay = razorpay; }
    public Stripe getStripe() { return stripe; }
    public void setStripe(Stripe stripe) { this.stripe = stripe; }

    public static class Razorpay {
        private String keyId;
        private String keySecret;

        public String getKeyId() { return keyId; }
        public void setKeyId(String keyId) { this.keyId = keyId; }
        public String getKeySecret() { return keySecret; }
        public void setKeySecret(String keySecret) { this.keySecret = keySecret; }
    }

    public static class Stripe {
        private String secretKey;
        private String publishableKey;

        public String getSecretKey() { return secretKey; }
        public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
        public String getPublishableKey() { return publishableKey; }
        public void setPublishableKey(String publishableKey) { this.publishableKey = publishableKey; }
    }
}
