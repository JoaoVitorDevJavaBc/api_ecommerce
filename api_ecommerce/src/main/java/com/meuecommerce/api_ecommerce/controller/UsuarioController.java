package com.meuecommerce.api_ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stripe.exception.StripeException;
import com.meuecommerce.api_ecommerce.model.Usuario;
import com.meuecommerce.api_ecommerce.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @PostMapping
    public Usuario cadastrar(@RequestBody Usuario usuario) throws StripeException {

        return usuarioService.cadastrar(usuario);
    }

}
 