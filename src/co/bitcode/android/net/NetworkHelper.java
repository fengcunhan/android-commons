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

package co.bitcode.android.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Provides methods to test for network availability.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class NetworkHelper {
    private NetworkHelper() {
    }

    /**
     * Checks whether the active network interface has an established connection to a network.
     * 
     * @param context
     *        The {@link Context}.
     * @return <code>true</code> if the active network interface is connected, false otherwise.
     * @since 1.0.0
     */
    public static boolean isConnected(final Context context) {
        final ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        return networkInfo.isConnected();
    }
}
