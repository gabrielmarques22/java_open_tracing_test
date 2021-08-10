package com.example.restservice;

public class LoanRequest {

	//private final long id;
	private final String cpf;
        private final double value;

	public LoanRequest(String cpf, double value) {
	//	this.id = id;
		this.cpf = cpf;
                this.value = value;
	}

	/*public long getId() {
		return id;
	}*/
        
        public String getCPF(){
                return cpf;
        }

	public double getValue() {
		return value;
	}
}
