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

package co.bitcode.android.util;

import java.util.logging.Logger;

import android.content.Context;
import android.content.res.Resources;

/**
 * A LoggerFactory instance can be used to create {@link Logger} objects pre-configured with the
 * name of application.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class LoggerFactory {
    private LoggerFactory() {
    }

    /**
     * Obtains a pre-configured {@link Logger} object with prepends the application name to outgoing
     * log messages.
     * 
     * It requires a string resource called <code>app_name</code> to be present.
     * 
     * @param context
     *        The {@link Context}.
     * @return A {@link Logger} named after the application name.
     * @since 1.0.0
     */
    public static Logger fromContext(final Context context) {
        final Resources resources = context.getResources();
        final String packageName = context.getPackageName();
        final int identifier = resources.getIdentifier("app_name", "string", packageName);
        final String applicationName = resources.getString(identifier);

        return Logger.getLogger(applicationName);
    }
}
