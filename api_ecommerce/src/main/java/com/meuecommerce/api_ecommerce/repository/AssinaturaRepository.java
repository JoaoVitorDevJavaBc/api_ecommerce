package com.meuecommerce.api_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meuecommerce.api_ecommerce.model.Usuario;

public interface AssinaturaRepository extends JpaRepository<Usuario, Long> {
   
}
