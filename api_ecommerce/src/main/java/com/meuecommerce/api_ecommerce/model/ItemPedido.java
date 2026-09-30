package com.meuecommerce.api_ecommerce.model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "produto_fisico_id")
    private ProdutoFisico produtoFisico;

    private Integer quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido() {
    }
    public ItemPedido(Pedido pedido, ProdutoFisico produtoFisico, Integer quantidade, BigDecimal precoUnitario) {
        this.pedido = pedido;
        this.produtoFisico = produtoFisico;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    public ProdutoFisico getProdutoFisico() {
        return produtoFisico;
    }
    public void setProdutoFisico(ProdutoFisico produtoFisico) {
        this.produtoFisico = produtoFisico;
    }
    public Integer getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
