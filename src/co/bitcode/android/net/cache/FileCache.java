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

import org.apache.commons.io.FileUtils;

/**
 * Builds a two-level (in-memory and on-storage), write through cache upon {@link LruCache}.
 * 
 * It provides hooks so that subclasses can manage the mechanisms trough which data is stored on
 * disk.
 * 
 * @param <K>
 *        Key type.
 * @param <V>
 *        Value type.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class FileCache<K, V> extends LruCache<K, V> {
    private final File cacheDir;

    /**
     * Constructor.
     * 
     * @param context
     *        Application context.
     * @param maxSize
     *        for caches that do not override {@link #sizeOf(Object, Object)}, this is the maximum
     *        number of entries in the cache. For all other caches, this is the maximum sum of the
     *        sizes of the entries in this cache.
     * @param cacheZone
     *        Cache zone name, without spaces and/or path separators.
     * @since 1.0.0
     */
    public FileCache(final Context context, final int maxSize, final String cacheZone) {
        super(maxSize);

        this.cacheDir = FileUtils.getFile(context.getCacheDir(), cacheZone);
        this.cacheDir.mkdirs();
    }

    /**
     * @param key
     *        Value to check.
     * @return <code>true</code> if <code>key</code> is in the cache, <code>false</code> otherwise.
     * @since 1.0.0
     */
    public boolean has(final K key) {
        return getCacheFile(key).exists();
    }

    /**
     * Caches <code>value</code> for <code>key</code>. The value is moved to the head of the queue.
     * 
     * @param key
     *        The key.
     * @param value
     *        The value.
     * @param writeThrough
     *        Whether to write the cached value on disk.
     * @return
     * @since 1.0.0
     */
    @Override
    public synchronized V put(final K key, final V value) {
        final V ret = super.put(key, value);

        store(getCacheFile(key), value);

        return ret;
    }

    /**
     * Callback method invoked upon a cache miss in the first level cache.
     * 
     * @param cacheFile
     *        The cache file to load. It is guaranteed to be there when the callback is invoked.
     * @return Data loaded from disk.
     * @since 1.0.0
     */
    protected abstract V load(final File cacheFile);

    /**
     * Callback method invoked when a new value is put into the first level cache.
     * 
     * @param cacheFile
     *        Destination file.
     * @param value
     *        The value to be written on disk.
     * @since 1.0.0
     */
    protected abstract void store(final File cacheFile, final V value);

    @Override
    protected synchronized V create(final K key) {
        final File cacheFile = getCacheFile(key);

        if (cacheFile.exists()) {
            return load(cacheFile);
        } else {
            return null;
        }
    }

    @Override
    protected synchronized void entryRemoved(final boolean evicted, final K key, final V oldValue,
            final V newValue) {
        getCacheFile(key).delete();
    }

    protected File getCacheFile(final K key) {
        return FileUtils.getFile(this.cacheDir, Integer.toHexString(key.toString().hashCode()));
    }
}
