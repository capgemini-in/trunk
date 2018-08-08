package com.capgemini.webapp.service.impl;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.capgemini.webapp.service.api.EmailService;
import com.capgemini.webapp.service.api.model.EmailModel;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;



@Service("emailService")
public class EmailServiceImpl implements EmailService {
	

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class.getName());
	
	public String sendEmail(EmailModel emailModel) {
		
		logger.info("EmailServiceImpl::sendEmail: Method Initiated");
		Mail mail = new Mail();
		Personalization personalization = new Personalization();
		String toEmail = emailModel.getToEmail();
		String ccEmail = emailModel.getCcEmail();
		StringTokenizer st = new StringTokenizer(toEmail,",");
		while (st.hasMoreTokens()) {  
	         personalization.addTo(new Email(st.nextToken()));
	     }
		st = null;
		st = new StringTokenizer(ccEmail,",");
		while (st.hasMoreTokens()) { 
			
	         personalization.addCc(new Email(st.nextToken()));
	         personalization.setSubject(emailModel.getSubject());
	         Content content = new Content("text/plain",emailModel.getContent());
	         mail.setFrom(new Email(emailModel.getFromEmail()));
	         mail.addPersonalization(personalization);
	         mail.addContent(content);
		  	 SendGrid sg = new SendGrid("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");
		  	 Request req = new Request();

			try {
				req.setMethod(Method.POST);
				req.setEndpoint("mail/send");
				req.setBody(mail.build());
				com.sendgrid.Response res = sg.api(req);
	
				
			} catch (IOException ex) {
				logger.error("EmailServiceImpl::sendEmail():: Exception"+ex.getMessage());
				ex.printStackTrace();
		   
			}//end of catch
		}//end of while
		return "";
		
	}
	public String sendEmailWithAttachments(String reportFolderPath, ServletContext servletContext) {
		
		logger.info("EmailServiceImpl::sendEmailWithAttachments:: Method Initiated");

		return "";
	}
	
	/**
	 * Send Email for Dashbaord Updates
	 * @param propertydata2 
	 * @throws JobExecutionException 
	 */
	public String sendEmail() {
		
		logger.info("EmailServiceImpl::sendEmail: Method Initiated");
		Mail mail = new Mail();
		Personalization personalization = new Personalization();
		String toEmail = "pallavi.b.patil@capgemini.com";
		String ccEmail = "divyam.vyas@capgemini.com";
		StringTokenizer st = new StringTokenizer(toEmail,",");
		while (st.hasMoreTokens()) {  
	         personalization.addTo(new Email(st.nextToken()));
	     }
		st = null;
		st = new StringTokenizer(ccEmail,",");
		while (st.hasMoreTokens()) { 
	         personalization.addCc(new Email(st.nextToken()));
	     }
		//personalization.addCc(new Email(propertyData.getProperty("CCEmail")));
		//personalization.addCc(new Email(propertyData.getProperty("ccEmail")));

		personalization.setSubject("Test Email");
		Content content = new Content("text/plain","Test Email from User Management Application");
		mail.setFrom(new Email("pallavi.b.patil@capgemini.com"));
		mail.addPersonalization(personalization);
		mail.addContent(content);
		
		SendGrid sg = new SendGrid("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");
		Request req = new Request();

		try {
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			com.sendgrid.Response res = sg.api(req);
			
		} catch (IOException ex) {
			logger.error("EmailServiceImpl::sendEmail():: Exception"+ex.getMessage());
			ex.printStackTrace();
			try {
				Thread.sleep(300000);//sleep for 5 mins
			} catch (InterruptedException e1) {
				logger.error("InterruptedException "+e1.getMessage());
				e1.printStackTrace();
			} 
         //   JobExecutionException e2 = new JobExecutionException(ex);
         
		}
		return "";
		
	}

	@Override
	public String sendEmailWithAttachments(String reportFolderPath) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		EmailServiceImpl email=new EmailServiceImpl();
		email.sendEmail();
	}
}
