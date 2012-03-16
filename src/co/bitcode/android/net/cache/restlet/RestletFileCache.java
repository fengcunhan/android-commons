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

package co.bitcode.android.net.cache.restlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;

import co.bitcode.android.net.cache.ExpirationFileCache;
import co.bitcode.android.net.cache.FileCache;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.restlet.data.MediaType;
import org.restlet.data.Reference;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.Representation;

/**
 * A {@link FileCache} customized for use with the RESTLet library.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class RestletFileCache extends ExpirationFileCache<Reference, Representation> {
    /**
     * Constructor.
     * 
     * @param context
     *        Application context.
     * @param maxSize
     *        for caches that do not override {@link #sizeOf(Object, Object)}, this is the maximum
     *        number of entries in the cache. For all other caches, this is the maximum sum of the
     *        sizes of the entries in this cache.
     * @param expiryTime
     *        Cache expiration time in milliseconds.
     * @param cacheZone
     *        Cache zone name, without spaces and/or path separators.
     * @since 1.0.0
     */
    public RestletFileCache(final Context context, final int maxSize, final long expiryTime,
            final String cacheZone) {
        super(context, maxSize, expiryTime, cacheZone);
    }

    @Override
    protected Representation load(final File cacheFile) {
        try {
            return new ByteArrayRepresentation(FileUtils.readFileToByteArray(cacheFile),
                    MediaType.APPLICATION_OCTET_STREAM);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void store(final File cacheFile, final Representation value) {
        final OutputStream outputStream = getOutputStream(cacheFile);

        try {
            IOUtils.copy(value.getStream(), outputStream);
        } catch (final IOException e) {
            // Silently fail.
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    private OutputStream getOutputStream(final File file) {
        try {
            return new FileOutputStream(file);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
