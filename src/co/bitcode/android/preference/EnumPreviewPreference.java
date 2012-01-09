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

package co.bitcode.android.preference;

import java.util.Arrays;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import co.bitcode.android.R;
import co.bitcode.android.widget.EnumAdapter;

/**
 * A preview preference that uses enum values as a data source.
 * 
 * @param <E>
 *        Enum type.
 * @author Lorenzo Villani
 */
public class EnumPreviewPreference<E extends Enum<E>> extends PreviewPreference {
    private static final int PADDING = 5;

    private CharSequence[] entries;
    private Class<E> valuesType;
    private E[] values;
    private Spinner spinner;
    private String defaultValue;
    private int mSelectedItem;

    /**
     * @see android.view.View#View(Context, AttributeSet)
     */
    // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
    public EnumPreviewPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    /**
     * @see android.view.View#View(Context, AttributeSet, int)
     */
    // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
    public EnumPreviewPreference(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        init(attrs);
    }

    @Override
    protected String getPersistedValueAsString() {
        final String currentValue = getPersistedString(this.defaultValue);

        for (int i = 0; i < this.values.length; i++) {
            if (this.values[i].toString().equals(currentValue)) {
                this.mSelectedItem = i;

                break;
            }
        }

        return this.entries[this.mSelectedItem].toString();
    }

    @Override
    protected void onBindDialogView(final View view) {
        super.onBindDialogView(view);

        EnumAdapter<E> adapter;

        adapter = new EnumAdapter<E>(getContext(), Arrays.asList(this.values), this.entries);

        this.spinner.setAdapter(adapter);
        this.spinner.setSelection(this.mSelectedItem);
    }

    @Override
    protected View onCreateDialogView() {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (this.spinner.getParent() != null) {
            ((ViewGroup) this.spinner.getParent()).removeAllViews();
        }

        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
        linearLayout.addView(this.spinner, layoutParams);

        return linearLayout;
    }

    @Override
    protected void persistValue() {
        persistString(this.spinner.getSelectedItem().toString());
    }

    @SuppressWarnings("unchecked")
    private void init(final AttributeSet attrs) {
        final TypedArray ta = getContext()
                .obtainStyledAttributes(attrs, R.styleable.EnumPreference);
        final CharSequence[] entries = ta.getTextArray(R.styleable.EnumPreference_entries);

        this.entries = entries;
        this.defaultValue = ta.getString(R.styleable.EnumPreference_defaultValue);
        this.spinner = new Spinner(getContext());
        this.spinner.setId(android.R.id.edit);

        try {
            this.values = (E[]) Class.forName(ta.getString(R.styleable.EnumPreference_values))
                    .getEnumConstants();
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
