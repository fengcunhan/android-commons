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

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Provides utility methods to convert <code>org.json</code> data types to classes from the
 * Collections library.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class JSONUtil {
    private JSONUtil() {
    }

    /**
     * Converts a {@link JSONArray} of {@link JSONObject} to a {@link List} of {@link JSONObject}s.
     * 
     * @param jsonArray
     *        The JSON array to convert.
     * @return A list which contains all JSON objects contained in the array.
     * @since 1.0.0
     */
    public static List<JSONObject> toList(final JSONArray jsonArray) {
        try {
            final int size = jsonArray.length();
            final List<JSONObject> objects = new ArrayList<JSONObject>(size);

            for (int i = 0; i < size; i++) {
                objects.add(jsonArray.getJSONObject(i));
            }

            return objects;
        } catch (final JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
