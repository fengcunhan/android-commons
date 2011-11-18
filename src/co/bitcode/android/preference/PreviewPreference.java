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

package co.bitcode.android.preference;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import co.bitcode.android.R;

/**
 * A {@link DialogPreference} which shows the current persisted value in the summary field.
 * <p>
 * It provides hooks for subclasses to store new values which will be called at appropriate times.
 * </p>
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class PreviewPreference extends DialogPreference {
    /**
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @see android.view.View#View(Context, AttributeSet)
     */
    public PreviewPreference(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @param defStyle
     *        The default style.
     * @see android.view.View#View(Context, AttributeSet, int)
     */
    public PreviewPreference(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected View onCreateView(final ViewGroup parent) {
        refreshSummary();

        return super.onCreateView(parent);
    }

    @Override
    protected void onDialogClosed(final boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            persistValue();

            refreshSummary();
        }
    }

    /**
     * Sub-classes must implement this method in order to show a string representation of persisted
     * data.
     * 
     * @return Persisted value as string.
     * @since 1.0.0
     */
    protected abstract String getPersistedValueAsString();

    /**
     * Callback method invoked when the editing dialog is being closed, a good time to persist this
     * field's value.
     * 
     * @since 1.0.0
     */
    protected abstract void persistValue();

    private void refreshSummary() {
        final String value = getPersistedValueAsString();

        if (value == null) {
            setSummary(R.string.previewpreference_notSet);
        } else {
            setSummary(value);
        }
    }
}
