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

package co.bitcode.android.app;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Provides plumbing to simplify implementation of a {@link FragmentPagerAdapter}.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 * @see FragmentPagerAdapter
 */
public abstract class AbstractPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragments;

    /**
     * Constructor.
     * 
     * @param fragmentManager
     *        The {@link FragmentManager}.
     * @since 1.0.0
     */
    public AbstractPagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);

        this.fragments = getFragments();

        assert (this.fragments != null);
        assert (!this.fragments.isEmpty());
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Fragment getItem(final int item) {
        return this.fragments.get(item);
    }

    /**
     * Subclasses must override this method to set-up {@link Fragment} to be shown inside a
     * <code>ViewPager</code>.
     * 
     * @since 1.0.0
     * @return A configured {@link List} of {@link Fragment}s. Empty or null lists are not allowed
     *         and will raise a {@link RuntimeException}.
     */
    protected abstract List<Fragment> getFragments();
}
