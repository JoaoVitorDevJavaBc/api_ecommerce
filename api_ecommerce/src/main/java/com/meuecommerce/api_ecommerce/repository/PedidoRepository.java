package com.meuecommerce.api_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meuecommerce.api_ecommerce.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
