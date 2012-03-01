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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StreamRepresentation;

/**
 * A representation backed by a non-transient byte array.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class ByteArrayRepresentation extends StreamRepresentation {
    private final byte[] data;

    /**
     * Constructor.
     * 
     * @param representation
     *        Representation to wrap.
     * @since 1.0.0
     */
    public ByteArrayRepresentation(final Representation representation) {
        super(representation.getMediaType());

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            IOUtils.copy(representation.getStream(), outputStream);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        this.data = outputStream.toByteArray();
    }

    /**
     * Constructor.
     * 
     * @param data
     *        Input byte array.
     * @param mediaType
     *        The media type.
     * @since 1.0.0
     */
    public ByteArrayRepresentation(final byte[] data, final MediaType mediaType) {
        super(mediaType);

        this.data = data;
    }

    @Override
    public long getSize() {
        return this.data.length;
    }

    @Override
    public InputStream getStream() throws IOException {
        return new ByteArrayInputStream(this.data);
    }

    @Override
    public boolean isAvailable() {
        return (this.data != null) && (this.data.length > 0);
    }

    @Override
    public void write(final OutputStream outputStream) throws IOException {
        outputStream.write(this.data);
    }
}
