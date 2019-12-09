package br.com.bluesoft.desafio;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import br.com.bluesoft.desafio.dto.PedidoItenDTO;
import br.com.bluesoft.desafio.dto.ProdutoDTO;
import br.com.bluesoft.desafio.model.PedidoIten;

@Configuration
@SpringBootApplication
public class DesafioApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		
		Converter<PedidoIten, PedidoItenDTO> conversorPedidoIten = new Converter<PedidoIten, PedidoItenDTO>() {

			@Override
			public PedidoItenDTO convert(MappingContext<PedidoIten, PedidoItenDTO> context) {
				PedidoIten source = context.getSource();

				return new PedidoItenDTO(new ProdutoDTO(source.getId().getProduto().getNome()), source.getQuantidade(), source.getPreco(),
						source.getTotal());
			}

		};
		
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(conversorPedidoIten);
		
		return mapper;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
