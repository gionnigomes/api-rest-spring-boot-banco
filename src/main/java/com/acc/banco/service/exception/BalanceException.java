package com.acc.banco.service.exception;

public class BalanceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BalanceException (String msg) {
		super(msg);
	}
}
