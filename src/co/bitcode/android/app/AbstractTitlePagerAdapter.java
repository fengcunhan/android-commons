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

import android.support.v4.app.FragmentManager;

import com.viewpagerindicator.TitleProvider;

/**
 * Provides plumbing to simplify implementation of a <code>FragmentPagerAdapter</code> which is used
 * in tandem with a <code>TitlePageIndicator</code>.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 * @see android.support.v4.app.FragmentPagerAdapter
 * @see com.viewpagerindicator.TitlePageIndicator
 */
public abstract class AbstractTitlePagerAdapter extends AbstractPagerAdapter implements
        TitleProvider {
    private final List<String> titles;

    /**
     * Constructor.
     * 
     * @param fragmentManager
     *        The {@link FragmentManager}.
     */
    public AbstractTitlePagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);

        this.titles = getTitles();

        assert (this.titles != null);
        assert (this.titles.size() == getCount());
    }

    @Override
    public String getTitle(final int index) {
        return this.titles.get(index);
    }

    /**
     * Subclasses must override this method to set-up titles to be shown in a
     * <code>TitlePageIndicator</code>.
     * 
     * @since 1.0.0
     * @return A configured {@link List} of {@link String}. Empty or null lists are not allowed and
     *         will raise a {@link RuntimeException}. In addition, this list must have the same
     *         number of elements as reported by {@link #getCount()}.
     */
    public abstract List<String> getTitles();
}
