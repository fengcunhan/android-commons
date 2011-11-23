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

package co.bitcode.android.app.observable;

/**
 * Helper class for Observable activities. Provides shorthand methods to implement event dispatch
 * logic.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class EventHelper {
    private EventHelper() {
    }

    /**
     * Fires the {@link OnDestroyListener#onDestroy()} event. If the listener is <code>null</code>
     * this method is no-op.
     * 
     * @param listener
     *        The event listener.
     * @since 1.0.0
     */
    public static void triggerOnDestroyEvent(final OnDestroyListener listener) {
        if (listener != null) {
            listener.onDestroy();
        }
    }
}
