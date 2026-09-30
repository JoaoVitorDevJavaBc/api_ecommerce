package com.meuecommerce.api_ecommerce.service;

import com.meuecommerce.api_ecommerce.model.ItemPedido;
import com.meuecommerce.api_ecommerce.model.Pedido;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class StripeService {

    public Product criarProdutoNoStripe(String nome, String descricao) throws StripeException {
        ProductCreateParams params = ProductCreateParams.builder()
                .setName(nome)
                .setDescription(descricao)
                .build();

        return Product.create(params);
    }

    public PriceCreateParams criarParametrosDePreco(String productId, Long valorEmCentavos) {
        return PriceCreateParams.builder()
                .setProduct(productId)
                .setUnitAmount(valorEmCentavos)
                .setCurrency("brl")
                .build();
    }

    public String criarPrecoNoStripe(String productId, Long valorEmCentavos) throws StripeException {
        PriceCreateParams paramsPreco = criarParametrosDePreco(productId, valorEmCentavos);
        Price precoCriado = Price.create(paramsPreco);
        return precoCriado.getId();
    }

    public String criarSessaoCheckout(String precoId, String urlSucesso, String urlCancelado) throws StripeException {
        com.stripe.param.checkout.SessionCreateParams params = com.stripe.param.checkout.SessionCreateParams.builder()
                .setMode(com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(urlSucesso)
                .setCancelUrl(urlCancelado)
                .addLineItem(com.stripe.param.checkout.SessionCreateParams.LineItem.builder()
                        .setPrice(precoId)
                        .setQuantity(1L)
                        .build())
                .build();

        com.stripe.model.checkout.Session sessao = com.stripe.model.checkout.Session.create(params);
        return sessao.getUrl();
    }

    public String criarClienteNoStripe(String nome, String email) throws StripeException {
        com.stripe.param.CustomerCreateParams params = com.stripe.param.CustomerCreateParams.builder()
                .setName(nome)
                .setEmail(email)
                .build();

        com.stripe.model.Customer cliente = com.stripe.model.Customer.create(params);
        return cliente.getId();
    }

    public Product buscarProdutoNoStripe(String produtoId) throws StripeException {
        return Product.retrieve(produtoId);
    }

    public String criarSessaoAssinatura(String precoId, String clienteId, String urlSucesso, String urlCancelado) throws StripeException {
        com.stripe.param.checkout.SessionCreateParams params = com.stripe.param.checkout.SessionCreateParams.builder()
                .setMode(com.stripe.param.checkout.SessionCreateParams.Mode.SUBSCRIPTION)
                .setCustomer(clienteId)
                .setSuccessUrl(urlSucesso)
                .setCancelUrl(urlCancelado)
                .addLineItem(com.stripe.param.checkout.SessionCreateParams.LineItem.builder()
                        .setPrice(precoId)
                        .setQuantity(1L)
                        .build())
                .build();

        com.stripe.model.checkout.Session sessao = com.stripe.model.checkout.Session.create(params);
        return sessao.getUrl();
    }

    public String criarSessaoCheckout(List<ItemPedido> itens, Pedido pedido, String urlSucesso, String urlCancelado) throws StripeException {
        com.stripe.param.checkout.SessionCreateParams.Builder builder = com.stripe.param.checkout.SessionCreateParams.builder()
                .setMode(com.stripe.param.checkout.SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(urlSucesso)
                .setCancelUrl(urlCancelado)
                .putMetadata("pedido_id", pedido.getId().toString());

        for (ItemPedido item : itens) {
            builder.addLineItem(com.stripe.param.checkout.SessionCreateParams.LineItem.builder()
                    .setPrice(item.getProdutoFisico().getStripePriceId())
                    .setQuantity(item.getQuantidade().longValue())
                    .build());
        }

        com.stripe.model.checkout.Session sessao = com.stripe.model.checkout.Session.create(builder.build());
        return sessao.getUrl();
    }
}

