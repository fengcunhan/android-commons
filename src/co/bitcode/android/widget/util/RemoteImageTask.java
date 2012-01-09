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

package co.bitcode.android.widget.util;

import android.graphics.Bitmap;
import android.net.Uri;

import co.bitcode.android.os.AsyncTask;
import co.bitcode.android.widget.RemoteImageView;

/**
 * Worker {@link AsyncTask} for {@link RemoteImageView}.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class RemoteImageTask extends AsyncTask<Uri, Bitmap> {
    private final RemoteImageView remoteImageView;

    /**
     * Constructor.
     * 
     * @param remoteImageView
     *        The managed {@link RemoteImageView}.
     */
    public RemoteImageTask(final RemoteImageView remoteImageView) {
        super();

        this.remoteImageView = remoteImageView;
    }

    @Override
    protected Bitmap doInBackgroundThread(final Uri... params) {
        final Uri uri = params[0];

        if (uri != null) {
            return this.remoteImageView.fetchRemoteImage(uri);
        } else {
            return null;
        }
    }

    @Override
    protected void onFinish(final Bitmap result) {
        super.onFinish(result);

        if (result == null) {
            this.remoteImageView.setImageResource(this.remoteImageView.getNotFoundDrawable());
        } else {
            this.remoteImageView.setImageBitmap(result);
        }
    }
}
