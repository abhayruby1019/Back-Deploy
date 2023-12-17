package com.example.fn;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;

import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.util.encoders.Base64;

public class PKCS7Gen {
	
	public static SignatureModel getSignature(MudraAPIModel mudraModel) {
		
		SignatureModel sm = new SignatureModel();
		
		try {
			
			KeyStore keystore = KeyStore.getInstance("jks");

			// InputStream targetStream = new FileInputStream("D:\\output.jks");

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream targetStream = loader.getResourceAsStream("output.jks");
			
			
			try {
				char[] password=mudraModel.getPassword().toCharArray();
				keystore.load(targetStream, password);
			} catch (IOException e) {
			} finally {

			}
			
			
			Enumeration e = keystore.aliases();
			String alias = "";

			if(e!=null)
			{
				while (e.hasMoreElements())
				{
					String  n = (String)e.nextElement();
					if (keystore.isKeyEntry(n))
					{
						alias = n;
					}
				}
			}
			PrivateKey privateKey=(PrivateKey) keystore.getKey(alias, mudraModel.getPassword().toCharArray());
			X509Certificate myPubCert=(X509Certificate) keystore.getCertificate(alias);
			
			String data = mudraModel.getUserId() + "^^" + mudraModel.getPan();
			
			byte[] dataToSign=data.getBytes();
			CMSSignedDataGenerator sgen = new CMSSignedDataGenerator();
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider ());
			sgen.addSigner(privateKey, myPubCert,CMSSignedDataGenerator.DIGEST_SHA1);
			Certificate[] certChain =keystore.getCertificateChain(alias);
			ArrayList certList = new ArrayList();
			CertStore certs = null;
			for (int i=0; i < certChain.length; i++)
				certList.add(certChain[i]); 
			sgen.addCertificatesAndCRLs(CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));
			CMSSignedData csd = sgen.generate(new CMSProcessableByteArray(dataToSign),true, "BC");
			byte[] signedData = csd.getEncoded();
			byte[] signedData64 = Base64.encode(signedData); 
			// FileOutputStream out = new FileOutputStream("signature4.txt");
			// out.write(signedData64);
			// out.close();

			String sData = new String(signedData64, StandardCharsets.UTF_8);
			System.out.println("sData -------------->" +sData);
			
			sm.setSignature(sData);
			sm.addError("");
			
		} catch (KeyStoreException kse) { 
			
			sm.setSignature("");
			sm.addError("Password is incorrect for the given .jks file");
			kse.printStackTrace();
		
		} catch (Exception e) {
			sm.setSignature("");
			sm.addError(e.getMessage());
			e.printStackTrace();
		}
		
		return sm;
	}

}
