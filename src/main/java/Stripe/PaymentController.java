package Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PaymentController {

    @PostMapping("/create-checkout")
    public Map<String, Object> createCheckoutSession() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Create the parameters for the checkout session
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/success")
                    .setCancelUrl("http://localhost:8080/cancel")
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("sek") // Swedish Krona (SEK)
                                                    .setUnitAmount(19900L) // Price in öre (199 SEK)
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName("Premium Membership")
                                                                    .build())
                                                    .build())
                                    .build())
                    .build();

            // Create a Stripe session
            Session session = Session.create(params);

            // Put the session ID in the response
            response.put("id", session.getId());

        } catch (StripeException e) {
            // Handle any Stripe exceptions that occur
            response.put("error", e.getMessage());
        }

        return response;
    }
}
