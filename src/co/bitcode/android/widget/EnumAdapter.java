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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

/**
 * An adapter which shows user-friendly strings instead of raw {@link Enum} values.
 * 
 * @param <E>
 *        Enumeration type.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class EnumAdapter<E extends Enum<E>> extends CustomViewArrayAdapter<E> {
    private final CharSequence[] mFriendlyStrings;

    /**
     * @see http://www.youtube.com/watch?v=wDBM6wVEO70
     * @author Lorenzo Villani
     */
    private static class ViewHolder {
        /* CHECKSTYLE IGNORE ALL CHECKS NEXT LINE */
        public TextView textView;
    }

    /**
     * @see http://www.youtube.com/watch?v=wDBM6wVEO70
     * @author Lorenzo Villani
     */
    private static class DropdownViewHolder {
        /* CHECKSTYLE IGNORE ALL CHECKS NEXT LINE */
        public CheckedTextView checkedTextView;
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param list
     *        List of {@link Enum} values.
     * @param friendlyStrings
     *        An array which contains friendly strings.
     * @since 1.0.0
     */
    public EnumAdapter(final Context context, final List<E> list,
            final CharSequence[] friendlyStrings) {
        super(context, list);

        assert (friendlyStrings.length == list.size());

        this.mFriendlyStrings = friendlyStrings;
    }

    /* CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES */
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflate(android.R.layout.simple_spinner_item);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(this.mFriendlyStrings[position]);

        return convertView;
    }

    /* CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES */
    @Override
    public View getDropDownView(final int position, View convertView, final ViewGroup parent) {
        final DropdownViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflate(android.R.layout.simple_spinner_dropdown_item);

            viewHolder = new DropdownViewHolder();
            viewHolder.checkedTextView = (CheckedTextView) convertView
                    .findViewById(android.R.id.text1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DropdownViewHolder) convertView.getTag();
        }

        viewHolder.checkedTextView.setText(this.mFriendlyStrings[position]);

        return convertView;
    }
}
