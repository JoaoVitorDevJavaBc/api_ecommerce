package com.meuecommerce.api_ecommerce.controller;

import com.meuecommerce.api_ecommerce.model.Pedido;
import com.meuecommerce.api_ecommerce.repository.PedidoRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/webhooks")
public class StripeWebhookController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> receberWebhook(
            @RequestBody String payload, 
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {

            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            System.out.println("❌ Erro de assinatura inválida!");
            return ResponseEntity.status(400).body("Assinatura inválida");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            
            if (dataObjectDeserializer.getObject().isPresent()) {
                Session session = (Session) dataObjectDeserializer.getObject().get();

                String pedidoIdStr = session.getMetadata().get("pedido_id");
                
                if (pedidoIdStr != null) {
                    Long pedidoId = Long.parseLong(pedidoIdStr);

                    pedidoRepository.findById(pedidoId).ifPresent(pedido -> {
                        try {
                            pedido.setStatus(Pedido.StatusPedido.valueOf("PAGO"));
                            pedidoRepository.save(pedido);
                            System.out.println("✅ Pedido #" + pedidoId + " atualizado para PAGO com sucesso!");
                        } catch (IllegalArgumentException e) {
                            System.out.println("⚠️ Status PAGO não está disponível no enum StatusPedido.");
                        }
                    });
                }
            }
        }
        return ResponseEntity.ok("Webhook processado");
    }
}
