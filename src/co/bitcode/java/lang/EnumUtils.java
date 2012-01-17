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

package co.bitcode.java.lang;

import android.content.Context;

/**
 * Helper methods when dealing with {@link Enum} values.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class EnumUtils {
    private EnumUtils() {
    }

    /**
     * Converts an enumeration constant to its respective user-friendly string representation.
     * 
     * @param <T>
     *        Enumeration type.
     * @param context
     *        The {@link Context}.
     * @param arrayResId
     *        String array resource id.
     * @param enumType
     *        The enum type.
     * @return The UI string.
     * @since 1.0.0
     */
    public static <T extends Enum<T>> String toUiString(final Context context,
            final int arrayResId, final Enum<T> enumType) {
        final String[] uiStrings = context.getResources().getStringArray(arrayResId);
        final Enum<?>[] enumConstants = enumType.getClass().getEnumConstants();
        int i = 0;

        for (i = 0; i < uiStrings.length; i++) {
            if (enumType.equals(enumConstants[i])) {
                return uiStrings[i];
            }
        }

        throw new IllegalStateException("Could not find the corresponding UI string.");
    }
}
