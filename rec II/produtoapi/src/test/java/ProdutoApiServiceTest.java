

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Produto;
import com.example.ProdutoApiClient;
import com.example.ProdutoApiService;

@ExtendWith(MockitoExtension.class) 
class ProdutoApiServiceTest {

    @Mock
    private ProdutoApiClient produtoApiClient; 

    @InjectMocks
    private ProdutoApiService produtoApiService; 
    private Produto produto1;
    private Produto produto2;

    @BeforeEach
    void setUp() {
        
        produto1 = new Produto(1L, "Banana", "frutas", 3.50, 100);
        produto2 = new Produto(2L, "Maçã", "frutas", 5.00, 50);
    }

    
    @Test
    void buscarProdutosPorCategoriaFrutas() {
        List<Produto> produtos = Arrays.asList(produto1, produto2);

        
        when(produtoApiClient.buscarProdutosPorCategoria("frutas")).thenReturn(produtos);

        
        List<Produto> resultado = produtoApiService.buscarProdutosPorCategoria("frutas");

        
        assertEquals(2, resultado.size(), "A lista de produtos deve ter 2 itens");
        assertTrue(resultado.contains(produto1), "A lista deve conter a Banana");
        assertTrue(resultado.contains(produto2), "A lista deve conter a Maçã");
    }

   
    @Test
    void buscarProdutosPorCategoriaInexistente() {
        
        when(produtoApiClient.buscarProdutosPorCategoria("eletronicos")).thenReturn(Collections.emptyList());

        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoria("eletronicos");
        });

        assertEquals("Nenhum produto encontrado para a categoria: eletronicos", exception.getMessage());
    }

    
    @Test
    void buscarProdutosSemCategoria() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoria(null);
        });
        assertEquals("Categoria não pode ser nula ou vazia", exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoria("");
        });
        assertEquals("Categoria não pode ser nula ou vazia", exception.getMessage());
    }

    
    @Test
    void buscarProdutosPorCategoriaComConversaoParaUSD() {
        List<Produto> produtos = Arrays.asList(produto1, produto2);


        when(produtoApiClient.buscarProdutosPorCategoriaComConversao("frutas", "USD")).thenReturn(produtos);

        List<Produto> resultado = produtoApiService.buscarProdutosPorCategoriaComConversao("frutas", "USD");

        
        assertEquals(2, resultado.size(), "A lista de produtos deve ter 2 itens");
        assertEquals(3.50, resultado.get(0).getPreco(), "O preço da Banana deve ser 3.50");
        assertEquals(5.00, resultado.get(1).getPreco(), "O preço da Maçã deve ser 5.00");
    }

    
    @Test
    void buscarProdutosPorCategoriaComMoedaNaoSuportada() {
        List<Produto> produtos = Arrays.asList(produto1, produto2);

        
        when(produtoApiClient.buscarProdutosPorCategoriaComConversao("frutas", "INR")).thenReturn(produtos);

        List<Produto> resultado = produtoApiService.buscarProdutosPorCategoriaComConversao("frutas", "INR");

        
        assertEquals(2, resultado.size(), "A lista de produtos deve ter 2 itens");
        assertEquals(3.50, resultado.get(0).getPreco(), 0.01, "O preço da Banana não deve ser alterado");
        assertEquals(5.00, resultado.get(1).getPreco(), 0.01, "O preço da Maçã não deve ser alterado");
    }

    
    @Test
    void buscarProdutosSemMoeda() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoriaComConversao("frutas", null);
        });
        assertEquals("O código da moeda não pode ser nulo ou vazio", exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoriaComConversao("frutas", "");
        });
        assertEquals("O código da moeda não pode ser nulo ou vazio", exception.getMessage());
    }

    
    @Test
    void buscarProdutosSemCategoriaComMoeda() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoriaComConversao(null, "USD");
        });
        assertEquals("Categoria não pode ser nula ou vazia", exception.getMessage());

        exception = assertThrows(RuntimeException.class, () -> {
            produtoApiService.buscarProdutosPorCategoriaComConversao("", "USD");
        });
        assertEquals("Categoria não pode ser nula ou vazia", exception.getMessage());
    }
}
