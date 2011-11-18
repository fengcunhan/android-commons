/*
 * Android Utilities
 * Copyright (C) 2010, 2011  Lorenzo Villani
 *
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package co.bitcode.android.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Converts Date objects to their equivalent ISO Date string representation.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class ISODateFormatter {
    /**
     * Pattern used to convert from/to ISO Date format.
     */
    public static final DateFormat ISO_FORMAT = new SimpleDateFormat("yyyyMMdd");

    /**
     * Pattern used to convert from/to ISO Date And Time format.
     */
    public static final DateFormat ISO_FULL_FORMAT = new SimpleDateFormat("yyyyMMddhhmmss");

    static {
        ISO_FULL_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private ISODateFormatter() {
    }

    /**
     * Formats a {@link Date} object to ISO Date + Time string.
     * 
     * @param date
     *        Date to format
     * @return Full ISO Date + Time string.
     * @since 1.0.0
     */
    public static String format(final Date date) {
        return ISO_FULL_FORMAT.format(date);
    }

    /**
     * Formats a {@link Date} to ISO Date string.
     * 
     * @param date
     *        Date to format
     * @return ISO Date string.
     * @since 1.0.0
     */
    public static String formatDate(final Date date) {
        return ISO_FORMAT.format(date);
    }

    /**
     * Parse an ISO Date from a String.
     * 
     * @param date
     *        ISO Date string.
     * @return {@link java.util.Date} object representation of input string.
     * @since 1.0.0
     */
    public static Date parseISODate(final String date) {
        try {
            return ISO_FORMAT.parse(date);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Parse ISO Date and Time from a String.
     * 
     * @param dateAndTime
     *        ISO Date and Time string
     * @return {@link java.util.Date} representation of input string.
     * @since 1.0.0
     */
    public static Date parseISODateAndTime(final String dateAndTime) {
        try {
            return ISO_FULL_FORMAT.parse(dateAndTime);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
