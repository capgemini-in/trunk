package com.capgemini.webapp.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TempPasswordGenerator {

	  public static String generateTempPassword () {   
	    String tempPassword = RandomStringUtils.random(1,"ABCDEFGHIJKLMNOPQRSTUVWXYZ") 
	        + RandomStringUtils.random(5, "abcdefghijklmnopqrstuvwxyz")                        
	        + RandomStringUtils.random(1, "0123456789")
	        + RandomStringUtils.random(1, "@#$%!");
	  
	    return tempPassword;
	  } 
	  public static void main(String[] args) {
		System.out.println(generateTempPassword());
	}
	}