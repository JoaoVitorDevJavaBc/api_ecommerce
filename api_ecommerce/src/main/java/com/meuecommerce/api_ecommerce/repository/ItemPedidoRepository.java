package com.meuecommerce.api_ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.meuecommerce.api_ecommerce.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
