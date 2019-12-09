package br.com.bluesoft.desafio.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bluesoft.desafio.dto.FornecedorProdutoDTO;
import br.com.bluesoft.desafio.dto.MelhorFornecedorDTO;
import br.com.bluesoft.desafio.dto.PrecoProdutoDTO;
import br.com.bluesoft.desafio.dto.ProdutoPedidoDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.PedidoIten;
import br.com.bluesoft.desafio.model.PedidoItenId;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.service.exception.ServiceException;
import br.com.bluesoft.desafio.ws.ProdutoWS;

@Service
public class PedidoService {

	@Autowired
	private ProdutoWS produtoWS;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;

	@Transactional
	public List<Pedido> novo(List<ProdutoPedidoDTO> produtosPedido) {
		List<ProdutoPedidoDTO> produtosInformados = obterProdutosInformados(produtosPedido);

		return agruparPedidosPorFornecedor(produtosInformados).values().stream().map(pedido -> pedidoRepository.save(pedido))
				.collect(Collectors.toList());
	}

	public List<Pedido> buscarTodos() {
		return pedidoRepository.findAll();
	}

	public List<ProdutoPedidoDTO> obterProdutosInformados(List<ProdutoPedidoDTO> produtosPedido) {
		return produtosPedido.stream().filter(produtoPedido -> produtoPedido.getQuantidade() > 0).collect(Collectors.toList());
	}

	public Map<Fornecedor, Pedido> agruparPedidosPorFornecedor(List<ProdutoPedidoDTO> produtosPedido) {
		Map<Fornecedor, Pedido> agrupadorPedidoFornecedor = new HashMap<>();

		for (ProdutoPedidoDTO produtoPedido : produtosPedido) {
			
			List<FornecedorProdutoDTO> fornecedores = produtoWS.consultarFornecedores(produtoPedido.getGtin());
			
			MelhorFornecedorDTO melhorFornecedorDTO = selecionarMelhorFornecedor(produtoPedido.getQuantidade(), fornecedores);

			Produto produto = produtoService.buscarPorGtin(produtoPedido.getGtin());
 
			if (melhorFornecedorDTO.getFornecedor().isPresent()) {
				Fornecedor fornecedor = buscarOuCriarFornecedor(melhorFornecedorDTO);

				Optional<Pedido> opPedido = Optional.ofNullable(agrupadorPedidoFornecedor.get(fornecedor));

				if (!opPedido.isPresent()) {
					opPedido = Optional.of(new Pedido(fornecedor));
					agrupadorPedidoFornecedor.put(fornecedor, opPedido.get());
				}

				PedidoItenId pedidoItenId = new PedidoItenId();
				pedidoItenId.setProduto(produto);
				pedidoItenId.setPedido(opPedido.get());

				PedidoIten pedidoIten = new PedidoIten();
				pedidoIten.setId(pedidoItenId);
				pedidoIten.setPreco(melhorFornecedorDTO.getMenorPreco());
				pedidoIten.setQuantidade(produtoPedido.getQuantidade());
				pedidoIten.setTotal(pedidoIten.getPreco().multiply(BigDecimal.valueOf(pedidoIten.getQuantidade())));

				opPedido.get().getItens().add(pedidoIten);

			} else {
				throw new ServiceException("Nenhum fornecedor encontrado para a quantidade solicitada do produto " + produto.getNome());
			}
		}

		return agrupadorPedidoFornecedor;
	}

	private Fornecedor buscarOuCriarFornecedor(MelhorFornecedorDTO melhorFornecedorDTO) {
		Fornecedor melhorFornecedor = melhorFornecedorDTO.getFornecedor().get();
		
		return Optional.ofNullable(fornecedorRepository.findOne(melhorFornecedor.getCnpj())).orElse(melhorFornecedor);
	}

	public MelhorFornecedorDTO selecionarMelhorFornecedor(final Integer quantidade, List<FornecedorProdutoDTO> fornecedores) {

		Fornecedor fornecedor = null;
		BigDecimal menorPreco = null;

		for (FornecedorProdutoDTO fornecedorProduto : fornecedores) {

			for (PrecoProdutoDTO precoProduto : fornecedorProduto.getPrecos()) {

				if ((quantidade >= precoProduto.getQuantidadeMinima())
						&& ((menorPreco == null) || (precoProduto.getPreco().compareTo(menorPreco) < 0))) {
					fornecedor = new Fornecedor(fornecedorProduto.getCnpj(), fornecedorProduto.getNome());
					menorPreco = precoProduto.getPreco();
				}

			}

		}

		return new MelhorFornecedorDTO(Optional.ofNullable(fornecedor), menorPreco);
	}

}
