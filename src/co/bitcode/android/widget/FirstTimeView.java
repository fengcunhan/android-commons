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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.bitcode.android.R;
import co.bitcode.android.app.ContextUtils;

/**
 * A view that is shown the first time the application is started.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
// XXX: This should not really be a View but an helper which automatically hides and shows
// attached views. This View is scheduled for deletion.
public class FirstTimeView extends RelativeLayout implements OnClickListener {
    private static final int BACKGROUND_COLOR = 0xFFEEEEEC;
    private static final String DEFAULT = "default";
    private static final String PREFERENCES = "firsttimehelp";
    private static final String PREFERENCE_SUPPRESSALL = "suppressAll";

    private SharedPreferences preferences;
    private TextView textView;

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @see android.view.View#View(Context)
     */
    public FirstTimeView(final Context context) {
        super(context);

        init();
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param messageId
     *        The message id.
     */
    public FirstTimeView(final Context context, final String messageId) {
        super(context);

        setTag(messageId);
        init();
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
    public FirstTimeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        init();
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
    public FirstTimeView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    /**
     * Forcefully suppresses all help messages, preventing them to be shown.
     * 
     * @param context
     *        The {@link Context}.
     */
    public static void suppressAll(final Context context) {
        final SharedPreferences preferences = ContextUtils.getPrivatePreferences(context,
                PREFERENCES);
        final Editor editor = preferences.edit();

        editor.putBoolean(PREFERENCE_SUPPRESSALL, true);
        editor.commit();
    }

    /**
     * @param message
     *        The Message.
     */
    public void setMessage(final String message) {
        this.textView.setText(message);
    }

    /**
     * @param resId
     *        Message string id.
     */
    public void setMessage(final int resId) {
        this.textView.setText(resId);
    }

    @Override
    public void onClick(final View v) {
        if (this.equals(v)) {
            setVisibility(View.GONE);
        }
    }

    private void init() {
        final LayoutInflater inflater = ContextUtils.getLayoutInflater(getContext());
        final String messageTag = getMessageTag();
        final int padding = (int) (10 * getContext().getResources().getDisplayMetrics().density);

        setBackgroundColor(BACKGROUND_COLOR);
        setPadding(padding, padding, padding, padding);

        inflater.inflate(R.layout.view_firsttimeview, this);

        this.preferences = ContextUtils.getPrivatePreferences(getContext(), PREFERENCES);
        this.textView = ViewUtils.find(this, R.id.view_firsttimeview_message);

        if (isSuppressed()) {
            setVisibility(View.GONE);
        } else {
            if (!hasShownMessageOnce(messageTag)) {
                setVisibility(View.VISIBLE);
                setOnClickListener(this);
                storeMessageShown(messageTag);
            } else {
                setVisibility(View.GONE);
            }
        }
    }

    private String getMessageTag() {
        final String tag = (String) getTag();

        if (tag == null) {
            return DEFAULT;
        } else {
            return tag;
        }
    }

    private boolean hasShownMessageOnce(final String messageTag) {
        return this.preferences.getBoolean(messageTag, false);
    }

    private void storeMessageShown(final String messageTag) {
        putBoolean(messageTag, true);
    }

    private void putBoolean(final String key, final boolean value) {
        final Editor editor = this.preferences.edit();

        editor.putBoolean(key, value);
        editor.commit();
    }

    private boolean isSuppressed() {
        return this.preferences.getBoolean(PREFERENCE_SUPPRESSALL, false);
    }
}
