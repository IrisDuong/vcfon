package com.vcfon.notification.mail;

import java.nio.charset.StandardCharsets;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
@EnableAsync
public class MailService {

	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;
	
	
	public MailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
		super();
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}


	@Async
	public void sendHtmlMail(MailRequest request){
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
		
			Context context = new Context();
			context.setVariables(request.variables());
			String html = templateEngine.process("mail/".concat(request.templateName()), context);

			helper.setFrom(request.from());
			helper.setTo(request.to());
			helper.setSubject(request.subject());
			helper.setText(html, true);
			
			mailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
