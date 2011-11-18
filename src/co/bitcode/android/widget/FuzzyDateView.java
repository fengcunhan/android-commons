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

import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import co.bitcode.android.R;
import co.bitcode.android.util.ISODateFormatter;

/**
 * Interprets an ISO Date string and shows it as a fuzzy string such as "few moments ago",
 * "one hour ago", etc.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class FuzzyDateView extends TextView {
    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
    private static final int WEEK = 7 * DAY;

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @see android.view.View#View(Context)
     */
    public FuzzyDateView(final Context context) {
        super(context);
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @see android.view.View#View(Context, AttributeSet)
     */
    public FuzzyDateView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @param defStyle
     *        The default style.
     * @see android.view.View#View(Context, AttributeSet, int)
     */
    public FuzzyDateView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Shows the fuzzy date string.
     * 
     * @param sourceDate
     *        Starting date.
     * @since 1.0.0
     */
    public void setText(final Date sourceDate) {
        final long delta = Math.abs(new Date().getTime() - sourceDate.getTime());

        super.setText(getFormattedQuantity(delta));
    }

    /**
     * Shows the fuzzy date string.
     * 
     * @param isoDate
     *        ISO Date and Time string.
     * @since 1.0.0
     */
    public void setText(final String isoDate) {
        setText(ISODateFormatter.parseISODateAndTime(isoDate));
    }

    private String getFormattedQuantity(final long delta) {
        return String.format(getFormatString(delta), getQuantity(delta));
    }

    private int getQuantity(final long delta) {
        if (delta < HOUR) {
            return (int) delta / MINUTE;
        } else if (delta < DAY) {
            return (int) delta / HOUR;
        } else if (delta < WEEK) {
            return (int) delta / DAY;
        } else {
            return 1;
        }
    }

    private String getFormatString(final long delta) {
        if (delta < MINUTE) {
            return getContext().getString(R.string.view_fuzzydate_seconds);
        } else if (delta < HOUR) {
            return getContext().getString(R.string.view_fuzzydate_minutes);
        } else if (delta < DAY) {
            return getContext().getString(R.string.view_fuzzydate_hours);
        } else if (delta < WEEK) {
            return getContext().getString(R.string.view_fuzzydate_days);
        } else {
            return getContext().getString(R.string.view_fuzzydate_other);
        }
    }
}
