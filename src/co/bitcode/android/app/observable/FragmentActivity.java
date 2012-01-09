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

package co.bitcode.android.app.observable;

/**
 * A {@link android.support.v4.app.FragmentActivity} which implements the Observable pattern for
 * certain life-cycle events.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class FragmentActivity extends android.support.v4.app.FragmentActivity implements
        ObservableActivity {
    private OnDestroyListener onDestroyListener;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventHelper.triggerOnDestroyEvent(this.onDestroyListener);
    }

    @Override
    public OnDestroyListener getOnDestroyListener() {
        return this.onDestroyListener;
    }

    @Override
    public void setOnDestroyListener(final OnDestroyListener onDestroyListener) {
        this.onDestroyListener = onDestroyListener;
    }
}
