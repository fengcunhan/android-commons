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

package co.bitcode.android.view.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * An {@link Animation} which collapses the attached View.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class CollapseAnimation extends Animation {
    private static final long DEFAULT_DURATION = 500;
    private final View attachedView;
    private int attachedViewHeight;

    /**
     * Constructor.
     * 
     * @param attachedView
     *        The {@link View} we want to animate.
     * @since 1.0.0
     */
    public CollapseAnimation(final View attachedView) {
        super();

        this.attachedView = attachedView;
        this.attachedViewHeight = attachedView.getMeasuredHeight();

        setDuration(DEFAULT_DURATION);
    }

    /**
     * Refresh cached height for attached {@link View}.
     * 
     * @since 1.0.0
     */
    public void notifyHeightChanged() {
        this.attachedViewHeight = this.attachedView.getMeasuredHeight();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    protected void applyTransformation(final float interpolatedTime, final Transformation t) {
        final int newHeight = (int) (1 + (this.attachedViewHeight * (1 - interpolatedTime)));

        // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
        this.attachedView.getLayoutParams().height = newHeight;
        this.attachedView.requestLayout();
    }
}
