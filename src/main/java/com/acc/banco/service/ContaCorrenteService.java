package com.acc.banco.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.banco.model.Cliente;
import com.acc.banco.model.ContaCorrente;
import com.acc.banco.model.Transferencia;
import com.acc.banco.repository.ClienteRepository;
import com.acc.banco.repository.ContaCorrenteRepository;
import com.acc.banco.service.exception.BalanceException;
import com.acc.banco.service.exception.ObjectNotFoundException;

@Service
public class ContaCorrenteService {

	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	// Salvar contas
	@Transactional
	public ContaCorrente save(ContaCorrente conta) {
//		conta.setSaldo(new BigDecimal(0));
		return contaCorrenteRepository.save(conta);
	}
	
	// Lista todas as contas
	public List<ContaCorrente> listarContas() {
		return contaCorrenteRepository.findAll();
	}

	// buscar conta por ID
	public ContaCorrente findId(Long id) {
		Optional<ContaCorrente> conta = contaCorrenteRepository.findById(id);
		return conta.orElseThrow(() -> new ObjectNotFoundException("Código não encontrado! Id: " + id));
	}

	public ContaCorrente saque(BigDecimal valor, long idCliente) {
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		ContaCorrente contaCorrente = new ContaCorrente();
		if (cliente.isPresent()) {
			contaCorrente = contaCorrenteRepository.findByCliente(cliente.get());
		}

		if (contaCorrente.getSaldo().subtract(valor).doubleValue() < 0) {
			throw new BalanceException("Saldo insuficiente para o saque.");
		}
		
		contaCorrente.setSaldo(contaCorrente.getSaldo().subtract(valor));

		return contaCorrenteRepository.save(contaCorrente);

	}

	public ContaCorrente deposito(BigDecimal valor, long idCliente) {
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		ContaCorrente contaCorrente = new ContaCorrente();
		if (cliente.isPresent()) {
			contaCorrente = contaCorrenteRepository.findByCliente(cliente.get());
		}

		contaCorrente.setSaldo(contaCorrente.getSaldo().add(valor));

		return contaCorrenteRepository.save(contaCorrente);

	}

	@Transactional
	public Transferencia transferencia(Transferencia transferencia) {
		ContaCorrente contaOrigem = contaCorrenteRepository.findByAgenciaAndConta(transferencia.getContaOrigem().getAgencia(), transferencia.getContaOrigem().getConta());
		ContaCorrente contaDestino = contaCorrenteRepository.findByAgenciaAndConta(transferencia.getContaDestino().getAgencia(), transferencia.getContaDestino().getConta());
		transferencia.setContaOrigem(saque(transferencia.getValor(), contaOrigem.getCliente().getId()));
		transferencia.setContaDestino(deposito(transferencia.getValor(), contaDestino.getCliente().getId()));
		return transferencia;
	}

	public ContaCorrente delete(String agencia, String conta) {
		ContaCorrente contaCorrente = contaCorrenteRepository.findByAgenciaAndConta(agencia, conta);
		contaCorrenteRepository.delete(contaCorrente);
		return contaCorrente;
	}


}