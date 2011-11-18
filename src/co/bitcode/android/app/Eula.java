/*
 * Copyright (C) 2008 The Android Open Source Project
 * Copyright (C) 2011 Lorenzo Villani.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package co.bitcode.android.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import co.bitcode.android.R;

/**
 * Displays an EULA ("End User License Agreement") that the user has to accept before using the
 * application.
 * <p>
 * The EULA is retrieved from your project's <code>assets</code> directory.
 * </p>
 * <p>
 * Your application should call {@link Eula#show(android.app.Activity)} in the onCreate() method of
 * the first activity.
 * </p>
 * <p>
 * If the user accepts the EULA, it will never be shown again. If the user refuses,
 * {@link android.app.Activity#finish()} is invoked on your activity.
 * </p>
 * 
 * @since 1.0.0
 * @author The Android Open Source Project
 * @author Lorenzo Villani
 */
public final class Eula {
    private static final String ASSET_EULA = "EULA";
    private static final String PREFERENCE = "eula";
    private static final String PREFERENCE_EULA_ACCEPTED = "accepted";

    private Eula() {
    }

    /**
     * Interface used to allow the creator of an {@link ObservableTask} to run some code when users
     * accept the license agreement.
     * 
     * @author The Android Open Source Project
     * @since 1.0.0
     */
    public interface OnEulaAgreedTo {
        /**
         * This method will be invoked when the user accepts the license agreement.
         */
        void onEulaAgreedTo();
    }

    /**
     * Displays the EULA if necessary. This method should be called from the onCreate() method of
     * your main Activity.
     * 
     * @param activity
     *        The Activity to finish if the user rejects the EULA.
     * @return Whether the user has agreed already.
     * @since 1.0.0
     */
    public static boolean show(final Activity activity) {
        final SharedPreferences preferences;

        preferences = activity.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);

        if (!preferences.getBoolean(PREFERENCE_EULA_ACCEPTED, false)) {
            final AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(activity);

            builder.setTitle(R.string.eula_title);
            builder.setCancelable(true);
            builder.setPositiveButton(R.string.eula_accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    accept(preferences);
                    if (activity instanceof OnEulaAgreedTo) {
                        ((OnEulaAgreedTo) activity).onEulaAgreedTo();
                    }
                }
            });
            builder.setNegativeButton(R.string.eula_refuse, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, final int which) {
                    refuse(activity);
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(final DialogInterface dialog) {
                    refuse(activity);
                }
            });
            builder.setMessage(readEula(activity));
            builder.create().show();

            return false;
        }

        return true;
    }

    /**
     * Accepts the EULA. That is, persists the truth value in our preference file.
     * 
     * @param preferences
     */
    private static void accept(final SharedPreferences preferences) {
        preferences.edit().putBoolean(PREFERENCE_EULA_ACCEPTED, true).commit();
    }

    /**
     * Terminates parent activity.
     * 
     * @param activity
     *        Parent {@link Activity}.
     */
    private static void refuse(final Activity activity) {
        activity.finish();
    }

    /**
     * Reads EULA from assets folder.
     * 
     * @param activity
     *        Parent {@link Activity}
     * @return EULA contents.
     */
    private static CharSequence readEula(final Activity activity) {
        BufferedReader in;

        in = null;

        try {
            String line;
            StringBuilder buffer;

            in = new BufferedReader(new InputStreamReader(activity.getAssets().open(ASSET_EULA)));
            buffer = new StringBuilder();

            while ((line = in.readLine()) != null) {
                buffer.append(line).append('\n');
            }

            return buffer;
        } catch (final IOException e) {
            return "";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (final IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
