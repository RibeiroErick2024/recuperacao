package com.example;
import java.util.List;

public interface ProdutoApiClient {

    /**
     * Busca produtos por categoria.
     *
     * @param categoria Nome da categoria
     * @return Lista de produtos encontrados
     */
    List<Produto> buscarProdutosPorCategoria(String categoria);

    /**
     * Busca produtos por categoria e retorna os valores convertidos para uma moeda específica.
     *
     * @param categoria Nome da categoria
     * @param moeda Código da moeda (exemplo: "USD", "EUR", "BRL")
     * @return Lista de produtos com valores convertidos
     */
    List<Produto> buscarProdutosPorCategoriaComConversao(String categoria, String moeda);
}

