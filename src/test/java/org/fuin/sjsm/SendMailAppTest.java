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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

// CHECKSTYLE:OFF
public class SendMailAppTest {

    private static SimpleSmtpServer dumbster;

    @Before
    public void setUp() throws IOException {
        dumbster = SimpleSmtpServer.start(SimpleSmtpServer.AUTO_SMTP_PORT);
    }

    @After
    public void tearDown() {
        dumbster.close();
    }

    @Test
    public void testSend() {

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

}
// CHECKSTYLE:ON
