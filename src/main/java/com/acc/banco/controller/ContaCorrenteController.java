package com.acc.banco.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acc.banco.model.Cliente;
import com.acc.banco.model.ContaCorrente;
import com.acc.banco.model.Transferencia;
import com.acc.banco.service.ContaCorrenteService;

import javax.validation.Valid;

@RequestMapping("api/conta")
@RestController
public class ContaCorrenteController {

	@Autowired
	private ContaCorrenteService contaCorrenteService;

	@PostMapping("/novo")
	public ResponseEntity<ContaCorrente> save(@Valid @RequestBody ContaCorrente conta) {
		ContaCorrente contaSalva = contaCorrenteService.save(conta);

		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}

	@GetMapping("/all")
	public List<ContaCorrente> findAll() {
		List<ContaCorrente> list = contaCorrenteService.listarContas();
		return list;

	}
	
	@PutMapping("/saque/{valor}/idCliente/{idCliente}")
	public ResponseEntity<ContaCorrente> saque(@Valid @PathVariable("valor") BigDecimal valor, @PathVariable("idCliente") int idCliente) {
		ContaCorrente contaCorrente = contaCorrenteService.saque(valor, idCliente);
		return ResponseEntity.ok(contaCorrente);

	}
	
	@PutMapping("/deposito/{valor}/idCliente/{idCliente}")
	public ResponseEntity<ContaCorrente> deposito(@PathVariable("valor") BigDecimal valor, @PathVariable("idCliente") int idCliente) {
		ContaCorrente contaCorrente = contaCorrenteService.deposito(valor, idCliente);
		return ResponseEntity.ok(contaCorrente);

	}

	@PostMapping("/transferencia")
	public ResponseEntity<Transferencia> transferencia(@Valid @RequestBody Transferencia transferencia) {
		Transferencia transferenciaFinal = contaCorrenteService.transferencia(transferencia);

		return ResponseEntity.ok(transferenciaFinal);
	}
	
	@DeleteMapping("/agencia/{agencia}/conta/{conta}")
    public ResponseEntity<ContaCorrente> delete(@PathVariable("agencia") String agencia, @PathVariable("conta") String conta){
    	ContaCorrente contaCorrente = contaCorrenteService.delete(agencia, conta);
    	
    	return ResponseEntity.ok(contaCorrente);
    }



}
