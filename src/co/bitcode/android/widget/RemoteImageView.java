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
import android.net.Uri;
import android.util.AttributeSet;

import co.bitcode.android.widget.util.RemoteImageTask;

/**
 * An ImageView which downloads a picture from the network and displays it when done.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class RemoteImageView extends RoundedCornerImageView {
    private OnImageDownloaded onImageDownloaded;
    private RemoteImageTask asyncTask;

    /**
     * Interface definition for a callback to be invoked when the RemoteImageView has finished
     * getting a picture from the network.
     * 
     * @since 1.0.0
     * @author Lorenzo Villani
     */
    public static interface OnImageDownloaded {
        /**
         * Called when the {@link RemoteImageView} has finished downloading the picture.
         * 
         * @param bitmap
         *        The downloaded {@link Bitmap}.
         * @since 1.0.0
         */
        void onImageDownloaded(final Bitmap bitmap);
    }

    /**
     * Use this as a return value with {@link #getMissingDrawable()} and
     * {@link #getLoadingDrawable()} in case you don't want to change our current bitmap.
     */
    protected static final int NO_PICTURE = -1;

    /**
     * @param context
     *        The {@link Context}.
     * @see android.view.View#View(Context)
     */
    public RemoteImageView(final Context context) {
        super(context);
    }

    /**
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @see android.view.View#View(Context, AttributeSet)
     */
    public RemoteImageView(final Context context, final AttributeSet attrs) {
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
    public RemoteImageView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Cancels any pending asynchronous image downloading task.
     * 
     * @since 1.0.0
     */
    public void cancel() {
        if ((this.asyncTask != null) && !this.asyncTask.isCancelled()) {
            this.asyncTask.cancel(true);
            this.asyncTask = null;

            setImageResource(getNotFoundDrawable());
        }
    }

    /**
     * Triggers an asynchronous image download.
     * 
     * @param uri
     *        Uri of image to download.
     * @since 1.0.0
     * @see #fetchRemoteImage(Uri)
     */
    @Override
    public void setImageURI(final Uri uri) {
        this.asyncTask = new RemoteImageTask(this) {
            @Override
            protected void onFinish(final Bitmap result) {
                super.onFinish(result);

                if (RemoteImageView.this.onImageDownloaded != null) {
                    RemoteImageView.this.onImageDownloaded.onImageDownloaded(result);
                }

                RemoteImageView.this.asyncTask = null;
            }
        };
        this.asyncTask.execute(uri);
    }

    public void setOnImageDownloaded(final OnImageDownloaded onImageDownloaded) {
        this.onImageDownloaded = onImageDownloaded;
    }

    /**
     * @return Drawable ID to show when an error occurs while fetching the image from the server.
     */
    public abstract int getNotFoundDrawable();

    /**
     * Callback method invoked whenever this {@link RemoteImageView} needs to download data from the
     * network.
     * 
     * @param uri
     *        Uri of the image to download.
     * @return A {@link Bitmap} containing the downloaded resource.
     * @since 1.0.0
     */
    public abstract Bitmap fetchRemoteImage(Uri uri);
}
