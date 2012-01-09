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

package co.bitcode.android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An {@link ImageView} which shows a fade-in animation between the old and new drawable.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class TransitionImageView extends ImageView {
    private int duration;

    /**
     * @param context
     *        The {@link Context}.
     * @see android.view.View#View(Context)
     */
    public TransitionImageView(final Context context) {
        super(context);

        init(context);
    }

    /**
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @see android.view.View#View(Context, AttributeSet)
     */
    public TransitionImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        init(context);
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
    public TransitionImageView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    @Override
    public void setImageBitmap(final Bitmap bm) {
        if (getDrawable() == null) {
            super.setImageBitmap(bm);
        } else {
            setImageDrawable(new BitmapDrawable(bm));
        }

    }

    @Override
    public void setImageResource(final int resId) {
        if (getDrawable() == null) {
            super.setImageResource(resId);
        } else {
            setImageDrawable(getResources().getDrawable(resId));
        }
    }

    @Override
    public void setImageDrawable(final Drawable drawable) {
        if (getDrawable() == null) {
            super.setImageDrawable(drawable);
        } else {
            final TransitionDrawable transitionDrawable = createTransition(drawable);

            super.setImageDrawable(transitionDrawable);
            transitionDrawable.startTransition(this.duration);
        }
    }

    private void init(final Context context) {
        this.duration = context.getResources().getInteger(
                android.R.integer.config_longAnimTime);
    }

    private TransitionDrawable createTransition(final Drawable newDrawable) {
        final Drawable originalDrawable = getDrawable();
        final Drawable[] composedDrawables = new Drawable[2];

        composedDrawables[0] = originalDrawable;
        composedDrawables[1] = newDrawable;

        return new TransitionDrawable(composedDrawables);
    }
}
