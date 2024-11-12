package com.example.produto_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.produto_spring.entities.Produto;
import com.example.produto_spring.services.ProdutoService;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) throws Exception{
        Produto usuarioCriado = service.create(produto);
        return new ResponseEntity<Produto>(usuarioCriado, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Produto> put(@RequestBody Produto produto) throws Exception{
        Produto usuarioAtualizada = service.update(produto);
        return ResponseEntity.ok(usuarioAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getList(){
        List<Produto> lista = service.list();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getRead(@PathVariable Long id){
        Produto usuarioEncontrada = service.read(id);
        return ResponseEntity.ok(usuarioEncontrada);
    }
}
