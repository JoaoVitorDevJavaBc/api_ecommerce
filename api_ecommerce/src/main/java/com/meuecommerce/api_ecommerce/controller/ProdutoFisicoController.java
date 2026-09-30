package com.meuecommerce.api_ecommerce.controller;

import com.meuecommerce.api_ecommerce.model.ProdutoFisico;
import com.meuecommerce.api_ecommerce.repository.ProdutoFisicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos-fisicos")
public class ProdutoFisicoController {

    @Autowired
    private ProdutoFisicoRepository produtoFisicoRepository;

    @PostMapping
    public ProdutoFisico criar(@RequestBody ProdutoFisico produtoFisico) {
        return produtoFisicoRepository.save(produtoFisico);
    }
}