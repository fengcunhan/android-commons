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

package co.bitcode.android.content;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Provides convenience methods to perform common operations involving intents.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class IntentUtils {
    private IntentUtils() {
    }

    /**
     * Wraps {@link PendingIntent#getBroadcast(Context, int, Intent, int)}.
     * 
     * @param context
     *        The {@link Context}.
     * @param intent
     *        The {@link Intent}.
     * @return A {@link PendingIntent} to broadcast.
     * @since 1.0.0
     */
    public static PendingIntent toBroadcast(final Context context, final Intent intent) {
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }
}