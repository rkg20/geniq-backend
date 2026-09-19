package com.geniq.payment.controller;

import com.geniq.payment.dto.CheckoutRequest;
import com.geniq.payment.dto.PaymentResponse;
import com.geniq.payment.dto.VerifyRequest;
import com.geniq.payment.model.Payment;
import com.geniq.payment.service.CheckoutResult;
import com.geniq.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentResponse> checkout(@Valid @RequestBody CheckoutRequest req) {
        CheckoutResult result = paymentService.createCheckout(req);
        PaymentResponse body = new PaymentResponse(result.payment);
        // Attach the public key / client secret the browser needs to open the
        // gateway checkout. These are non-secret, per the gateway's design.
        body.publicKey = result.gatewayOrder.publicKey;
        body.clientSecret = result.gatewayOrder.clientSecret;
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(@RequestParam("ref") String reference) {
        Payment payment = paymentService.getByReference(reference);
        if (payment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Payment not found."));
        }
        return ResponseEntity.ok(new PaymentResponse(payment));
    }

    /**
     * Called by the browser after Razorpay checkout succeeds. Verifies the
     * signature server-side, then marks the payment paid.
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@Valid @RequestBody VerifyRequest req) {
        Payment payment = paymentService.confirmRazorpay(
                req.reference, req.razorpayOrderId, req.razorpayPaymentId, req.razorpaySignature);
        if (payment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Payment verification failed."));
        }
        return ResponseEntity.ok(new PaymentResponse(payment));
    }

    /**
     * Simulates a provider webhook confirming payment. In production this
     * endpoint must verify the provider's signature before trusting the event.
     */
    @PostMapping("/webhook/confirm")
    public ResponseEntity<?> confirm(@RequestParam("ref") String reference) {
        Payment payment = paymentService.markPaid(reference);
        if (payment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Payment not found."));
        }
        return ResponseEntity.ok(new PaymentResponse(payment));
    }
}
