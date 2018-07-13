package com.capgemini.webapp.service.api;

import com.capgemini.webapp.service.api.model.EmailModel;

/**
 * This interface handles email functionality
 * @author pallapat
 *
 */
public interface EmailService {

	public String sendEmailWithAttachments(String reportFolderPath);

	public String sendEmail(EmailModel emailModel);
}
