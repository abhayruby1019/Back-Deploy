package com.example.fn;

import java.util.ArrayList;
import java.util.List;

public class SignatureModel {

    private String signature;
	private List<String> errors = new ArrayList<String>();
	
	public String getSignature() {
		return signature;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	public void addError(String error) {
		
		this.errors.add(error);
	}
	
}
