/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice;

/**
 *
 * @author igor.simoes
 */
public class LoanResponse {
        private final long id;
	private final String cpf;
        private final double requested_value;
        private final double approved_value;
        private final int approval_status_code;

	public LoanResponse(long id, String cpf, double requested_value, double approved_value, int approval_status_code) {
		this.id = id;
		this.cpf = cpf;
                this.requested_value = requested_value;
                this.approved_value = approved_value;
                this.approval_status_code = approval_status_code;
	}

	public long getId() {
		return id;
	}
        
        public String getCPF(){
                return cpf;
        }

	public double getRequestedValue() {
		return requested_value;
	}
        
        public double getApprovedValue() {
		return approved_value;
	}
        
        public double getApprovalStatusCode() {
		return approval_status_code;
	}
        
        @Override
        public String toString(){
            return "Id: "+getId()+", ReqValue:"+getRequestedValue()+", CPF: "+getCPF()+", AppVal: "+getApprovedValue()+", AppStatusCode: "+getApprovalStatusCode();
        }
}
