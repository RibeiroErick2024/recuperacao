package com.example;

public class ProdutoService {

    public boolean validarProduto(Produto produto) {
        if (produto == null) {
            throw new RuntimeException("Produto não pode ser nulo.");
        }

        if (!validarIdProduto(produto.getId())) {
            throw new RuntimeException("ID do produto é inválido.");
        }

        if (!validarNomeProduto(produto.getNome())) {
            throw new RuntimeException("Nome do produto contém caracteres inválidos ou está em branco.");
        }

        if (!validarPrecoProduto(produto.getPreco())) {
            throw new RuntimeException("O preço do produto deve ser maior que zero.");
        }

        if (!validarQuantidadeEstoque(produto.getQuantidadeEstoque())) {
            throw new RuntimeException("A quantidade em estoque não pode ser negativa.");
        }

        return true;
    }

    private boolean validarIdProduto(Long id) {
        return id != null && id > 0;
    }

    private boolean validarNomeProduto(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private boolean validarPrecoProduto(Double preco) {
        return preco != null && preco > 0;
    }

    private boolean validarQuantidadeEstoque(int quantidade) {
        return quantidade >= 0;
    }

    
}

