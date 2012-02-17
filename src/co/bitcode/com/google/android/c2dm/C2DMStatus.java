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

package co.bitcode.com.google.android.c2dm;

import android.content.Context;

import co.bitcode.android.content.SharedPreferencesWrapper;

/**
 * Stores Google's Cloud to Device Messaging Framework status for current application.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class C2DMStatus extends SharedPreferencesWrapper {
    private static final String KEY_IS_REGISTERED = "isRegistered";

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     */
    public C2DMStatus(final Context context) {
        super(context);
    }

    public boolean isRegistered() {
        return get(KEY_IS_REGISTERED, false);
    }

    // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
    public void setRegistered(final boolean isRegistered) {
        put(KEY_IS_REGISTERED, isRegistered);
    }

    @Override
    protected String getPreferenceName() {
        return "c2dm";
    }
}
