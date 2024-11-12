package com.example.produto_spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.produto_spring.entities.Produto;
import com.example.produto_spring.repositories.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public Produto create(Produto produto) throws Exception {
        if (this.validarProduto(produto)){
            Produto produtoCriado = repository.save(produto);
            return produtoCriado;
        }else{
            throw new Exception("Erro de validação no cadastro da produto");
        }
    }

    public Produto read(Long id) {
        return repository.findById(id).get();
    }

    public List<Produto> list() {
        List<Produto> produtos = (List<Produto>) repository.findAll();
        return produtos;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public Produto update(Produto produto) throws Exception {
        if (repository.existsById(produto.getId())) {
            return this.create(produto);
        } else {
            return null;
        }
    }

    public boolean validarProduto(Produto produto) {
        if (produto == null) {
            throw new RuntimeException("Produto não pode ser nulo.");
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
