package com.nova.messenger.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Legacy Java Utility class for Time operations.
 * Demonstrates Java + Kotlin interoperability.
 */
public class TimeUtils {
    
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String formatLastSeen(boolean isOnline) {
        if (isOnline) return "Online";
        return "Last seen recently";
    }
}
