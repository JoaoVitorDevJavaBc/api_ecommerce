package com.meuecommerce.api_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuecommerce.api_ecommerce.model.Usuario;

public interface PlanoAssinaturaRepository extends JpaRepository<Usuario, Long> {
   
}
