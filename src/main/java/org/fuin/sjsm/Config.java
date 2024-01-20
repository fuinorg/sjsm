/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.sjsm;

import java.util.*;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.kohsuke.args4j.Option;

/**
 * Configuration for the mail sender.
 */
public final class Config {

    @Option(name = "-host", usage = "Host", metaVar = "HOST", required = true)
    private String host;

    @Option(name = "-port", usage = "Port", metaVar = "PORT", required = true)
    private int port;

    @Option(name = "-user", usage = "User", metaVar = "USER", required = true)
    private String user;

    @Option(name = "-pw", usage = "Password", metaVar = "PW")
    private String pw;

    @Option(name = "-envPw", usage = "Name of an environment variable that contains the password", metaVar = "ENV_PW")
    private String envPw;

    @Option(name = "-from", usage = "Sender", metaVar = "SEND", required = true)
    private String from;

    @Option(name = "-to", usage = "Receiver", metaVar = "RCVR", required = true)
    private String receiver;

    @Option(name = "-subject", usage = "Subject", metaVar = "SUBJ", required = true)
    private String subject;

    @Option(name = "-message", usage = "Text or HTML message", metaVar = "MSG", required = true)
    private String message;

    @Option(name = "-html", usage = "HTML message (otherwise TEXT)")
    private boolean html;

    @Option(name = "-charset", usage = "Message encoding (Defaults to UTF-8)")
    private String charset = "UTF-8";

    @Option(name = "-smtp", usage = "Use smtp (not smtps)")
    private boolean smtp;

    @Option(name = "-timeout", usage = "Timeout millis (defaults to 5 seconds)", metaVar = "MILLIS")
    private int timeout = 5000;

    @Option(name = "-noauth", usage = "SMTP without authentication")
    private boolean noauth;

    @Option(name = "-important", usage = "Send High Priority Email (X-Priority)")
    private boolean important;

    /**
     * Returns the host.
     *
     * @return host.
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the host to a new value.
     *
     * @param host
     *            host.
     */
    public void setHost(final String host) {
        this.host = host;
    }

    /**
     * Returns the port.
     *
     * @return port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the port to a new value.
     *
     * @param port
     *            port.
     */
    public void setPort(final int port) {
        this.port = port;
    }

    /**
     * Returns the user.
     *
     * @return Authentication user.
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user
     *            Authentication user.
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     * Returns the password.
     *
     * @return Authentication password.
     */
    public String getPw() {
        return pw;
    }

    /**
     * Sets the password.
     *
     * @param pw
     *            Authentication password.
     */
    public void setPw(final String pw) {
        this.pw = pw;
    }

    /**
     * Returns the name of an environment variable that contains the password.
     *
     * @return Environment variable with password.
     */
    public String getEnvPw() {
        return envPw;
    }

    /**
     * Sets the name of an environment variable that contains the password.
     *
     * @param envPw
     *            Environment variable to use.
     */
    public void setEnvPw(final String envPw) {
        this.envPw = envPw;
    }

    /**
     * Returns the important (X-Priority) flag.
     *
     * @return {@literal true} if the "X-Priority" should be added, else {@literal false}.
     */
    public boolean getImportant() {
        return important;
    }

    /**
     * Sets the important (X-Priority) flag.
     *
     * @param important
     *            {@literal true} if the "X-Priority" should be added, else {@literal false}.
     */
    public void setImportant(final boolean important) {
        this.important = important;
    }

    /**
     * Returns the sender.
     *
     * @return Sender's email address,
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the sender as Internet address.
     *
     * @return Sender's email Internet address,
     */
    public InternetAddress getFromAddress() {
        try {
            return new InternetAddress(from);
        } catch (final AddressException ex) {
            throw new RuntimeException(
                    "Creating internet address for sender failed: " + from, ex);
        }
    }

    /**
     * Sets the sender.
     *
     * @param from
     *            Sender's email address,
     */
    public void setFrom(final String from) {
        this.from = from;
    }

    /**
     * Returns the receiver.
     *
     * @return Receiver's email address,
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Returns the receiver as Internet address.
     *
     * @return Receiver's email Internet address,
     */
    public InternetAddress getReceiverAddress() {
        try {
            return new InternetAddress(receiver);
        } catch (final AddressException ex) {
            throw new RuntimeException(
                    "Creating internet address for receiver failed: "
                            + receiver,
                    ex);
        }
    }

    /**
     * Sets the receiver.
     *
     * @param receiver
     *            Receiver's email address,
     */
    public void setReceiver(final String receiver) {
        this.receiver = receiver;
    }

    /**
     * Returns the message subject.
     *
     * @return Message subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the message subject.
     *
     * @param subject
     *            Message subject.
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Returns the message body.
     *
     * @return Text or HTML message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message body.
     *
     * @param message
     *            Text or HTML message.
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Determines if the message is a HTML message.
     *
     * @return True if it's an HTML message, else false (text message).
     */
    public boolean isHtml() {
        return html;
    }

    /**
     * Sets the message type.
     *
     * @param html
     *            True for an HTML message, else false (text message).
     */
    public void setHtml(final boolean html) {
        this.html = html;
    }

    /**
     * Returns the message encoding.
     *
     * @return Encoding (Defaults to UTF-8).
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the message encoding.
     *
     * @param charset
     *            Charset to use (will be defaulted to UTF-8 if argument is
     *            <code>null</code>).
     */
    public void setCharset(final String charset) {
        if (charset == null) {
            this.charset = "utf-8";
        } else {
            this.charset = charset;
        }
    }

    /**
     * Returns the content type and encoding.
     *
     * @return Content type and encoding.
     */
    public String getContentTypeAndEncoding() {
        if (html) {
            return getContentType() + "; charset=" + charset
                    + "; format=flowed";
        }
        return getContentType() + "; charset=" + charset;
    }

    /**
     * Returns the content type only.
     *
     * @return Content type.
     */
    public String getContentType() {
        if (html) {
            return "text/html; charset=" + charset;
        }
        return "text/plain; charset=" + charset;
    }

    /**
     * Send via (unsecured) SMTP.
     *
     * @return True for SMTP, else false (SMTPS).
     */
    public boolean isSmtp() {
        return smtp;
    }

    /**
     * Send via (unsecured) SMTP.
     *
     * @param smtp
     *            True for SMTP, else false (SMTPS).
     */
    public void setSmtp(final boolean smtp) {
        this.smtp = smtp;
    }

    /**
     * Returns the timeout in milliseconds.
     *
     * @return Milliseconds until timeout.
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Sets the timeout in milliseconds.
     *
     * @param timeout
     *            Milliseconds until timeout.
     */
    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    /**
     * Authentication disabled.
     *
     * @return True if SMTP without authentication is required.
     */
    public boolean isNoauth() {
        return noauth;
    }

    /**
     * Authentication disabled.
     *
     * @param noauth
     *            True if SMTP without authentication is required.
     */
    public void setNoauth(final boolean noauth) {
        this.noauth = noauth;
    }

    /**
     * Returns the transport type.
     *
     * @return Either "smtp" or "smtps" (default).
     */
    public String getTransportType() {
        if (smtp) {
            return "smtp";
        }
        return "smtps";
    }

    /**
     * Returns the session properties based on the configuration.
     *
     * @return Properties.
     */
    public Properties createSessionProperties() {
        final Properties props = new Properties();
        final String key = getTransportType();
        if (!smtp) {
            props.put("mail.smtps.starttls.enable", "true");
        }
        props.put("mail." + key + ".host", host);
        props.put("mail." + key + ".port", "" + port);
        props.put("mail." + key + ".timeout", "" + timeout);
        props.put("mail." + key + ".sendpartial", "true");
        if (!noauth) {
            props.put("mail." + key + ".auth", "true");
        }
        return props;
    }

    /**
     * Returns an authenticator based on user/pw.
     *
     * @return New authenticator instance.
     */
    public Authenticator createAuthenticator() {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                final String p;
                if (pw == null) {
                    p = System.getenv(envPw);
                } else {
                    p = pw;
                }
                return new PasswordAuthentication(user, p);
            }
        };
    }

    /**
     * Creates a message from the configuration.
     *
     * @param session
     *            Session to create a message for.
     *
     * @return Message connected with the session.
     */
    public MimeMessage createMimeMessage(final Session session) {
        try {
            final MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", getContentTypeAndEncoding());
            msg.addHeader("Content-Transfer-Encoding", "8BIT");
            msg.setFrom(getFromAddress());
            msg.setSubject(subject, getCharset());
            msg.setContent(message, getContentType());
            msg.setSentDate(new Date());
            if (this.getImportant()) {
                msg.setHeader("X-Priority", "1");
            }
            msg.setRecipients(Message.RecipientType.TO, convertToInternetAddress(extractRecipients(getReceiver())));
            return msg;
        } catch (final MessagingException ex) {
            throw new RuntimeException(
                    "Failed to create a message with subject '" + subject
                            + "' for " + receiver,
                    ex);
        }
    }

    /**
     * Splits the string into a list of receivers.
     *
     * @param receiver One or more email addresses separated with a semicolon.
     * @return List of receivers.
     */
    static List<String> extractRecipients(final String receiver) {
        Objects.requireNonNull(receiver, "receiver == null");
        final List<String> recipients = new ArrayList<>();
        final StringTokenizer tok = new StringTokenizer(receiver, ";");
        while (tok.hasMoreTokens()) {
            final String recipient = tok.nextToken().trim();
            if (!recipient.isEmpty()) {
                recipients.add(recipient);
            }
        }
        return recipients;
    }

    /**
     * Converts a list of recipients into an address array.
     *
     * @param recipients List of receivers to convert.
     *
     * @return Array with addresses constructed from the given list.
     */
    static Address[] convertToInternetAddress(List<String> recipients) {
        Objects.requireNonNull(recipients, "recipients == null");
        final List<Address> list = new ArrayList<>();
        for (final String recipient : recipients) {
            if (recipient != null && !recipient.isEmpty()) {
                try {
                    list.add(new InternetAddress(recipient));
                } catch (final AddressException ex) {
                    throw new RuntimeException("Not a valid email address: '" + recipient +  "'", ex);
            }
            }
        }
        return list.toArray(new Address[0]);
    }


}
