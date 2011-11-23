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

package co.bitcode.android.os;

import android.content.Context;
import android.util.Log;

import co.bitcode.android.app.observable.ObservableActivity;
import co.bitcode.android.app.observable.OnDestroyListener;

/**
 * An AsyncTask which holds a reference to a parent {@link Context}.
 * 
 * In case our parent {@link Context} is an {@link ObservableActivity}, we set-up an
 * {@link OnDestroyListener} which is used to cancel this task whenever the parent activity is
 * destroyed.
 * 
 * Note that we preserve the original {@link OnDestroyListener} and that we cancel the task after
 * the original handler has been called.
 * 
 * @param <Params>
 *        The type of the parameters sent to the task upon execution.
 * @param <Result>
 *        The type of the result of the background computation.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class ContextAsyncTask<Params, Result> extends AsyncTask<Params, Result> {
    private final Context context;

    /**
     * Constructor.
     * 
     * @param context
     *        The parent {@link Context}.
     */
    public ContextAsyncTask(final Context context) {
        this.context = context;

        if (context instanceof ObservableActivity) {
            setUpDestroyListener((ObservableActivity) context);
        }
    }

    public Context getContext() {
        return this.context;
    }

    private void setUpDestroyListener(final ObservableActivity context) {
        final OnDestroyListener originalListener = context.getOnDestroyListener();

        context.setOnDestroyListener(new OnDestroyListener() {
            @Override
            public void onDestroy() {
                if (originalListener != null) {
                    originalListener.onDestroy();
                }

                cancelTask();
            }
        });
    }

    private void cancelTask() {
        if (!isCancelled()) {
            Log.d(getClass().getName(), "Parent Context looks dead. Cancelling task");

            cancel(true);
        }
    }
}
