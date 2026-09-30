package com.meuecommerce.api_ecommerce.service;

import java.util.Map;
import java.util.HashMap;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meuecommerce.api_ecommerce.model.Usuario;
import com.meuecommerce.api_ecommerce.repository.UsuarioRepository;
import com.stripe.model.Customer;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrar(Usuario usuario) throws StripeException {

        Map<String, Object> params = new HashMap<>();
        params.put("email", usuario.getEmail());
        Customer customer = Customer.create(params);
        usuario.setStripeCustomerId(customer.getId());
        return usuarioRepository.save(usuario);
    }
}
