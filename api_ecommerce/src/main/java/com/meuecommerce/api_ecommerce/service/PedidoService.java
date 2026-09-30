package com.meuecommerce.api_ecommerce.service;

import com.meuecommerce.api_ecommerce.model.ItemPedido;
import com.meuecommerce.api_ecommerce.model.Pedido;
import com.meuecommerce.api_ecommerce.repository.ItemPedidoRepository;
import com.meuecommerce.api_ecommerce.repository.PedidoRepository;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private StripeService stripeService;

    @Transactional
    public String finalizarPedidoECriarCheckout(Pedido pedido, List<ItemPedido> itens, String urlSucesso, String urlCancelado) throws StripeException {
        
      
        pedido.setStatus(Pedido.StatusPedido.PENDENTE);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

      
        for (ItemPedido item : itens) {
            item.setPedido(pedidoSalvo);
        }
        itemPedidoRepository.saveAll(itens);

        
        pedidoSalvo.setItens(itens);

       
        String urlCheckoutStripe = stripeService.criarSessaoCheckout(itens, pedidoSalvo, urlSucesso, urlCancelado);

        
        return urlCheckoutStripe;
    }
}
