package com.meuecommerce.api_ecommerce.controller;

import com.meuecommerce.api_ecommerce.model.ItemPedido;
import com.meuecommerce.api_ecommerce.model.Pedido;
import com.meuecommerce.api_ecommerce.service.PedidoService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> criarCheckout(@RequestBody CheckoutRequest request) {
        try {
            String urlSucesso = "http://localhost:8080/sucesso";
            String urlCancelado = "http://localhost:8080/cancelado";

            String checkoutUrl = pedidoService.finalizarPedidoECriarCheckout(
                    request.getPedido(), 
                    request.getItens(), 
                    urlSucesso, 
                    urlCancelado
            );

           
            return ResponseEntity.ok(Map.of("url", checkoutUrl));
            
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao integrar com o Stripe: " + e.getMessage()));
        }
    }

   
    public static class CheckoutRequest {
        private Pedido pedido;
        private List<ItemPedido> itens;

        public Pedido getPedido() { return pedido; }
        public void setPedido(Pedido pedido) { this.pedido = pedido; }
        public List<ItemPedido> getItens() { return itens; }
        public void setItens(List<ItemPedido> itens) { this.itens = itens; }
    }
}
