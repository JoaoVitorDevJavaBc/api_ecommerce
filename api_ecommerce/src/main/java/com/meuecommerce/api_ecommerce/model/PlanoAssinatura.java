package com.meuecommerce.api_ecommerce.model;
import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class PlanoAssinatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String periodicidade;
    private String stripePriceId;

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getnome() {
        return this.nome;
    }
    public void setnome(String nome) {
        this.nome = nome;
    }
    public String getdescricao() {
        return this.descricao;
    }
    public void setdescricao(String descricao) {
        this.descricao = descricao;
    }
    public BigDecimal getpreco() {
        return this.preco;
    }
    public void setpreco(BigDecimal preco) {
        this.preco = preco;
    }
    public String getperiodicidade() {
        return this.periodicidade;
    }
    public void setperiodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }
    public String getstripePriceId() {
        return this.stripePriceId;
    }
    public void setstripePriceId(String stripePriceId) {
        this.stripePriceId = stripePriceId;
    }
}