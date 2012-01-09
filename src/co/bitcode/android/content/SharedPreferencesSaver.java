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
    private final Map<String, Object> fieldValueMap;
    private final SharedPreferences preferences;

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @since 1.0.0
     */
    public SharedPreferencesSaver(final Context context) {
        this.fieldValueMap = getMappings();
        this.preferences = ContextUtils.getPrivatePreferences(context, getPreferenceName());

        validateMappings();
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
            put(editor, key, this.fieldValueMap.get(key));
        }

        editor.commit();
    }

    public Map<String, Object> getFieldValueMap() {
        return this.fieldValueMap;
    }

    /**
     * Callback method invoked when the mapping between this shared preference's keys and its values
     * is being initialized. The contents of the map are checked for <code>null</code> values after
     * this callback method has been invoked.
     * 
     * @return Mapping between preference keys and their default values.
     * @since 1.0.0
     */
    protected abstract Map<String, Object> getMappings();

    /**
     * @return Desired name of the (private) shared preference.
     * @since 1.0.0
     */
    protected abstract String getPreferenceName();

    private void put(final Editor editor, final String key, final Object value) {
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

    private void validateMappings() {
        for (final String key : this.fieldValueMap.keySet()) {
            if (this.fieldValueMap.get(key) == null) {
                throw new NullPointerException(key.concat(" maps to a nul value"));
            }
        }
    }
}
