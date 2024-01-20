package org.fuin.sjsm;

import org.kohsuke.args4j.Localizable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Message constants.
 */
public enum Messages implements Localizable {

    /** Neither 'pw' nor 'envPw' argument is set. */
    MISSING_PASSWORD_OPTION,

    /** The environment variable from 'envPw' argument is not set. */
    PASSWORD_ENV_VAR_NOT_SET;

    @Override
    public String formatWithLocale(final Locale locale, final Object... args) {
        final ResourceBundle localized = ResourceBundle.getBundle("sjsm-messages", locale);
        return MessageFormat.format(localized.getString(name()), args);
    }

    @Override
    public String format(final Object... args) {
        return formatWithLocale(Locale.getDefault(), args);
    }

}
