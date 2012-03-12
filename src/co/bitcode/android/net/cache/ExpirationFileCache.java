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

package co.bitcode.android.net.cache;

import java.io.File;

import android.content.Context;

/**
 * Write-through cache with time-based expiration.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class ExpirationFileCache<K, V> extends FileCache<K, V> {
    private final long expiryTime;

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
    public ExpirationFileCache(final Context context, final int maxSize, final long expiryTime,
            final String cacheZone) {
        super(context, maxSize, cacheZone);

        this.expiryTime = expiryTime;
    }

    public long getExpiryTime() {
        return this.expiryTime;
    }

    @Override
    public synchronized V get(final K key) {
        final V value = super.get(key);

        if ((value != null) && isFreshEnough(getCacheFile(key))) {
            return value;
        } else {
            remove(key);

            return null;
        }
    }

    @Override
    protected synchronized V create(final K key) {
        final File cacheFile = getCacheFile(key);

        if (cacheFile.exists() && isFreshEnough(cacheFile)) {
            return load(cacheFile);
        } else {
            cacheFile.delete();

            return null;
        }
    }

    private boolean isFreshEnough(final File cacheFile) {
        final long delta = Math.abs(System.currentTimeMillis() - cacheFile.lastModified());

        return delta < this.expiryTime;
    }
}
