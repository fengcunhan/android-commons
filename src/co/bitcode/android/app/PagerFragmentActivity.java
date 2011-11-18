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

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import co.bitcode.android.R;

/**
 * Exposes an Intent extras-based API to allow callers to select the default page shown in a child
 * {@link ViewPager}.
 * 
 * The child {@link ViewPager} must have the {@link co.bitcode.android.R.id.viewpager} ID.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public abstract class PagerFragmentActivity extends FragmentActivity {
    /** Name of the Intent extra to use when requesting a different page. */
    public static final String EXTRA_FRAGMENT_PAGE = "fragmentPage";

    private ViewPager viewPager;

    @Override
    protected void onStart() {
        super.onStart();

        ensurePager();
        switchToFragment(getIntent());
    }

    /**
     * Subclasses must implement this method to have this class choose a page to show whenever
     * callers don't give as an argument via Intent extras.
     * 
     * @return Index of the fragment to show in case we got no values from Intent extras.
     * @since 1.0.0
     * @see #EXTRA_FRAGMENT_PAGE
     */
    protected abstract int getDefaultPage();

    private void ensurePager() {
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);

        if (this.viewPager == null) {
            throw new IllegalStateException("Unable to find a ViewPager inside current layout");
        }
    }

    private void switchToFragment(final Intent intent) {
        final int fragmentIndex = intent.getIntExtra(EXTRA_FRAGMENT_PAGE, getDefaultPage());

        switchToFragment(fragmentIndex);
    }

    private void switchToFragment(final int fragmentIndex) {
        this.viewPager.setCurrentItem(fragmentIndex);
    }
}
