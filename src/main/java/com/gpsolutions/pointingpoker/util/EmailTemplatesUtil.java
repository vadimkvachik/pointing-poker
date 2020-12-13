package com.gpsolutions.pointingpoker.util;

public class EmailTemplatesUtil {
    public static String FOOTER = "<i><br/><br/>Pointing-poker®</i>";
    public static String USER_ACTIVATION_SUBJECT = "Please confirm your email address";
    public static String USER_ACTIVATION_TEXT =
        "<i>Dear user,<br/><br/>You have been registered in the pointing-poker system" +
        "<br/>To verify your email address, click on the link below:<br/><br/>${link}<br/><br/>" +
        "If you have not registered in our system, please disregard this email.</i>" + FOOTER;
    public static String SUCCESSFUL_USER_ACTIVATION_SUBJECT = "Email address successfully confirmed";
    public static String SUCCESSFUL_USER_ACTIVATION_TEXT =
        "Your email address has been successfully confirmed.<br>Now you can log in using your email and password" +
        FOOTER;


    public static String getTextWithLink(final String text, final String controllerPath, final String linkName) {
        final String link = "<a href=\"" + controllerPath + "\">" + linkName + "</a>";
        return text.replace("${link}", link);
    }
}
