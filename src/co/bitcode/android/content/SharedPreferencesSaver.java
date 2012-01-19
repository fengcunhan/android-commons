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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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
public abstract class SharedPreferencesSaver {
    private final Map<String, Object> fieldValueMap = new HashMap<String, Object>();
    private final SharedPreferences preferences;

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @since 1.0.0
     */
    public SharedPreferencesSaver(final Context context) {
        this.preferences = ContextUtils.getPrivatePreferences(context, getPreferenceName());

        reload();
    }

    /**
     * Reloads the shared preferences in the backing map.
     * 
     * @since 1.0.0
     */
    public void reload() {
        this.fieldValueMap.putAll(this.preferences.getAll());
    }

    /**
     * Saves values into the {@link SharedPreferences} backing this object.
     * 
     * @since 1.0.0
     */
    public void save() {
        final Editor editor = this.preferences.edit();

        for (final String key : this.fieldValueMap.keySet()) {
            final Object value = this.fieldValueMap.get(key);

            if (value != null) {
                store(editor, key, value);
            }
        }

        editor.commit();
    }

    /**
     * A.
     * 
     * @param <V>
     *        Type of returned value.
     * @param key
     *        What to look for.
     * @return The value, or <code>null</code> if it could not be found.
     */
    @SuppressWarnings("unchecked")
    public <V> V get(final String key) {
        return (V) this.fieldValueMap.get(key);
    }

    /**
     * @param key
     *        What to look for.
     * @return <code>true</code> if we have it, <code>false</code> otherwise.
     */
    public boolean has(final String key) {
        return this.fieldValueMap.containsKey(key);
    }

    /**
     * Puts a value.
     * 
     * @param key
     *        The key.
     * @param value
     *        The value.
     */
    public void put(final String key, final Object value) {
        this.fieldValueMap.put(key, value);
    }

    /**
     * @return Desired name of the (private) shared preference.
     * @since 1.0.0
     */
    protected abstract String getPreferenceName();

    private void store(final Editor editor, final String key, final Object value) {
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else {
            throw new RuntimeException("Trying to store an unsupported data type");
        }
    }
}
