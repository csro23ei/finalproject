package Stripe;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.apiKey}")
    private String stripeApiKey;

    @Bean
    public void configureStripe() {
        Stripe.apiKey = stripeApiKey;
    }
}
