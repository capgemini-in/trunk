package com.capgemini.webapp.security;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PasswordEncryptionTest {

  @Test
  public void passwordEncryptionTests () {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String rawPwd = "admin";    
    String encodedPwd = encoder.encode(rawPwd);
    
    // verify that the encoder is generating a valid hash
    Assert.assertTrue(encoder.matches(rawPwd, encodedPwd), "unable to match password");
    
    // known-good hashed password for "admin"
    String hashedPwd = "$2a$10$t7NTbcI5m5txfKrwTH6G5uSmyByr98baCERdoTQbwIJiAGSwDc1sy";    
    Assert.assertTrue(encoder.matches(rawPwd, hashedPwd), "unable to match password");
    
    // negative test - replace y's with z's and re-run the match
    hashedPwd = hashedPwd.replace("y", "z");
    Assert.assertFalse(encoder.matches(rawPwd, hashedPwd), "passwords should not match but do");    
  }
   
}
