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

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import co.bitcode.android.app.ContextUtils;

/**
 * Provides the plumbing requried to implement a location-based background service.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class AbstractLocationService extends Service implements LocationListener {
    private LocationManager locationManager;

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        this.locationManager = ContextUtils.getLocationManager(getApplicationContext());
        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                getMinimumTime() / 2, getMinimumDistance(), this);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, getMinimumTime(),
                getMinimumDistance(), this);

        notifyBestKnownLocation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        detachLocationListener();
    }

    @Override
    public void onProviderDisabled(final String provider) {
    }

    @Override
    public void onProviderEnabled(final String provider) {
    }

    @Override
    public void onStatusChanged(final String provider, final int status, final Bundle extras) {
    }

    /**
     * Switches to a different location provider, making sure to remove all previously attached
     * event listeners.
     * 
     * @param provider
     *        The new location provider to use.
     * @since 1.0.0
     */
    protected void useProvider(final String provider) {
        detachLocationListener();

        this.locationManager.requestLocationUpdates(provider, getMinimumTime(),
                getMinimumDistance(), this);
    }

    protected LocationManager getLocationManager() {
        return this.locationManager;
    }

    /**
     * @return The minimum time (in milliseconds) between fixes.
     * @since 1.0.0
     */
    protected abstract long getMinimumTime();

    /**
     * @return The minimum distance (in meters) between fixes.
     * @since 1.0.0
     */
    protected abstract float getMinimumDistance();

    private void notifyBestKnownLocation() {
        final Location knownLocation = LocationHelper.getBestKnownLocation(this.locationManager);

        if (knownLocation != null) {
            onLocationChanged(knownLocation);
        }
    }

    private void detachLocationListener() {
        this.locationManager.removeUpdates(this);
    }
}
