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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Provides helper methods to interact with Google's Cloud to Device Messaging Framework
 * Intent-based API.
 * 
 * This class is meant to be used with {@link C2DMBroadcastReceiver} provided that these advices are
 * followed: <a href="http://code.google.com/android/c2dm/index.html">Android Cloud to Device
 * Messaging Framework Developer's Guide</a>
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 * @see C2DMBroadcastReceiver
 * @see "http://code.google.com/android/c2dm/index.html"
 */
public final class C2DMHelper {
    private C2DMHelper() {
    }

    /**
     * Registers to Google's Cloud to Device Messaging Framework.
     * 
     * @param context
     *        The {@link Context}.
     * @param accountId
     *        The ID of the account authorized to send messages to the application, typically the
     *        email address of an account set up by the application's developer.
     * @since 1.0.0
     */
    public static void register(final Context context, final String accountId) {
        final Intent intent = new Intent(C2DMContract.ACTION_REGISTER);

        intent.putExtra(C2DMContract.EXTRA_APP, getAppExtra(context));
        intent.putExtra(C2DMContract.EXTRA_SENDER, accountId);

        context.startService(intent);
    }

    /**
     * Unregisters from Google's Cloud to Device Messaging Framework.
     * 
     * @param context
     *        The {@link Context}.
     * @since 1.0.0
     */
    public static void unregister(final Context context) {
        final Intent intent = new Intent(C2DMContract.ACTION_UNREGISTER);

        intent.putExtra(C2DMContract.EXTRA_APP, getAppExtra(context));

        context.startService(intent);
    }

    /**
     * @param context
     *        The {@link Context}.
     * @return <code>true</code> if this application is already registered with Google's Cloud to
     *         Device Messaging Framework (it has received and dealt with a registration ID at least
     *         once), <code>false</code> otherwise.
     * @since 1.0.0
     */
    public static boolean isRegistered(final Context context) {
        return new C2DMStatus(context).isRegistered();
    }

    private static PendingIntent getAppExtra(final Context context) {
        return PendingIntent.getBroadcast(context, 0, new Intent(), 0);
    }
}
