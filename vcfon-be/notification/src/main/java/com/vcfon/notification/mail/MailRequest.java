package com.vcfon.notification.mail;

public record MailRequest(String from, String to,String subject,String templateName, java.util.Map<String, Object> variables){
}
