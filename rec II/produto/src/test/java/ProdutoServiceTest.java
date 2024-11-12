import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.Produto;
import com.example.ProdutoService;

class ProdutoServiceTest {

    private final ProdutoService produtoService = new ProdutoService();

    

    @Test
    void cadastroProdutoComIdValido() {
        Produto produto = new Produto(5L, "abacate", 20.50, 20);
        assertTrue(produtoService.validarProduto(produto));
    }

    @Test
    void cadastroProdutoComIdNegativo() {
        Produto produto = new Produto(-30L, "Banana", 1.50, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("ID do produto é inválido.", exception.getMessage());
    }

    @Test
    void cadastroProdutoComIdNulo() {
        Produto produto = new Produto(null, "Banana", 1.50, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("ID do produto é inválido.", exception.getMessage());
    }

    // Nome do Produto

    @Test
    void cadastroProdutoComNomeValido() {
        Produto produto = new Produto(50L, "banana", 1.50, 20);
        assertTrue(produtoService.validarProduto(produto));
    }

    @Test
    void cadastroProdutoComNomeVazio() {
        Produto produto = new Produto(50L, "", 1.50, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("Nome do produto contém caracteres inválidos ou está em branco.", exception.getMessage());
    }

    @Test
    void cadastroProdutoComNomeNulo() {
        Produto produto = new Produto(50L, null, 1.50, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("Nome do produto contém caracteres inválidos ou está em branco.", exception.getMessage());
    }


    //  Estoque do produto

    @Test
    void cadastroProdutoComQuantidadeEstoqueValida() {
        Produto produto = new Produto(50L, "banana", 1.50, 20);
        assertTrue(produtoService.validarProduto(produto));
    }

    @Test
    void cadastroProdutoComQuantidadeEstoqueNegativa() {
        Produto produto = new Produto(50L, "banana", 1.50, -5);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("A quantidade em estoque não pode ser negativa.", exception.getMessage());
    }

    @Test
    void cadastroProdutoComQuantidadeEstoqueZero() {
        Produto produto = new Produto(50L, "Produto G", 1.50, 0);
        assertTrue(produtoService.validarProduto(produto));
    }

    //Preço do Produto

    @Test
    void cadastroProdutoComPrecoValido() {
        Produto produto = new Produto(50L, "banana", 1.50, 20);
        assertTrue(produtoService.validarProduto(produto));
    }

    @Test
    void cadastroProdutoComPrecoNegativo() {
        Produto produto = new Produto(50L, "bergamota", -5.00, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("O preço do produto deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void cadastroProdutoComPrecoZero() {
        Produto produto = new Produto(50L, "banana", 0.00, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("O preço do produto deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void cadastroProdutoComPrecoNulo() {
        Produto produto = new Produto(50L, "banana", null, 20);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.validarProduto(produto);
        });
        assertEquals("O preço do produto deve ser maior que zero.", exception.getMessage());
    }

    
}
