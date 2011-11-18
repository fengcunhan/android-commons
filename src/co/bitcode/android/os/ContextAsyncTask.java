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

/**
 * An AsyncTask which holds a reference to a parent {@link Context}.
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
    }

    public Context getContext() {
        return this.context;
    }
}
