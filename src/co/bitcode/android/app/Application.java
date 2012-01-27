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

package co.bitcode.android.app;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.pm.PackageManager.NameNotFoundException;

import co.bitcode.android.util.LoggerFactory;

/**
 * Extends {@link android.app.Application} to provide utility methods.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class Application extends android.app.Application {
    private static final MessageFormat MESSAGE_FORMAT = new MessageFormat("");
    private static Application instance;
    private static Logger logger;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        logger = LoggerFactory.fromContext(this);
    }

    /**
     * @return The <code>versionCode</code> as declared in <code>AndroidManifest.xml</code> for this
     *         application.
     * @since 1.0.0
     */
    public int getVersionCode() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (final NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The <code>versionName</code> as declared in <code>AndroidManifest.xml</code> for this
     *         application.
     * @since 1.0.0
     */
    public String getVersionName() {
        try {
            return getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (final NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Application getInstance() {
        return instance;
    }

    public static Logger getLogger() {
        return logger;
    }

    /**
     * @param logLevel
     *        The level of the specified message.
     * @param message
     *        The message to log.
     * @see Logger#log(Level, String)
     */
    public static void log(final Level logLevel, final String message) {
        logger.log(logLevel, message);
    }

    /**
     * @param logLevel
     *        The level of the specified message.
     * @param message
     *        The message to log.
     * @param params
     *        The parameter array associated with the event that is logged.
     * @see Logger#log(Level, String, Object[])
     */
    public static void log(final Level logLevel, final String message, final Object... params) {
        synchronized (MESSAGE_FORMAT) {
            MESSAGE_FORMAT.applyPattern(message);

            logger.log(logLevel, MESSAGE_FORMAT.format(params), params);
        }
    }

    /**
     * @param logLevel
     *        The level of the specified message.
     * @param message
     *        The message to log.
     * @param thrown
     *        The <code>Throwable</code> object associated with the event that is logged.
     * @see Logger#log(Level, String, Throwable)
     */
    public static void log(final Level logLevel, final String message, final Throwable thrown) {
        logger.log(logLevel, message, thrown);
    }
}
