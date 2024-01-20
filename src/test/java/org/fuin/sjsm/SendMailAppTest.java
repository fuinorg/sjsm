/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved.
 * http://www.fuin.org/
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.sjsm;

import static com.github.stefanbirkner.systemlambda.SystemLambda.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

/**
 * Test for the {@link SendMailApp} class.
 */
class SendMailAppTest {

    private static SimpleSmtpServer dumbster;

    @BeforeEach
    void setUp() throws IOException {
        dumbster = SimpleSmtpServer.start(SimpleSmtpServer.AUTO_SMTP_PORT);
    }

    @AfterEach
    void tearDown() {
        dumbster.close();
    }

    @Test
    void testSendSingleReceiver() {

        // PREPARE
        final String subject = "Test message";
        final String msg = "<html><body><h1>test</h1></body></html>";
        final String sender = "test@fuin.org";
        final String receiver = "other@fuin.org";

        final Config config = new Config();
        config.setHost("localhost");
        config.setPort(dumbster.getPort());
        config.setUser("myaccount");
        config.setPw("mypw");
        config.setFrom(sender);
        config.setReceiver(receiver);
        config.setSubject(subject);
        config.setMessage(msg);
        config.setHtml(true);
        config.setCharset("utf-8");
        config.setSmtp(true);
        config.setTimeout(1000);
        config.setNoauth(true);

        // TEST
        new SendMailApp().send(config);

        // VERIFY
        final List<SmtpMessage> emails = dumbster.getReceivedEmails();
        assertThat(emails).hasSize(1);
        SmtpMessage email = emails.get(0);
        assertThat(email.getHeaderValue("Subject")).isEqualTo(subject);
        assertThat(email.getBody()).isEqualTo(msg);
        assertThat(email.getHeaderValue("From")).isEqualTo(sender);
        assertThat(email.getHeaderValue("To")).isEqualTo(receiver);

    }

    @Test
    void testSendMultipleReceiver() {

        // PREPARE
        final String subject = "Test message";
        final String msg = "<html><body><h1>test</h1></body></html>";
        final String sender = "test@fuin.org";
        final String receiver = "other@fuin.org;not-existing@fuin.org";

        final Config config = new Config();
        config.setHost("localhost");
        config.setPort(dumbster.getPort());
        config.setUser("myaccount");
        config.setPw("mypw");
        config.setFrom(sender);
        config.setReceiver(receiver);
        config.setSubject(subject);
        config.setMessage(msg);
        config.setHtml(false);
        config.setCharset("utf-8");
        config.setSmtp(true);
        config.setTimeout(1000);
        config.setNoauth(true);

        // TEST
        new SendMailApp().send(config);

        // VERIFY
        final List<SmtpMessage> emails = dumbster.getReceivedEmails();
        assertThat(emails).hasSize(1);
        SmtpMessage email = emails.get(0);
        assertThat(email.getHeaderValue("Subject")).isEqualTo(subject);
        assertThat(email.getBody()).isEqualTo(msg);
        assertThat(email.getHeaderValue("From")).isEqualTo(sender);
        assertThat(email.getHeaderValue("To")).isEqualTo("other@fuin.org, not-existing@fuin.org");

    }

    @Test
    void testMissingPasswordOption() throws Exception {

        final String[] args = new String[]{
                "-host", "localhost",
                "-port", "" + dumbster.getPort(),
                "-user", "myaccount",
                "-from", "does-not@matter.com",
                "-to", "not@used.com",
                "-subject", "Whatever",
                "-message", "None"
        };

        final AtomicLong exitCode = new AtomicLong();
        final String systemErr = tapSystemErr(() -> {
            exitCode.set(catchSystemExit(() -> {
                SendMailApp.main(args);
            }));
        });
        assertThat(systemErr).contains("A password or an environment variable with the password is mandatory");
        assertThat(exitCode.get()).isEqualTo(1);

    }

    @Test
    void testPwOption() throws Exception {

        final String[] args = new String[]{
                "-host", "localhost",
                "-port", "" + dumbster.getPort(),
                "-user", "myaccount",
                "-pw", "abc",
                "-from", "does-not@matter.com",
                "-to", "not@used.com",
                "-subject", "Whatever",
                "-message", "None",
                "-smtp"
        };

        final AtomicLong exitCode = new AtomicLong();
        final String systemOut = tapSystemOut(() -> {
            exitCode.set(catchSystemExit(() -> {
                SendMailApp.main(args);
            }));
        });
        assertThat(systemOut).contains("Successfully sent message 'Whatever' to 'not@used.com");
        assertThat(exitCode.get()).isEqualTo(0);

    }

    @Test
    void testEnvPwOption() throws Exception {

        final String[] args = new String[]{
                "-host", "localhost",
                "-port", "" + dumbster.getPort(),
                "-user", "myaccount",
                "-envPw", "MAIL_PW_TEST",
                "-from", "does-not@matter.com",
                "-to", "not@used.com",
                "-subject", "Whatever",
                "-message", "None",
                "-smtp"
        };

        final AtomicLong exitCode = new AtomicLong();
        final AtomicReference<String> systemOut = new AtomicReference<>();
        withEnvironmentVariable("MAIL_PW_TEST", "abc").execute(() -> {
            systemOut.set(tapSystemOut(() -> {
                exitCode.set(catchSystemExit(() -> {
                    SendMailApp.main(args);
                }));
            }));
        });
        assertThat(systemOut.get()).contains("Successfully sent message 'Whatever' to 'not@used.com");
        assertThat(exitCode.get()).isEqualTo(0);

    }

    @Test
    void testMissingEnvPassword() throws Exception {

        final String[] args = new String[]{
                "-host", "localhost",
                "-port", "" + dumbster.getPort(),
                "-user", "myaccount",
                "-envPw", "NOT_EXISTING_PW_ENV_VAR",
                "-from", "does-not@matter.com",
                "-to", "not@used.com",
                "-subject", "Whatever",
                "-message", "None"
        };

        final AtomicLong exitCode = new AtomicLong();
        final String systemErr = tapSystemErr(() -> {
            exitCode.set(catchSystemExit(() -> {
                SendMailApp.main(args);
            }));
        });
        assertThat(systemErr).contains("The environment variable NOT_EXISTING_PW_ENV_VAR is not set");
        assertThat(exitCode.get()).isEqualTo(1);

    }

}
