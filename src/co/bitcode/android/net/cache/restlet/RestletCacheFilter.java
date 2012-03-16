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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Filter;

/**
 * A filter which serves responses from the cache. It tries to cache both images and JSON responses.
 * 
 * <strong>Make sure to enable entity buffering by calling
 * {@link ClientResource#setEntityBuffering(boolean)}.</strong>
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class RestletCacheFilter extends Filter {
    private static final String ZONE_ENTITY = "entity";
    private static final String ZONE_IMAGE = "image";
    private static final int ENTITY_CACHE_SIZE = 100;
    private static final int IMAGE_CACHE_SIZE = 50;

    private final List<RestletFileCache> cachePool = new ArrayList<RestletFileCache>(2);
    private final RestletFileCache entityCache;
    private final RestletFileCache imageCache;

    /**
     * Constructor.
     * 
     * @param context
     *        The application context.
     * @since 1.0.0
     */
    public RestletCacheFilter(final Context context, final long expImages, final long expEntities) {
        this.imageCache = new RestletFileCache(context, IMAGE_CACHE_SIZE, expImages, ZONE_IMAGE);
        this.entityCache = new RestletFileCache(context, ENTITY_CACHE_SIZE, expEntities,
                ZONE_ENTITY);

        this.cachePool.add(this.imageCache);
        this.cachePool.add(this.entityCache);
    }

    public RestletFileCache getImageCache() {
        return this.imageCache;
    }

    public RestletFileCache getEntityCache() {
        return this.entityCache;
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        final Representation cached = getCachedEntity(request);

        if (cached != null) {
            response.setEntity(cached);

            return STOP;
        } else {
            return CONTINUE;
        }
    }

    @Override
    protected void afterHandle(final Request request, final Response response) {
        if (!isCached(request) && response.isEntityAvailable()) {
            final MediaType mediaType = response.getEntity().getMediaType();

            if ("image".equals(mediaType.getMainType())) {
                store(this.imageCache, request, response);
            }
        }
    }

    private boolean isCached(final Request request) {
        for (final RestletFileCache cache : this.cachePool) {
            if (cache.hasKey(request.getResourceRef())) {
                return true;
            }
        }

        return false;
    }

    private Representation getCachedEntity(final Request request) {
        for (final RestletFileCache cache : this.cachePool) {
            if (cache.hasKey(request.getResourceRef())) {
                return cache.get(request.getResourceRef());
            }
        }

        return null;
    }

    private void store(final RestletFileCache cache, final Request request, final Response response) {
        cache.put(request.getResourceRef(), response.getEntity());
    }
}
