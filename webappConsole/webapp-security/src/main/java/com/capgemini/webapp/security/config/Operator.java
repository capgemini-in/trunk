package com.capgemini.webapp.security.config;

import java.security.MessageDigest;

import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Arrays;
 
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.binary.Base64;

public class Operator
{
    
	private final String algo="PBEWITHSHA256AND256BITAES-CBC-BC";
	//private final String algo="EncodeDecode/ECB/NoPadding";
    
	/*static {
        Security.addProvider(new BouncyCastleProvider());
    }*/
    public String encrypt(String txtPassword, String saltKey)
    {
    	String encrypted = null;
    	try
        {
            SecretKeyFactory secretKeyFactory=SecretKeyFactory.getInstance(algo);
            KeySpec keySpec=new PBEKeySpec(txtPassword.toCharArray());
            SecretKey secretKey=secretKeyFactory.generateSecret(keySpec);
            
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            md.update(txtPassword.getBytes());
            byte[] digest=md.digest();
            byte[] salt=Arrays.copyOf(digest, txtPassword.getBytes().length);
            AlgorithmParameterSpec aps=new PBEParameterSpec(salt, 20);
            Cipher cipher=Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, aps);
            encrypted = Base64.encodeBase64String(cipher.doFinal(txtPassword.getBytes("UTF-8")));
            
            //encrypted = cipher.doFinal(txtPassword.getBytes(), 0, txtPassword.getBytes().length).toString();
            //encrypted = new String(cipher.doFinal(txtPassword.getBytes()));
            System.out.println("encryptedPassword :"+encrypted);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    	return encrypted;
    }
     
    public String decrypt(String encryptedPassword,String saltKey)
    {
    	String decrypted=null;
    	try
        {
            KeySpec ks=new PBEKeySpec(encryptedPassword.toCharArray());
            SecretKeyFactory skf=SecretKeyFactory.getInstance(algo);
            SecretKey key=skf.generateSecret(ks);
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            md.update(encryptedPassword.getBytes());
            byte[] digest=md.digest();
            byte[] salt=Arrays.copyOf(digest, encryptedPassword.getBytes().length);
            AlgorithmParameterSpec aps=new PBEParameterSpec(salt, 20);
            Cipher cipher=Cipher.getInstance(algo);
            cipher.init(Cipher.DECRYPT_MODE, key, aps);
            decrypted = new String(cipher.doFinal(Base64.decodeBase64(encryptedPassword)),"UTF-8");
            //decrypted= cipher.doFinal(encryptedPassword.getBytes(), 0, encryptedPassword.getBytes().length).toString();
           
            //decrypted = new String(cipher.doFinal(encryptedPassword.getBytes()));
            //Base64.encodeBase64String(encrypted);
            System.out.println("decryptedPassword :"+decrypted);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    	return decrypted;
    }
    public static void main(String[] args) {
    	Operator operator = new Operator();
        String encrypted= operator.encrypt("Ashish", "AshishTestAshishTest");
        String dycrypted= operator.decrypt(encrypted, "AshishTestAshishTest");
	}
}  