package br.com.food.pagamentos.service;

import br.com.food.pagamentos.dto.PagamentoDTO;
import br.com.food.pagamentos.model.Pagamento;
import br.com.food.pagamentos.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> obterTodos(Pageable paginacao) {
        return pagamentoRepository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PagamentoDTO.class));

    }

    public PagamentoDTO obterPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento = pagamentoRepository.save(pagamento);
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Pagamento pagamentoAtualizado = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamentoAtualizado = pagamentoRepository.save(pagamentoAtualizado);
        return modelMapper.map(pagamentoAtualizado, PagamentoDTO.class);
    }

    public void removerPagamento(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
