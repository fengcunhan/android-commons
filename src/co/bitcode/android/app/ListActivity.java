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

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import co.bitcode.android.R;

/**
 * Adds separation between progress and content containers to a {@link ListActivity}.
 * 
 * Offers similar functionality to <code>ListFragment</code>.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 * @see android.support.v4.app.ListFragment
 */
public class ListActivity extends android.app.ListActivity {
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private View progressContainer;
    private View listContainer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        this.fadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
    }

    /**
     * Control whether the list is being displayed. You can make it not displayed if you are waiting
     * for the initial data to show in it. During this time an indeterminante progress indicator
     * will be shown instead.
     * 
     * @param shown
     *        If true, the list view is shown; if false, the progress indicator. The initial value
     *        is true.
     * @since 1.0.0
     */
    public void setListShown(final boolean shown) {
        ensureContainers();

        if ((this.listContainer == null) || (this.progressContainer == null)) {
            throw new IllegalStateException("Unable to find list and progress containers");
        }

        if (shown) {
            this.progressContainer.startAnimation(this.fadeOutAnimation);
            this.listContainer.startAnimation(this.fadeInAnimation);
            this.progressContainer.setVisibility(View.GONE);
            this.listContainer.setVisibility(View.VISIBLE);
        } else {
            this.progressContainer.startAnimation(this.fadeInAnimation);
            this.listContainer.startAnimation(this.fadeOutAnimation);
            this.progressContainer.setVisibility(View.VISIBLE);
            this.listContainer.setVisibility(View.GONE);
        }
    }

    private void ensureContainers() {
        if (this.listContainer == null) {
            this.listContainer = findViewById(R.id.include_list_content_container);
        }

        if (this.progressContainer == null) {
            this.progressContainer = findViewById(R.id.include_list_content_progressContainer);
        }
    }
}
