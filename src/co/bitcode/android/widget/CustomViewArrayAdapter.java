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

package co.bitcode.android.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Provides some facilities for Array Adapters which must use custom View(s) to display data.
 * 
 * @param <T>
 *        Type of objects to adapt.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class CustomViewArrayAdapter<T> extends ArrayAdapter<T> {
    private final LayoutInflater layoutInflater;

    /**
     * Constructor.
     * 
     * @param context
     *        Parent <code>Context</code>.
     * @param list
     *        Objects to display.
     * @since 1.0.0
     */
    public CustomViewArrayAdapter(final Context context, final List<T> list) {
        super(context, 0, list);

        this.layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Inflate layout.
     * 
     * @param layoutId
     *        Layout resource ID.
     * @return Inflated View.
     * @since 1.0.0
     */
    protected View inflate(final int layoutId) {
        return inflate(layoutId, null);
    }

    /**
     * Inflate layout and attach it to parent View.
     * 
     * @param layoutId
     *        Layout resource ID.
     * @param parent
     *        Parent View.
     * @return Inflated View.
     * @since 1.0.0
     */
    protected View inflate(final int layoutId, final ViewGroup parent) {
        return this.layoutInflater.inflate(layoutId, parent, false);
    }
}
