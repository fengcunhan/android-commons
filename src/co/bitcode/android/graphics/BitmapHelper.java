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

package co.bitcode.android.graphics;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Operations on {@link Bitmap}s.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class BitmapHelper {
    private BitmapHelper() {
    }

    /**
     * Scale a {@link Bitmap}.
     * 
     * @param bitmap
     *        {@link Bitmap} to be scaled.
     * @param width
     *        New width.
     * @param height
     *        New height.
     * @return Scaled {@link Bitmap}.
     * @since 1.0.0
     */
    public static Bitmap scale(final Bitmap bitmap, final int width, final int height) {
        final Matrix matrix = new Matrix();
        final int originalHeight = bitmap.getHeight();
        final int originalWidth = bitmap.getWidth();

        matrix.postScale(((float) width) / originalWidth, ((float) height) / originalHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, false);
    }
}
