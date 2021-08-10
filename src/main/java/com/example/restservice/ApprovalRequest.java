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
public class ApprovalRequest {
    private double value;
    private boolean isRisky;
    private long loan_id;
    
    public ApprovalRequest(double value, boolean isRisky, long loan_id) {
        this.loan_id = loan_id;
        this.isRisky = isRisky;
        this.value = value;
    }
    
    public long getLoanId() {
            return loan_id;
    }
        
    public boolean getIsRisky() {
            return isRisky;
    }
    
    public double getLoanValue() {
            return value;
    }
    
    public void setLoanId(long loand_id) {
            this.loan_id = loand_id;
    }
    
    public void setIsRisky(boolean isRisky) {
            this.isRisky = isRisky;
    }
        
    public void setLoanValue(double value) {
            this.value = value;
    }
    
    @Override
    public String toString(){
        return "{\"value\": \""+this.value+"\", \"isRisky\":\""+this.isRisky+"\", \"loand_id\": \""+this.loan_id+"\"}";
    }
}
