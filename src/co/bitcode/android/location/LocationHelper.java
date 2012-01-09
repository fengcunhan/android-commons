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

package co.bitcode.android.location;

import android.location.Location;
import android.location.LocationManager;

/**
 * Useful utility methods for location-based applications.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class LocationHelper {
    private static final int THRESHOLD_ACCURACY = 200;
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    private LocationHelper() {
    }

    /**
     * Retrieves best known location from all location providers available on this system.
     * 
     * @param locationManager
     *        A {@link LocationManager}
     * @return Best known location or <code>null</code> if it could not be found.
     * @since 1.0.0
     */
    public static Location getBestKnownLocation(final LocationManager locationManager) {
        Location bestLocation = null;

        for (final String provider : locationManager.getProviders(false)) {
            final Location location = locationManager.getLastKnownLocation(provider);

            if ((location != null) && isBetterLocation(location, bestLocation)) {
                bestLocation = location;
            }
        }

        return bestLocation;
    }

    /**
     * Determines the best {@link Location}.
     * 
     * @param location
     *        The new Location that you want to evaluate
     * @param currentBestLocation
     *        The current Location fix, to which you want to compare the new one
     * @return <code>true</code> if <code>location</code> is a better location, <code>false</code>
     *         otherwise.
     * @since 1.0.0
     */
    //
    // This method was adapted from Android Developer site. The licensing terms of this stuff are
    // unknown to me. I'm assuming it is "public domain" but if you have a different opinion, please
    // contact me.
    //
    public static boolean isBetterLocation(final Location location,
            final Location currentBestLocation) {

        if (currentBestLocation == null) {
            return true;
        }

        final boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());
        final float accuracyDelta = (location.getAccuracy() - currentBestLocation.getAccuracy());
        final boolean isLessAccurate = accuracyDelta > 0;
        final boolean isMoreAccurate = accuracyDelta < 0;
        final boolean isSignificantlyLessAccurate = accuracyDelta > THRESHOLD_ACCURACY;

        final long timeDelta = location.getTime() - currentBestLocation.getTime();
        final boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        final boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        final boolean isNewer = timeDelta > 0;

        if (isSignificantlyNewer) {
            return true;
        } else if (isSignificantlyOlder) {
            return false;
        }

        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }

        return false;
    }

    private static boolean isSameProvider(final String provider1, final String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }

        return provider1.equals(provider2);
    }
}
