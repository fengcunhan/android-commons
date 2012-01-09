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
    private final View view;
    private int startHeight;
    private int endHeight;

    /**
     * Constructor.
     * 
     * @param view
     *        The {@link View} we want to animate.
     * @since 1.0.0
     */
    public CollapseAnimation(final View view) {
        super();

        this.view = view;

        setDuration(DEFAULT_DURATION);
    }

    /**
     * Sets collapse parameters for this animation.
     * 
     * @param startHeight
     *        Starting height.
     * @param endHeight
     *        The desired height at the end of the animation.
     * @since 1.0.0
     */
    public void collapseTo(final int startHeight, final int endHeight) {
        if (endHeight < 1) {
            this.endHeight = 1;
        } else {
            this.endHeight = endHeight;
        }

        if (startHeight < this.endHeight) {
            this.startHeight = this.endHeight + 1;
        } else {
            this.startHeight = startHeight;
        }
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    protected void applyTransformation(final float interpolatedTime, final Transformation t) {
        final int h = (int) (1 + (this.startHeight * (1 - interpolatedTime)));

        if (h > this.endHeight) {
            // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
            this.view.getLayoutParams().height = h;
        } else {
            // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
            this.view.getLayoutParams().height = this.endHeight;
        }

        this.view.requestLayout();
    }
}
