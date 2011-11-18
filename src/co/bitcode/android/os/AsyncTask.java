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

/**
 * Extends {@link android.os.AsyncTask} providing support for checked exception handling.
 * 
 * @param <Params>
 *        The type of the parameters sent to the task upon execution.
 * @param <Result>
 *        The type of the result of the background computation.
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class AsyncTask<Params, Result> extends android.os.AsyncTask<Params, Void, Result> {
    private Exception exception;

    @Override
    protected final Result doInBackground(final Params... params) {
        try {
            return doInBackgroundThread(params);
            /* CHECKSTYLE IGNORE ALL CHECKS NEXT LINE */
        } catch (final Exception e) {
            this.exception = e;

            return null;
        }
    }

    @Override
    protected final void onPostExecute(final Result result) {
        if (this.exception != null) {
            onCatchException(this.exception);
        } else {
            onFinish(result);
        }

        onFinalize();
    }

    /**
     * Override this method to perform a computation which might throw a checked exception.
     * 
     * @param params
     *        The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @throws Exception
     *         Checked exception caught.
     * @since 1.0.0
     */
    protected abstract Result doInBackgroundThread(Params... params) throws Exception;

    /**
     * Called when the computation raises an exception.
     * 
     * @param e
     *        Exception raised inside {@link #doInBackgroundThread(Object...)}.
     * @since 1.0.0
     */
    protected void onCatchException(final Exception e) {
    }

    /**
     * Runs on the UI thread after {@link #doInBackgroundThread(Object...)}. The specified result is
     * the value returned by {@link #doInBackgroundThread(Object...)}.
     * 
     * This method will be called even if {@link #doInBackgroundThread(Object...)} raised an
     * exception or the task was cancelled.
     * 
     * @since 1.0.0
     */
    protected void onFinalize() {
    }

    /**
     * Runs on the UI thread after {@link #doInBackgroundThread(Object...)}. The specified result is
     * the value returned by {@link #doInBackgroundThread(Object...)}.
     * 
     * This method won't be invoked if the task was cancelled.
     * 
     * @param result
     *        The result of the operation computed by {@link #doInBackgroundThread(Object...)}.
     * @since 1.0.0
     */
    protected void onFinish(final Result result) {
    }
}
