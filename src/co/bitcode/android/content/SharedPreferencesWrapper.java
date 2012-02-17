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

package co.bitcode.android.content;

import android.content.Context;
import android.content.SharedPreferences;

import co.bitcode.android.app.ContextUtils;

/**
 * Provides some plumbing to provide a POJO-like interface to a {@link SharedPreferences}-backed
 * object.
 * 
 * NOTE: This class' API is unstable and the benefits it brings are questionable. It can be removed
 * and changed at any time, even if it's included in a stable release of the library.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class SharedPreferencesWrapper {
    private final SharedPreferences preferences;

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @since 1.0.0
     */
    public SharedPreferencesWrapper(final Context context) {
        this.preferences = ContextUtils.getPrivatePreferences(context, getPreferenceName());
    }

    /**
     * Clears any stored preferences.
     * 
     * @since 1.0.0
     */
    public void clear() {
        this.preferences.edit().clear().commit();
    }

    public SharedPreferences getPreferences() {
        return this.preferences;
    }

    /**
     * Retrieves a value from the underlying {@link SharedPreferences}.
     * 
     * @param <T>
     *        Return type.
     * @param key
     *        Lookup key.
     * @return The stored value, or <code>null</code> if it could not be found.
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    protected <T> T get(final String key) {
        return (T) this.preferences.getAll().get(key);
    }

    /**
     * Retrieves a value from the underlying {@link SharedPreferences} or, if it doesn't exist,
     * returns a default value.
     * 
     * @param <T>
     *        Return type.
     * @param key
     *        Lookup key.
     * @param defaultValue
     *        The default value to return in case no value is associated with the key.
     * @return The stored value of <code>defaultValue</code> if it could not be found.
     * @since 1.0.0
     */
    protected <T> T get(final String key, final T defaultValue) {
        final T value = get(key);

        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }

    /**
     * @param key
     *        What to look for.
     * @return <code>true</code> if we have it, <code>false</code> otherwise.
     */
    protected boolean has(final String key) {
        return this.preferences.contains(key);
    }

    /**
     * Puts a value.
     * 
     * @param key
     *        The key.
     * @param value
     *        The value.
     * @param <T>
     *        Value type.
     */
    protected <T> void put(final String key, final T value) {
        store(key, value);
    }

    /**
     * @return Desired name of the (private) shared preference.
     * @since 1.0.0
     */
    protected abstract String getPreferenceName();

    private void store(final String key, final Object value) {
        if (value instanceof Boolean) {
            this.preferences.edit().putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            this.preferences.edit().putFloat(key, (Float) value).commit();
        } else if (value instanceof Integer) {
            this.preferences.edit().putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            this.preferences.edit().putLong(key, (Long) value).commit();
        } else if (value instanceof String) {
            this.preferences.edit().putString(key, (String) value).commit();
        } else {
            throw new RuntimeException("Trying to store an unsupported data type");
        }
    }
}
