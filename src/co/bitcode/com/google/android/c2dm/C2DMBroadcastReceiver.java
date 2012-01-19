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

package co.bitcode.com.google.android.c2dm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Provides the plumbing to easily handle Google's Cloud to Device Messaging Framework registration
 * events and messages.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class C2DMBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (C2DMContract.ACTION_REGISTRATION.equals(intent.getAction())) {
            handleRegistration(context, intent);
        } else if (C2DMContract.ACTION_RECEIVE.equals(intent.getAction())) {
            onMessageReceived(context, intent);
        }
    }

    /**
     * Callback method invoked whenever we receive a Cloud to Device message, delivered to us
     * (typically after registration has occurred) by Google's Cloud to Device Messaging Framework
     * Intent-based API.
     * 
     * @param context
     *        The {@link Context}.
     * @param intent
     *        The received message, wrapped inside an Intent.
     * @since 1.0.0
     */
    public abstract void onMessageReceived(final Context context, final Intent intent);

    /**
     * Callback method invoked whenever we get a registration id from Google's Cloud to Device
     * Messaging Framework Intent-based API.
     * 
     * @param registrationId
     *        The registration ID we received.
     * @since 1.0.0
     */
    public abstract void onRegister(final String registrationId);

    private void handleRegistration(final Context context, final Intent intent) {
        if (!intent.hasExtra(C2DMContract.EXTRA_ERROR)) {
            if (intent.hasExtra(C2DMContract.EXTRA_UNREGISTERED)) {
                // Clear registration status
                final C2DMStatus status = new C2DMStatus(context);

                status.setRegistered(false);
                status.save();
            } else if (intent.hasExtra(C2DMContract.EXTRA_REGISTRATION_ID)) {
                // Store registration status
                final C2DMStatus status = new C2DMStatus(context);

                onRegister(intent.getStringExtra(C2DMContract.EXTRA_REGISTRATION_ID));

                status.setRegistered(true);
                status.save();
            }
        }
    }
}
