/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restservice;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author igor.simoes
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApprovalResponse {
    private double approved_value;
    private int approval_status_code;
    
    public ApprovalResponse() {
        
    }
    
    public double getApprovedValue() {
            return approved_value;
    }
        
    public int getApprovalStatusCode() {
            return approval_status_code;
    }
    
    public void setapApprovedValue(double value) {
            this.approved_value = value;
    }
        
    public void setApprovalStatusCode(int status_code) {
            this.approval_status_code = status_code;
    }
    
    @Override
    public String toString(){
        return "ApprovalStatus{value='"+this.approved_value+"', status_code='"+this.approval_status_code+"'}";
    }
    
}
