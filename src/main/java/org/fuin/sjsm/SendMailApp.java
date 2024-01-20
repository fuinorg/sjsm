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

import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Main command line application that sends a single mail message via SMTPS to a
 * single receiver.
 */
public final class SendMailApp {

    /**
     * Sends a message.
     * 
     * @param config
     *            Configuration to use.
     */
    public void send(final Config config) {

        try {
            final Session session;
            if (config.isNoauth()) {
                session = Session.getInstance(config.createSessionProperties());
            } else {
                session = Session.getInstance(config.createSessionProperties(),
                        config.createAuthenticator());
            }

            final MimeMessage msg = config.createMimeMessage(session);
            final Transport transport = session.getTransport(config.getTransportType());
            try {
                transport.connect();
                msg.saveChanges();
                transport.sendMessage(msg, msg.getAllRecipients());
            } finally {
                transport.close();
            }
            System.out
                    .println("Successfully sent message '" + config.getSubject()
                            + "' to '" + config.getReceiver() + "'");
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to send mail with subject '"
                    + config.getSubject() + "' to " + config.getReceiver(), ex);
        }

    }

    private static void ensurePasswordIsSet(final CmdLineParser parser , final Config config) throws CmdLineException {
        if (config.getPw() == null && config.getEnvPw() == null) {
            throw new CmdLineException(parser, Messages.MISSING_PASSWORD_OPTION);
        }
        if (config.getEnvPw() != null && System.getenv(config.getEnvPw()) == null) {
            throw new CmdLineException(parser, Messages.PASSWORD_ENV_VAR_NOT_SET, config.getEnvPw());
        }
    }


    /**
     * Main application method.
     * 
     * @param args
     *            Command line arguments.
     */
    public static void main(final String[] args) {

        final Config config = new Config();
        final CmdLineParser parser = new CmdLineParser(config);
        try {
            parser.parseArgument(args);
            ensurePasswordIsSet(parser, config);
            new SendMailApp().send(config);
            System.exit(0);
        } catch (final CmdLineException ex) {
            System.err.println(ex.getMessage());
            System.err.println("java -jar sjsm.jar <arguments>");
            parser.printUsage(System.err);
            System.err.println();
            System.exit(1);
        } catch (final RuntimeException ex) {
            ex.printStackTrace(System.err);
            System.exit(2);
        }

    }

}
