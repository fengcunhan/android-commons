/*
 * Android Utilities
 * Copyright (C) 2010, 2011, 2012  Lorenzo Villani
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

package co.bitcode.android.net;

import java.util.regex.Pattern;

/**
 * Validates email addresses.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class EmailValidator {
    // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
    private static final String PATTERN_STRING = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

    private EmailValidator() {
    }

    /**
     * Checks whether the email address is valid.
     * 
     * @param emailAddress
     *        Email address string.
     * @return <code>true</code> whether the email address is valid, <code>false</code> otherwise.
     * @since 1.0.0
     */
    public static boolean isValid(final String emailAddress) {
        return PATTERN.matcher(emailAddress).matches();
    }
}
