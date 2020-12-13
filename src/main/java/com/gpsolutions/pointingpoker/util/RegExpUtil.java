package com.gpsolutions.pointingpoker.util;

import java.util.regex.Pattern;

public class RegExpUtil {
    public static final String EMAIL_REGEXP =
        "(?i)(^[\\p{L}0-9!#$%&’*+/=?^_`{|}~-]+(\\.[\\p{L}0-9!#$%&’*+/=?^_`{|}~-]+)*@([\\p{L}0-9_-])+(\\.[\\p{L}0-9_-]+)*(\\.[\\p{L}]{2,})$)|(^$)";
    public static final String PASSWORD_REGEXP = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,64}$";

    public static boolean isNotValidByRegexp(final String regexp, final String text) {
        return !Pattern.compile(regexp).matcher(text).matches();
    }
}