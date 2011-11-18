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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A {@link TransitionImageView} with rounded corners.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class RoundedCornerImageView extends ImageView {
    private static final Paint DEFAULT_PAINT = new Paint();
    private static final int DEFAULT_ROUNDNESS = 5;
    private final float density = getContext().getResources().getDisplayMetrics().density;
    private float roundness = DEFAULT_ROUNDNESS * this.density;
    private Bitmap cache;

    /**
     * @param context
     *        The {@link Context}.
     * @see android.view.View#View(Context)
     */
    public RoundedCornerImageView(final Context context) {
        super(context);
    }

    /**
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @see android.view.View#View(Context, AttributeSet)
     */
    public RoundedCornerImageView(final Context context, final AttributeSet attrs) {
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
    public RoundedCornerImageView(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void draw(final Canvas canvas) {
        if (this.cache == null) {
            this.cache = getComposedBitmap();
        }

        canvas.drawBitmap(this.cache, 0, 0, DEFAULT_PAINT);
    }

    /**
     * @return Resolution independent roundness.
     */
    public float getRoundness() {
        return this.roundness / this.density;
    }

    /**
     * Sets resolution independent roundness.
     * 
     * @param roundness
     *        Resolution-dependent roundness in pixels.
     */
    public void setRoundness(final float roundness) {
        this.roundness = roundness * this.density;
    }

    @Override
    public void setImageBitmap(final Bitmap bm) {
        super.setImageBitmap(bm);
        invalidateCache();
    }

    @Override
    public void setImageDrawable(final Drawable drawable) {
        super.setImageDrawable(drawable);
        invalidateCache();
    }

    @Override
    public void setImageResource(final int resId) {
        super.setImageResource(resId);
        invalidateCache();
    }

    private Bitmap getComposedBitmap() {
        final int height = getWidth();
        final int width = getHeight();
        final Bitmap composedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Bitmap originalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas composedCanvas = new Canvas(composedBitmap);
        final Canvas originalCanvas = new Canvas(originalBitmap);
        final Paint paint = new Paint();

        // Setup
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        // Draw original picture to a temporary bitmap
        super.draw(originalCanvas);

        // Clear canvas and make it transparent
        composedCanvas.drawARGB(0, 0, 0, 0);

        // Draw rounded rectangle
        composedCanvas.drawRoundRect(new RectF(0, 0, width, height), this.roundness,
                this.roundness, paint);

        // Compose the rounded corner mask with the original bitmap
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        composedCanvas.drawBitmap(originalBitmap, 0, 0, paint);

        return composedBitmap;
    }

    private void invalidateCache() {
        this.cache = null;
        invalidate();
    }
}
