package com.capgemini.webapp.spring.initialize;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.capgemini.webapp.spring.config.web.WebMvcConfig;
/**
 * @author awarhoka
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebMvcConfig.class };
	}
 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}
 
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

}
