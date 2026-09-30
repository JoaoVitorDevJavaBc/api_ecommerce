package com.meuecommerce.api_ecommerce.config;
import jakarta.annotation.PostConstruct;
import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    @Value("${stripe.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = this.apiKey;
    }
}