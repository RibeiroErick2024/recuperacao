package com.example.produto_spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.produto_spring.entities.Produto;
import com.example.produto_spring.repositories.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        produtoRepository.deleteAll(); // Limpa a base de dados antes de cada teste
    }

    // 1. Teste de Criação de Produto Válido
    @Test
    public void criarProdutoValido() throws Exception {
        Produto produto = new Produto(null, "Banana", "Frutas", 3.50, 100);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Banana"))
                .andExpect(jsonPath("$.categoria").value("Frutas"))
                .andExpect(jsonPath("$.preco").value(3.50))
                .andExpect(jsonPath("$.quantidadeEstoque").value(100))
                .andExpect(jsonPath("$.id").exists());
    }

    
    @Test
    public void criarProdutoComNomeVazio() throws Exception {
        Produto produto = new Produto(null, "", "Frutas", 3.50, 100);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Nome do produto não pode ser vazio"));
    }

    
    @Test
    public void criarProdutoComPrecoInvalido() throws Exception {
        Produto produto = new Produto(null, "Maçã", "Frutas", -5.00, 50);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("O preço do produto deve ser maior que zero"));
    }

    
    @Test
    public void criarProdutoComQuantidadeNegativa() throws Exception {
        Produto produto = new Produto(null, "Laranja", "Frutas", 4.00, -10);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Quantidade em estoque não pode ser negativa"));
    }

    
    @Test
    public void consultarProdutoExistentePorId() throws Exception {
        Produto produto = new Produto(null, "Banana", "Frutas", 3.50, 100);
        Produto produtoCriado = produtoRepository.save(produto);

        mockMvc.perform(get("/produtos/{id}", produtoCriado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Banana"))
                .andExpect(jsonPath("$.categoria").value("Frutas"))
                .andExpect(jsonPath("$.preco").value(3.50))
                .andExpect(jsonPath("$.quantidadeEstoque").value(100));
    }

    
    @Test
    public void consultarProdutoInexistentePorId() throws Exception {
        mockMvc.perform(get("/produtos/{id}", 99))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produto não encontrado"));
    }

  
    @Test
    public void atualizarProdutoExistente() throws Exception {
        Produto produto = new Produto(null, "Banana", "Frutas", 3.50, 100);
        Produto produtoCriado = produtoRepository.save(produto);

        produtoCriado.setPreco(4.00);

        mockMvc.perform(put("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produtoCriado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.preco").value(4.00))
                .andExpect(jsonPath("$.quantidadeEstoque").value(100));
    }

   
    @Test
    public void atualizarProdutoComIdInexistente() throws Exception {
        Produto produto = new Produto(99L, "Banana", "Frutas", 3.50, 100);

        mockMvc.perform(put("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Produto não encontrado"));
    }

   
    @Test
    public void excluirProdutoExistente() throws Exception {
        Produto produto = new Produto(null, "Banana", "Frutas", 3.50, 100);
        Produto produtoCriado = produtoRepository.save(produto);

        mockMvc.perform(delete("/produtos/{id}", produtoCriado.getId()))
                .andExpect(status().isNoContent());

        Optional<Produto> produtoDeletado = produtoRepository.findById(produtoCriado.getId());
        assert produtoDeletado.isEmpty();
    }

    
    @Test
    public void excluirProdutoInexistente() throws Exception {
        mockMvc.perform(delete("/produtos/{id}", 99L))
                .andExpect(status().isNoContent());
    }
}
