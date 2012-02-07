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

package co.bitcode.android.app.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.Uri;

import co.bitcode.android.R;
import co.bitcode.android.os.ProgressDialogTask;

/**
 * Asynchronously downloads a file from the network.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class DownloadImageTask extends ProgressDialogTask<Uri, File> {
    private final File destFile;

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param destFile
     *        Destination file.
     * @since 1.0.0
     */
    public DownloadImageTask(final Context context, final File destFile) {
        super(context, R.string.generic_loading);

        this.destFile = destFile;
    }

    @Override
    protected File doInBackgroundThread(final Uri... params) throws Exception {
        final Uri remoteImage = params[0];
        final File destination = new File(getContext().getFilesDir(), this.destFile.getName());
        final URLConnection urlConnection = new URL(remoteImage.toString()).openConnection();
        final BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(
                destination), 4096);
        final InputStream input = urlConnection.getInputStream();
        int read;

        while ((read = input.read()) != -1) {
            output.write(read);
        }

        output.close();
        input.close();

        return destination;
    }
}
