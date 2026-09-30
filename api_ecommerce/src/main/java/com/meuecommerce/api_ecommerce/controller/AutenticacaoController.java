package com.meuecommerce.api_ecommerce.controller;

import com.meuecommerce.api_ecommerce.model.Usuario;
import com.meuecommerce.api_ecommerce.repository.UsuarioRepository;
import com.meuecommerce.api_ecommerce.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
      
        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst()
                .orElse(null);

        if (usuario != null && usuario.getSenha().equals(request.getSenha())) {
            String token = tokenService.gerarToken(usuario);
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body(Map.of("erro", "E-mail ou senha inválidos"));
    }

    public static class LoginRequest {
        private String email;
        private String senha;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }
}
