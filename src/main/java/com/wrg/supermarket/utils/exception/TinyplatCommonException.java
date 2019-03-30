package com.wrg.supermarket.utils.exception;

public class TinyplatCommonException extends RuntimeException {

	private static final long serialVersionUID = -33397051406882139L;

	private String code;

	public TinyplatCommonException(String code, String msg)

	{
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
