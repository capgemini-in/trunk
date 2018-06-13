package com.capgemini.webapp.security.config;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncodeDecode {

	private static SecretKeySpec secretKeySpec;
	private static final String CHAR_ENCODE_STD = "UTF-8";
	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
	private static final String ENCRYPTION_SCHEME = "AES";
	private static final String MD_ALGORITHM = "SHA-256";

	private static void setSaltKey(String saltKey) {
		try {
			byte[] salt = saltKey.getBytes(CHAR_ENCODE_STD);
			MessageDigest messageDigest = MessageDigest.getInstance(MD_ALGORITHM);
			byte[] digest = messageDigest.digest(salt);
			byte[] key = Arrays.copyOf(digest, 16);
			secretKeySpec = new SecretKeySpec(key, ENCRYPTION_SCHEME);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static String encrypt(String strToEncrypt, String saltKey) {
		try {
			setSaltKey(saltKey);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(CHAR_ENCODE_STD)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public static String decrypt(String strToDecrypt, String secret) {
		try {
			setSaltKey(secret);
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)),CHAR_ENCODE_STD);
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static void main(String[] args) {
		String originalString = "Ashish";
		final String secretKey = "AshishTestSaltKey";

		String encryptedString = EncodeDecode.encrypt(originalString, secretKey);
		String decryptedString = EncodeDecode.decrypt(encryptedString, secretKey);

		System.out.println("Origional Value :"+originalString);
		System.out.println("Encrypted Value :"+encryptedString);
		System.out.println("Decrypted Value :"+decryptedString);
	}
}