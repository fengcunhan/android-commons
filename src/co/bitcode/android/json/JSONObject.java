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

package co.bitcode.android.json;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

/**
 * Wraps the {@link org.json.JSONObject} class so that each operation doesn't throw a checked
 * exception.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class JSONObject extends org.json.JSONObject {
    /**
     * Constructor.
     * 
     * Creates a JSONObject with no name/value mappings.
     */
    public JSONObject() {
        super();
    }

    /**
     * Constructor.
     * 
     * Creates a new JSONObject by copying all name/value mappings from the given map.
     * 
     * @param copyFrom
     *        a map whose keys are of type String and whose values are of supported types.
     */
    public JSONObject(final Map<?, ?> copyFrom) {
        super(copyFrom);
    }

    private JSONObject(final String json) throws JSONException {
        super(json);
    }

    // CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES
    private JSONObject(final org.json.JSONObject copyFrom, final String[] names)
            throws JSONException {
        super(copyFrom, names);
    }

    private JSONObject(final JSONTokener readFrom) throws JSONException {
        super(readFrom);
    }

    /**
     * Factory method: Creates a new JSONObject with name/value mappings from the JSON string.
     * 
     * @param json
     *        A JSON-encoded string containing an object.
     * @return A new JSONObject.
     */
    public static JSONObject fromString(final String json) {
        try {
            return new JSONObject(json);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Factory method: Creates a new JSONObject with name/value mappings from the next object in the
     * tokener.
     * 
     * @param tokener
     *        A tokener whose nextValue() method will yield a JSONObject.
     * @return A new JSONObject.
     */
    public static JSONObject fromTokener(final JSONTokener tokener) {
        try {
            return new JSONObject(tokener);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Factory method: Creates a new JSONObject by copying mappings for the listed names from the
     * given object. Names that aren't present in copyFrom will be skipped.
     * 
     * @param copyFrom
     *        Object to copy.
     * @param names
     *        Names to copy.
     * @return A new JSONObject.
     */
    public static JSONObject fromJSONObject(final org.json.JSONObject copyFrom, final String[] names) {
        try {
            return new JSONObject(copyFrom, names);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject accumulate(final String name, final Object value) {
        try {
            return super.accumulate(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object get(final String name) {
        try {
            return super.get(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getBoolean(final String name) {
        try {
            return super.getBoolean(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getDouble(final String name) {
        try {
            return super.getDouble(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getInt(final String name) {
        try {
            return super.getInt(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSONArray getJSONArray(final String name) {
        try {
            return super.getJSONArray(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject getJSONObject(final String name) {
        try {
            return super.getJSONObject(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getString(final String name) {
        try {
            return super.getString(name);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject put(final String name, final boolean value) {
        try {
            return super.put(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject put(final String name, final double value) {
        try {
            return super.put(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject put(final String name, final int value) {
        try {
            return super.put(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject put(final String name, final long value) {
        try {
            return super.put(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject put(final String name, final Object value) {
        try {
            return super.put(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public org.json.JSONObject putOpt(final String name, final Object value) {
        try {
            return super.putOpt(name, value);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSONArray toJSONArray(final JSONArray names) {
        try {
            return super.toJSONArray(names);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString(final int indentSpaces) {
        try {
            return super.toString(indentSpaces);
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
