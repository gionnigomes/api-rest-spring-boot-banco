package com.acc.banco.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.banco.model.Cliente;
import com.acc.banco.model.ContaCorrente;
import com.acc.banco.repository.ClienteRepository;
import com.acc.banco.repository.ContaCorrenteRepository;
import com.acc.banco.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ContaCorrenteRepository contaCorrenteRepository;

    //buscar todos os clientes
    public List<Cliente> findAll (){

        return clienteRepository.findAll();
    }

    //buscar clientes por ID
    public Cliente findId (Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        return cliente.orElseThrow(() -> new ObjectNotFoundException(
                "Código não encontrado! Id: " + id ));
    }

    //salvanod cliente.
    @Transactional
    public Cliente save (Cliente cliente) {

        return clienteRepository.save(cliente);
    }

    //realizando update do cliente
    @Transactional
    public Cliente update (Cliente cliente){
        Cliente newCliente = findId(cliente.getId());
        updateData(newCliente, cliente);
        return clienteRepository.save(newCliente);
    }

    //deletando cliente
    @Transactional
    public Cliente delete (Long id){
        Cliente cliente = findId(id);
<<<<<<< HEAD
=======
//        ContaCorrente contaCorrente = contaCorrenteRepository.findByCliente(cliente);
//        contaCorrenteRepository.delete(contaCorrente);
>>>>>>> 1b5cde87205d6ca14361ab179f932b8c7dae0e38
        clienteRepository.deleteById(cliente.getId());

        return cliente;

    }

    //Metodo para atualizar os campos que serão realizado o update.
    private void updateData (Cliente newCliente, Cliente cliente){
        newCliente.setNome(cliente.getNome());
        newCliente.setFone(cliente.getFone());
        newCliente.setCpf(cliente.getCpf());
    }

	public Optional<Cliente> findAgenciaConta(String agencia, String conta) {
		ContaCorrente contaCorrente = contaCorrenteRepository.findByAgenciaAndConta(agencia, conta);
		
		return clienteRepository.findById(contaCorrente == null ? -1 : contaCorrente.getCliente().getId());
	}

	public Optional<Cliente> findCliente(long id) {
		return clienteRepository.findById(id);
	}
}
