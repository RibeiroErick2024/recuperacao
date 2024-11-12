package com.example.produto_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.produto_spring.entities.Produto;


public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
