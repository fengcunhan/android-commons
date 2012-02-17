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

package co.bitcode.android.widget.util;

import android.content.Context;

import co.bitcode.android.content.SharedPreferencesWrapper;

/**
 * Persistently stores the state of every FirstTimeView.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class FirstTimeState extends SharedPreferencesWrapper {
    private static final String KEY_SUPPRESS_ALL = "suppressAll";

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @since 1.0.0
     */
    public FirstTimeState(final Context context) {
        super(context);
    }

    /**
     * Suppresses all FirstTimeView from being shown.
     * 
     * @param isSuppressingAll
     *        <code>true</code> to hide all FirstTimeView, <code>false</code> otherwise.
     * @since 1.0.0
     */
    public void setSuppressingAll(final boolean isSuppressingAll) {
        put(KEY_SUPPRESS_ALL, isSuppressingAll);
    }

    /**
     * @return <code>true</code> if all FirstTimeView are inhibited from showing, <code>false</code>
     *         otherwise.
     * @since 1.0.0
     */
    public boolean isSuppressingAll() {
        return get(KEY_SUPPRESS_ALL, false);
    }

    /**
     * @param id
     *        The id of the FirstTimeView.
     * @return <code>true</code> if the FirstTimeView is set to be shown, <code>false</code>
     *         otherwise.
     * @since 1.0.0
     */
    public boolean getState(final String id) {
        return get(id, false);
    }

    /**
     * Overrides the state for a single FirstTimeView.
     * 
     * @param id
     *        The id of the FirstTimeView.
     * @param isActive
     *        Set to <code>true</code> to show it the next time it is created, <code>false</code>
     *        otherwise.
     * @since 1.0.0
     */
    public void setState(final String id, final boolean isActive) {
        put(id, isActive);
    }

    @Override
    protected String getPreferenceName() {
        return "first_time_view";
    }
}
