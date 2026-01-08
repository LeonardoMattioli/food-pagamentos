package br.com.food.pagamentos.dto;

import br.com.food.pagamentos.model.StatusEnum;

import java.math.BigDecimal;

public record PagamentoDTO(Long id,
                           BigDecimal valor,
                           String name,
                           String numero,
                           String expiracao,
                           String codigo,
                           StatusEnum status,
                           Long pedidoId,
                           Long formaDePagamentoId) {
}
