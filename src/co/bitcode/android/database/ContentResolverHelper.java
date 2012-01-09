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

package co.bitcode.android.database;

import java.io.File;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images;

/**
 * Operations on {@link ContentResolver} {@link Uri}s.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class ContentResolverHelper {
    private ContentResolverHelper() {
    }

    /**
     * Resolve real path from {@link ContentResolver} Uri.
     * 
     * @param context
     *        The {@link Context}.
     * @param data
     *        The content resolver {@link Uri}.
     * @return An instance of {@link File} or <code>null</code> if we couldn't determine the real
     *         path.
     * @since 1.0.0
     */
    public static File resolveImagePath(final Context context, final Uri data) {
        final ContentResolver resolver = context.getContentResolver();
        /* CHECKSTYLE IGNORE ALL CHECKS NEXT LINE */
        final String[] projection = new String[] { Images.Media.DATA };
        final Cursor cursor = resolver.query(data, projection, null, null, null);
        final File resolvedFile;

        if (cursor != null) {
            cursor.moveToFirst();

            resolvedFile = new File(cursor.getString(cursor.getColumnIndex(Images.Media.DATA)));

            cursor.close();

            return resolvedFile;
        } else {
            return null;
        }
    }
}
