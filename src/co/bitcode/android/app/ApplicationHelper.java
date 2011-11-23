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

package co.bitcode.android.app;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * Provides utility methods to determine some properties of the running application.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class ApplicationHelper {
    private ApplicationHelper() {
    }

    /**
     * @param context
     *        The {@link Context}.
     * @return This application's main package name as described in the root element of
     *         <code>AndroidManifest.xml</code>
     * @since 1.0.0
     */
    public static String getVersionName(final Context context) {
        try {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            final PackageManager packageManager = context.getPackageManager();

            return packageManager.getPackageInfo(applicationInfo.packageName, 0).versionName;
        } catch (final NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Determines whether one of our activities is currently running on foreground (i.e.: is visible
     * to the user).
     * 
     * @param context
     *        The {@link Context}.
     * @return <code>true</code> if we are running in the foreground, <code>false</code> otherwise.
     * @since 1.0.0
     */
    public static boolean isForeground(final Context context) {
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        final PackageManager packageManager = context.getPackageManager();

        for (final RunningAppProcessInfo processInfo : processes) {
            if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                try {
                    final ApplicationInfo applicationInfo = packageManager.getApplicationInfo(
                            processInfo.processName, PackageManager.GET_META_DATA);

                    if (applicationInfo.packageName.equals(context.getPackageName())) {
                        return true;
                    }
                } catch (final NameNotFoundException ex) {
                    return false;
                }
            }
        }

        return false;
    }
}
