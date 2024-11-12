package com.example;

import java.util.List;

public class ProdutoApiService {

    private final ProdutoApiClient produtoApiClient;

    public ProdutoApiService(ProdutoApiClient produtoApiClient) {
        this.produtoApiClient = produtoApiClient;
    }

    public List<Produto> buscarProdutosPorCategoria(String categoria) {
        validarCategoria(categoria);
        List<Produto> produtos = produtoApiClient.buscarProdutosPorCategoria(categoria);
        if (produtos.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado para a categoria: " + categoria);
        }
        return produtos;
    }

    public List<Produto> buscarProdutosPorCategoriaComConversao(String categoria, String moeda) {
        validarCategoria(categoria);
        validarMoeda(moeda);
        List<Produto> produtos = produtoApiClient.buscarProdutosPorCategoriaComConversao(categoria, moeda);
        if (produtos.isEmpty()) {
            throw new RuntimeException("Nenhum produto encontrado para a categoria: " + categoria);
        }
        return produtos;
    }

    private void validarCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new RuntimeException("Categoria não pode ser nula ou vazia.");
        }
    }

     private void validarMoeda(String moeda) {
        if (moeda == null || moeda.trim().isEmpty()) {
            throw new RuntimeException("O código da moeda não pode ser nulo ou vazio.");
        }
    }
}
