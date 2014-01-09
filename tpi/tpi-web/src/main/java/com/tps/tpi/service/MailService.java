package com.tps.tpi.service;

import com.tps.tpi.email.MailServiceException;

import java.util.Map;
import java.util.Locale;

/**
 * User: Bjorn Harvold
 * Date: Apr 24, 2007
 * Time: 11:50:44 AM
 */
public interface MailService {
    void sendActivationEmail(String email, String activationId, Locale l) throws MailServiceException;

    void sendPlainEmail(String template, Map<String, Object> params) throws MailServiceException;

    void sendMIMEEmail(String template, Map<String, Object> params, Map<String, String> imageAssets,
                       Map<String, String> attachments, Map<String, String> headers) throws MailServiceException;

    Boolean isAvailable();

    public void sendPasswordReminderEmail(String email, String newPassword, Locale l) throws MailServiceException;
}
