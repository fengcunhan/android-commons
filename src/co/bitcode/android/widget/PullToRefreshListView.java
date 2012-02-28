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

package co.bitcode.android.widget;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.bitcode.android.R;
import co.bitcode.android.view.animation.CollapseAnimation;

/**
 * Pull-to-refresh {@link ListView}.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class PullToRefreshListView extends ListView implements OnClickListener {
    private Animation rotateDownwards;
    private Animation rotateUpwards;
    private CollapseAnimation collapseAnimation;
    private ImageView arrow;
    private ProgressBar progressBar;
    private TextView message;
    private View header;

    private boolean isActivated;
    private boolean isRefreshing;
    private int measuredHeight;
    private int lastDragStartY;
    private String emptyMessage;

    private final Set<OnRefreshListener> refreshListeners = Collections
            .synchronizedSet(new HashSet<OnRefreshListener>(1));
    private final Set<OnRefreshDoneListener> refreshDoneListeners = Collections
            .synchronizedSet(new HashSet<OnRefreshDoneListener>(1));

    /* CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES */
    public interface OnRefreshListener {
        void onRefresh();
    }

    /* CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES */
    public interface OnRefreshDoneListener {
        void onRefreshDone();
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @see android.view.View#View(Context)
     */
    public PullToRefreshListView(final Context context) {
        super(context);

        init();
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @see android.view.View#View(Context, AttributeSet)
     */
    public PullToRefreshListView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    /**
     * Constructor.
     * 
     * @param context
     *        The {@link Context}.
     * @param attrs
     *        The {@link AttributeSet}.
     * @param defStyle
     *        The default style.
     * @see android.view.View#View(Context, AttributeSet, int)
     */
    public PullToRefreshListView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    /**
     * Notifies this object that we are initiating a refresh.
     * 
     * @since 1.0.0
     */
    public void notifyRefreshStarting() {
        this.isRefreshing = true;

        showProgressBar();

        if (this.isActivated) {
            animatedResetHeaderHeight();
        } else {
            resetHeaderHeight();
        }
    }

    /**
     * Notifies us that refresh is completed and that we should attempt to hide the header view.
     * 
     * @since 1.0.0
     */
    public void notifyRefreshDone() {
        this.isRefreshing = false;

        if (isEmpty()) {
            setEmptyHeader();
        } else {
            collapseHeader();
            hideProgressBar();
            deactivateRefresh();
        }

        for (final OnRefreshDoneListener listener : this.refreshDoneListeners) {
            listener.onRefreshDone();
        }
    }

    /**
     * Manually invoke a refresh.
     * 
     * @since 1.0.0
     */
    public void refresh() {
        notifyRefreshStarting();

        for (final OnRefreshListener listener : this.refreshListeners) {
            listener.onRefresh();
        }
    }

    /**
     * Adds a listener to the list of objects to be notified in case {@link #refresh()} is invoked.
     * 
     * @param listener
     *        A listener.
     * @since 1.0.0
     */
    public void addOnRefreshListener(final OnRefreshListener listener) {
        this.refreshListeners.add(listener);
    }

    /**
     * Adds a listener to the list of objects to be notified in case {@link #notifyRefreshDone()} is
     * invoked.
     * 
     * @param listener
     *        A listener.
     * @since 1.0.0
     */
    public void addOnRefreshDoneListener(final OnRefreshDoneListener listener) {
        this.refreshDoneListeners.add(listener);
    }

    /**
     * @deprecated This method is superseeded by {@link #addOnRefreshListener(OnRefreshListener)}.
     * @param onRefreshListener
     *        A listener.
     * @see #addOnRefreshListener(OnRefreshListener)
     * @since 1.0.0
     */
    @Deprecated
    public void setOnRefreshListener(final OnRefreshListener onRefreshListener) {
        addOnRefreshListener(onRefreshListener);
    }

    @Override
    public void onClick(final View v) {
        // Click events enabled only when "Tap to refresh" is shown.
        if (isEmpty()) {
            refresh();
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if ((this.measuredHeight == 0) && (this.header.getMeasuredHeight() != 0)) {
            this.measuredHeight = this.header.getMeasuredHeight();
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        if (!isEmpty() && !this.isRefreshing && (getFirstVisiblePosition() == 0)) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                this.lastDragStartY = (int) ev.getY();
            } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
                dragHeader(this.lastDragStartY, (int) ev.getY());
            } else if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (this.isActivated) {
                    refresh();
                } else {
                    collapseHeader();
                }
            }
        }

        if (!this.isActivated) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        super.setAdapter(adapter);

        if (!isEmpty()) {
            setSelection(1);
        }
    }

    public String getEmptyMessage() {
        return this.emptyMessage;
    }

    public void setEmptyMessage(final String emptyMessage) {
        this.emptyMessage = emptyMessage;
    }

    public void setEmptyMessage(final int resId) {
        this.emptyMessage = getResources().getString(resId);
    }

    private void init() {
        this.header = getLayoutInflater().inflate(R.layout.row_header_pulltorefresh, null);
        this.header.setOnClickListener(this);

        this.arrow = ViewUtils.find(this.header, R.id.row_header_pulltorefresh_arrow);
        this.message = ViewUtils.find(this.header, R.id.row_header_pulltorefresh_message);
        this.progressBar = ViewUtils.find(this.header, R.id.row_header_pulltorefresh_progressbar);

        this.collapseAnimation = new CollapseAnimation(this.header);
        this.rotateDownwards = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_downwards);
        this.rotateUpwards = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_upwards);

        addHeaderView(this.header, null, false);
        setEmptyHeader();
        setSmoothScrollbarEnabled(true);
    }

    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void setEmptyHeader() {
        this.arrow.setVisibility(View.INVISIBLE);
        this.progressBar.setVisibility(View.INVISIBLE);

        if (this.emptyMessage != null) {
            this.message.setText(this.emptyMessage);
        } else {
            this.message.setText(R.string.row_header_pulltorefresh_empty);
        }

        resetHeaderHeight();
    }

    private boolean isEmpty() {
        if (getAdapter() == null) {
            return true;
        } else {
            return getAdapter().isEmpty();
        }
    }

    private void collapseHeader() {
        this.collapseAnimation.collapseTo(this.header.getMeasuredHeight(), 1);
        this.header.startAnimation(this.collapseAnimation);
    }

    private void dragHeader(final int startY, final int newY) {
        final int motionDelta = newY - startY;

        if ((motionDelta > 0) && (this.measuredHeight != 0)) {
            setHeaderHeight(motionDelta);

            if (!this.isActivated && (motionDelta > this.measuredHeight)) {
                activateRefresh();
            } else if (this.isActivated && (motionDelta < this.measuredHeight)) {
                deactivateRefresh();
            }
        }
    }

    private void animatedResetHeaderHeight() {
        this.collapseAnimation.collapseTo(this.header.getMeasuredHeight(), this.measuredHeight);
        this.header.startAnimation(this.collapseAnimation);
    }

    private void resetHeaderHeight() {
        setHeaderHeight(LayoutParams.WRAP_CONTENT);
    }

    private void setHeaderHeight(final int newHeight) {
        if (this.header.getLayoutParams() != null) {
            // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
            this.header.getLayoutParams().height = newHeight;
            this.header.requestLayout();
        }
    }

    private void showProgressBar() {
        this.arrow.clearAnimation();
        this.arrow.setVisibility(View.INVISIBLE);
        this.message.setText(R.string.row_header_pulltorefresh_loading);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        this.arrow.clearAnimation();
        this.arrow.setVisibility(View.VISIBLE);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    private void activateRefresh() {
        this.isActivated = true;
        this.arrow.startAnimation(this.rotateUpwards);
        this.message.setText(R.string.row_header_pulltorefresh_release);
    }

    private void deactivateRefresh() {
        this.isActivated = false;
        this.arrow.startAnimation(this.rotateDownwards);
        this.message.setText(R.string.row_header_pulltorefresh_pullDown);
    }
}
