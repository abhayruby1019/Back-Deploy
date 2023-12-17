package com.example.fn;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloFunction {

    public static void main(String[] args) {
        // MudraAPIModel mam = new MudraAPIModel("emudhra", "V0136601", "BYYPS8883G");

        // check basic validations
		SignatureModel sm = new SignatureModel();
		sm.setSignature("");

       // MudraAPIModel mam = new MudraAPIModel("123456", "", "ETAPP3954B");
		MudraAPIModel mam = new MudraAPIModel();
		mam.setPan("DIHPA0139J");
		mam.setPassword("123456");
		mam.setUserId("V0275601");
        if (!new HelloFunction().validateInput(mam, sm)) {
            
            System.out.println("Signature---------->" +new HelloFunction().handleRequest(mam).getSignature());
        } else {
            for (String error : sm.getErrors() ) {
                
                System.out.println("Erorrrrrrrrrrrrrr>" +error);
            }
        }
    }

    public SignatureModel handleRequest(MudraAPIModel mudraModel) {
        // check basic validations
		SignatureModel sm = new SignatureModel();
		sm.setSignature("");
		
		
        // Validate PAN number
        if (!validateInput(mudraModel, sm)) {
            return PKCS7Gen.getSignature(mudraModel);
        } else {
            return sm;
        }
    }


    private boolean isValidPanNumber(String panNumber) {
        // Add your PAN number validation logic here
        // For simplicity, let's assume a valid PAN number has a length of 10 characters
        return panNumber != null && panNumber.length() == 10;
    }
	
	
	
	private static boolean isValidPanCardNo(String panCardNo)
    {
 
        // Regex to check valid PAN Card number.
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
 
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
 
        // If the PAN Card number
        // is empty return false
        if (panCardNo == null)
        {
            return false;
        }
 
        // Pattern class contains matcher() method
        // to find matching between given
        // PAN Card number using regular expression.
        Matcher m = p.matcher(panCardNo);
 
        // Return if the PAN Card number
        // matched the ReGex
        return m.matches();
    }
	
	
	private boolean validateInput(MudraAPIModel mudraModel, SignatureModel sm) {
		boolean validationFailed = false;
		
		if (mudraModel.getPan() == null || (mudraModel.getPan() != null && (mudraModel.getPan().toUpperCase().equals("") || !isValidPanNumber(mudraModel.getPan().toUpperCase()) || !isValidPanCardNo(mudraModel.getPan().toUpperCase())))) {
            sm.addError("Invalid PAN number.");
			validationFailed = true;
        } 
		
		if (mudraModel.getPassword() == null || (mudraModel.getPassword() != null && mudraModel.getPassword().equals(""))) {
			sm.addError("Invalid Password");
			validationFailed = true;
		}
		
		if (mudraModel.getUserId() == null || (mudraModel.getUserId() != null && mudraModel.getUserId().equals(""))||(mudraModel.getUserId() != null && !mudraModel.getUserId().equals("V0275601"))) {
			sm.addError("Invalid User Id");
			validationFailed = true;
		}
		
		return validationFailed;
	}
}
