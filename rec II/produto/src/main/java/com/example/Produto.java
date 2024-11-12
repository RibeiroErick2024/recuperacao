package com.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Produto {
    private Long id;
    private String nome;
    private Double preco;
    private int quantidadeEstoque;
}
