package com.capgemini.webapp.security.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/*
* This class registers springSecurityFilterChain Filter for all URLs.
* Because we're using Spring MVC the SecurityConfig will be loaded
* in MvcWebApplicationInitializer instead of here
*/
public class SecurityWebApplicationInitializer 
      extends AbstractSecurityWebApplicationInitializer {

}