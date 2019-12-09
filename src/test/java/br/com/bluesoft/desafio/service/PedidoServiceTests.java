package br.com.bluesoft.desafio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bluesoft.desafio.dto.FornecedorProdutoDTO;
import br.com.bluesoft.desafio.dto.MelhorFornecedorDTO;
import br.com.bluesoft.desafio.dto.PrecoProdutoDTO;
import br.com.bluesoft.desafio.dto.ProdutoPedidoDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.FornecedorRepository;
import br.com.bluesoft.desafio.service.exception.ServiceException;
import br.com.bluesoft.desafio.ws.ProdutoWS;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class PedidoServiceTests {

	@InjectMocks
	private PedidoService pedidoService;

	@Mock
	private ProdutoWS produtoWS;

	@Mock
	private ProdutoService produtoService;

	@Mock
	private FornecedorRepository fornecedorRepository;

	@Before
	public void prepararMocks() {

		FornecedorProdutoDTO fornecedor1 = new FornecedorProdutoDTO();
		fornecedor1.setCnpj("56.918.868/0001-20");
		fornecedor1.setNome("Fornecedor 1");
		fornecedor1.setPrecos(Arrays.asList(
				new PrecoProdutoDTO[] { new PrecoProdutoDTO(BigDecimal.valueOf(6.89), 1), new PrecoProdutoDTO(BigDecimal.valueOf(5.89), 10), }));

		FornecedorProdutoDTO fornecedor2 = new FornecedorProdutoDTO();
		fornecedor2.setCnpj("37.563.823/0001-35");
		fornecedor2.setNome("Fornecedor 2");
		fornecedor2.setPrecos(Arrays
				.asList(new PrecoProdutoDTO[] { new PrecoProdutoDTO(BigDecimal.valueOf(6.8), 1), new PrecoProdutoDTO(BigDecimal.valueOf(6), 10) }));

		List<FornecedorProdutoDTO> fornecedores = new ArrayList<FornecedorProdutoDTO>();
		fornecedores.add(fornecedor1);
		fornecedores.add(fornecedor2);

		when(produtoWS.consultarFornecedores(eq("7894900011517"))).thenReturn(fornecedores);

		when(produtoService.buscarPorGtin("7894900011517")).thenReturn(new Produto("7894900011517"));
		
		when(produtoService.buscarPorGtin("7891000100103")).thenReturn(new Produto("7891000100103"));

		when(fornecedorRepository.findOne("37.563.823/0001-35")).thenReturn(new Fornecedor("37.563.823/0001-35", "Fornecedor"));
	}

	@Test
	public void obterProdutosInformadosTest() {

		List<ProdutoPedidoDTO> produtosPedido = Arrays.asList(new ProdutoPedidoDTO[] { new ProdutoPedidoDTO("7894900011517", 3),
				new ProdutoPedidoDTO("7891910000197", 1), new ProdutoPedidoDTO("7891000100103", 0), new ProdutoPedidoDTO("7891910007110", 0) });

		List<ProdutoPedidoDTO> produtosInformados = pedidoService.obterProdutosInformados(produtosPedido);

		assertEquals(produtosInformados.size(), 2);
	}

	@Test
	public void selecionarMelhorFornecedorTest() {
		FornecedorProdutoDTO fornecedorProdutoDTO1 = new FornecedorProdutoDTO();
		fornecedorProdutoDTO1.setCnpj("56.918.868/0001-20");
		fornecedorProdutoDTO1.setNome("Fornecedor 1");
		fornecedorProdutoDTO1.getPrecos().add(new PrecoProdutoDTO(BigDecimal.valueOf(6.89), 1));
		fornecedorProdutoDTO1.getPrecos().add(new PrecoProdutoDTO(BigDecimal.valueOf(5.89), 10));

		FornecedorProdutoDTO fornecedorProdutoDTO2 = new FornecedorProdutoDTO();
		fornecedorProdutoDTO2.setCnpj("37.563.823/0001-35");
		fornecedorProdutoDTO2.setNome("Fornecedor 2");
		fornecedorProdutoDTO2.getPrecos().add(new PrecoProdutoDTO(BigDecimal.valueOf(6.8), 1));
		fornecedorProdutoDTO2.getPrecos().add(new PrecoProdutoDTO(BigDecimal.valueOf(6.0), 10));

		List<FornecedorProdutoDTO> fornecedores = Arrays.asList(new FornecedorProdutoDTO[] { fornecedorProdutoDTO1, fornecedorProdutoDTO2 });

		MelhorFornecedorDTO melhorFornecedor = pedidoService.selecionarMelhorFornecedor(1, fornecedores);

		assertEquals(melhorFornecedor.getFornecedor().get().getCnpj(), fornecedorProdutoDTO2.getCnpj());
	}
	
	@Test
	public void agruparPedidosPorFornecedorTest() {
		
		List<ProdutoPedidoDTO> produtos = new ArrayList<>();
		produtos.add(new ProdutoPedidoDTO("7894900011517", 1));
		
		Map<Fornecedor, Pedido> pedidosAgrupados = pedidoService.agruparPedidosPorFornecedor(produtos);
		
		assertEquals(pedidosAgrupados.size(), 1);
		
	}
	
	@Test(expected = ServiceException.class)
	public void agruparPedidosPorFornecedorExceptionTest() {
		
		List<ProdutoPedidoDTO> produtos = new ArrayList<>();
		produtos.add(new ProdutoPedidoDTO("7891000100103", 3));
		
		Map<Fornecedor, Pedido> pedidosAgrupados = pedidoService.agruparPedidosPorFornecedor(produtos);
		
		assertEquals(pedidosAgrupados.size(), 1);
		
	}

}
