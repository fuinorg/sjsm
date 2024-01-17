package org.fuin.sjsm;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the {@link Config} class.
 */
class ConfigTest {

    @Test
    void testExtractRecipients() {
        assertThat(Config.extractRecipients("")).isEmpty();
        assertThat(Config.extractRecipients("a@b.com")).containsOnly("a@b.com");
        assertThat(Config.extractRecipients("a@b.com;nobody@nowhere.com"))
                .containsOnly("a@b.com", "nobody@nowhere.com");
    }

    @Test
    void testConvertToInternetAddress() throws AddressException {
        assertThat(Config.convertToInternetAddress(Collections.emptyList())).isEmpty();
        assertThat(Config.convertToInternetAddress(List.of("a@b.com", "nobody@nowhere.com")))
                .containsOnly(new InternetAddress("a@b.com"), new InternetAddress("nobody@nowhere.com"));
    }

}
