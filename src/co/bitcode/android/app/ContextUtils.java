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

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;

/**
 * Provides convenience methods to accomplish the most common tasks.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class ContextUtils {
    private ContextUtils() {
    }

    /**
     * Opens a private {@link SharedPreferences} file.
     * 
     * @param context
     *        The {@link Context}.
     * @param name
     *        Desired preferences file.
     * @return The single SharedPreferences instance that can be used to retrieve and modify the
     *         preference values.
     * @since 1.0.0
     * @see Context#getSharedPreferences(String, int)
     */
    public static SharedPreferences getPrivatePreferences(final Context context, final String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * Return the handle to the connectivity manager service.
     * 
     * @param context
     *        The {@link Context}.
     * @return The {@link ConnectivityManager}.
     * @since 1.0.0
     */
    public static ConnectivityManager getConnectivityManager(final Context context) {
        return getService(context, Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Return the handle to the input manager service.
     * 
     * @param context
     *        The {@link Context}.
     * @return The {@link ConnectivityManager}.
     * @since 1.0.0
     */
    public static InputMethodManager getInputMethodManager(final Context context) {
        return getService(context, Context.INPUT_METHOD_SERVICE);
    }

    /**
     * Return the handle to the layout inflater service.
     * 
     * @param context
     *        The {@link Context}.
     * @return The {@link LayoutInflater}.
     * @since 1.0.0
     */
    public static LayoutInflater getLayoutInflater(final Context context) {
        return getService(context, Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Return the handle to the location manager service.
     * 
     * @param context
     *        The {@link Context}.
     * @return The {@link LocationManager}.
     * @since 1.0.0
     */
    public static LocationManager getLocationManager(final Context context) {
        return getService(context, Context.LOCATION_SERVICE);
    }

    /**
     * Return the handle to the notification manager service.
     * 
     * @param context
     *        The {@link Context}.
     * @return The {@link NotificationManager}.
     * @since 1.0.0
     */
    public static NotificationManager getNotificationManager(final Context context) {
        return getService(context, Context.NOTIFICATION_SERVICE);
    }

    /**
     * Return the handle to a system-level service by name.
     * 
     * Throws a {@link NullPointerException} in case the service could not be found.
     * 
     * @param <T>
     *        Service type.
     * @param context
     *        The {@link Context}.
     * @param name
     *        Name of the service to get.
     * @return The requested service.
     * @since 1.0.0
     * @see Context#getSystemService(String)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getService(final Context context, final String name) {
        final Object service = context.getSystemService(name);

        if (service == null) {
            throw new NullPointerException("Unable to find service: " + name);
        } else {
            return (T) service;
        }
    }
}
