package com.meuecommerce.api_ecommerce.controller;

import com.meuecommerce.api_ecommerce.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private StripeService stripeService;
    
    @PostMapping
    public String cadastrarProduto(@RequestBody ProdutoRequest dto) throws StripeException {
        Product produtoStripe = stripeService.criarProdutoNoStripe(dto.getNome(), dto.getDescricao());
        String precoId = stripeService.criarPrecoNoStripe(produtoStripe.getId(), dto.getValorEmCentavos());
        return "Sucesso! Produto criado com o ID: " + produtoStripe.getId() + " | Preço ID: " + precoId;
    }
}
class ProdutoRequest {
    private String nome;
    private String descricao;
    private Long valorEmCentavos;

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    public String getDescricao() { 
        return descricao; 
    }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }
    public Long getValorEmCentavos() { 
        return valorEmCentavos; 
    }
    public void setValorEmCentavos(Long valorEmCentavos) { 
        this.valorEmCentavos = valorEmCentavos; 
    }
}
