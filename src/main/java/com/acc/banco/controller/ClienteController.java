package com.acc.banco.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acc.banco.model.Cliente;
import com.acc.banco.service.ClienteService;

@RequestMapping("api/cliente")
@RestController
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/novo")
    public ResponseEntity<Cliente> save (@RequestBody Cliente cliente){
        Cliente clienteSalvo = clienteService.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable("id") long id){
    	Cliente cliente = clienteService.delete(id);
    	
    	return ResponseEntity.ok(cliente);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cliente>> findAll(){
        List<Cliente> list = clienteService.findAll();

        return ResponseEntity.ok(list);

    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findCliente(@PathVariable("id") long id){
        Optional<Cliente> cliente = clienteService.findCliente(id);

        if (cliente.isPresent()) {
        	return ResponseEntity.ok(cliente.get());
        }else {
        	return ResponseEntity.notFound().build();
        }
        

    }
    
    @GetMapping("/agencia/{agencia}/conta/{conta}")
    public ResponseEntity<Cliente> findAgenciaConta(@PathVariable("agencia") String agencia, @PathVariable("conta") String conta){
        Optional<Cliente> cliente = clienteService.findAgenciaConta(agencia, conta);
        
        if(cliente.isPresent()) {
        	return ResponseEntity.ok(cliente.get());
        }else {
        	return ResponseEntity.notFound().build();
        }
        

    }
}
