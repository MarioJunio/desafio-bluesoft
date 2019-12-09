package br.com.bluesoft.desafio.api;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bluesoft.desafio.dto.PedidoDTO;
import br.com.bluesoft.desafio.dto.ProdutoPedidoDTO;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping()
	public ResponseEntity<Collection<PedidoDTO>> novo(@RequestBody List<ProdutoPedidoDTO> produtosPedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(converterParaDTO(pedidoService.novo(produtosPedido)));
	}

	@GetMapping()
	public ResponseEntity<?> listar() {
		List<Pedido> pedidos = pedidoService.buscarTodos();
		return !pedidos.isEmpty() ? ResponseEntity.ok(converterParaDTO(pedidos)) : ResponseEntity.noContent().build();
	}

	private List<PedidoDTO> converterParaDTO(List<Pedido> pedidos) {
		return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoDTO.class)).collect(Collectors.toList());
	}
 
}
