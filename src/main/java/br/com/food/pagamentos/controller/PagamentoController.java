package br.com.food.pagamentos.controller;

import br.com.food.pagamentos.dto.PagamentoDTO;
import br.com.food.pagamentos.service.PagamentoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public Page<PagamentoDTO> listar(@PageableDefault(size= 10) Pageable paginacao){
        return pagamentoService.obterTodos(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> obterPorId(@PathVariable @NotNull Long id){
        PagamentoDTO pagamentoDTO = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody @Valid PagamentoDTO pagamentoDTO, UriComponentsBuilder uriComponentsBuilder){
        PagamentoDTO pagamentoCriado = pagamentoService.criarPagamento(pagamentoDTO);
        URI endereco = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoCriado.id()).toUri();

        return ResponseEntity.created(endereco).body(pagamentoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizarPagamento(@PathVariable @NotNull Long id,@RequestBody @Valid PagamentoDTO pagamentoDTO){
        PagamentoDTO registroSalvo = pagamentoService.obterPorId(id);

        if (registroSalvo == null){
            throw new EntityNotFoundException();
        }

        PagamentoDTO pagamentoAtualizado = pagamentoService.atualizarPagamento(id, pagamentoDTO);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPagamento(@PathVariable @NotNull Long id){
        pagamentoService.removerPagamento(id);
        return ResponseEntity.noContent().build();
    }
}
