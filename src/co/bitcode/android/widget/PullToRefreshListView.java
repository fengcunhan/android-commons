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

package co.bitcode.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.bitcode.android.R;

/**
 * Pull-to-refresh {@link ListView}.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class PullToRefreshListView extends ListView implements OnClickListener, OnScrollListener {
    private Animation rotateDownwards;
    private Animation rotateUpwards;
    private View header;
    private ImageView arrow;
    private ProgressBar progressBar;
    private TextView message;

    private boolean isActivated;
    private boolean isRefreshing;
    private int activationThreshold;
    private int scrollState = OnScrollListener.SCROLL_STATE_IDLE;
    private OnRefreshListener onRefreshListener;

    /* CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES */
    public interface OnRefreshListener {
        void onRefresh();
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
    /* CHECKSTYLE IGNORE ALL CHECKS NEXT LINE */
    public PullToRefreshListView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    /**
     * Initiate loading.
     * 
     * @since 1.0.0
     */
    public void notifyRefreshStarting() {
        this.isRefreshing = true;

        showProgressBar();
        setSelection(0);
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
            resetHeader();
            setSelection(1);
        }
    }

    /**
     * Manually invoke a refresh.
     * 
     * @since 1.0.0
     */
    public void refresh() {
        notifyRefreshStarting();

        if (this.onRefreshListener != null) {
            this.onRefreshListener.onRefresh();
        }
    }

    public OnRefreshListener getOnRefreshListener() {
        return this.onRefreshListener;
    }

    public void setOnRefreshListener(final OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    @Override
    public void onClick(final View v) {
        if (isEmpty()) {
            refresh();
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // CHECKSTYLE IGNORE ALL CHECKS NEXT LINE
        this.activationThreshold = (7 * this.header.getMeasuredHeight()) / 8;
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
            final int visibleItemCount, final int totalItemCount) {
        // XXX: find a way to preserve short circuits AND have Eclipse break each expression on its
        // own line to make things readable.
        boolean isEventInteresting = this.scrollState == SCROLL_STATE_TOUCH_SCROLL;

        isEventInteresting &= firstVisibleItem == 0;
        isEventInteresting &= !this.isRefreshing;

        if (isEventInteresting) {
            final boolean isInActivationZone = this.header.getBottom() >= this.activationThreshold;

            if (!this.isActivated && isInActivationZone) {
                activateRefresh();
            } else if (!isInActivationZone && this.isActivated) {
                deactivateRefresh();
            }
        } else if ((this.scrollState == SCROLL_STATE_FLING) && (firstVisibleItem == 0)) {
            setSelection(1);
        }
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        // XXX: find a way to preserve short circuits AND have Eclipse break each expression on its
        // own line to make things readable.
        boolean isEventInteresting = getFirstVisiblePosition() == 0;

        isEventInteresting &= ev.getAction() == MotionEvent.ACTION_UP;
        isEventInteresting &= this.isActivated;

        if (isEventInteresting) {
            refresh();

            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        super.setAdapter(adapter);

        if (!isEmpty()) {
            setSelection(1);
        }
    }

    private void init() {
        this.rotateDownwards = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_downwards);
        this.rotateUpwards = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_upwards);

        this.header = getLayoutInflater().inflate(R.layout.row_header_pulltorefresh, null);
        this.header.setOnClickListener(this);

        this.arrow = ViewUtils.find(this.header, R.id.row_header_pulltorefresh_arrow);
        this.message = ViewUtils.find(this.header, R.id.row_header_pulltorefresh_message);
        this.progressBar = ViewUtils.find(this.header, R.id.row_header_pulltorefresh_progressbar);

        addHeaderView(this.header, null, false);
        setEmptyHeader();
        setOnScrollListener(this);
        setSmoothScrollbarEnabled(true);
    }

    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private void setEmptyHeader() {
        this.arrow.setVisibility(View.INVISIBLE);
        this.message.setText(R.string.row_header_pulltorefresh_empty);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    private boolean isEmpty() {
        if (getAdapter() == null) {
            return true;
        } else {
            return getAdapter().isEmpty();
        }
    }

    private void resetHeader() {
        this.arrow.clearAnimation();
        this.arrow.setVisibility(View.VISIBLE);
        this.message.setText(R.string.row_header_pulltorefresh_pullDown);
        this.progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        this.arrow.clearAnimation();
        this.arrow.setVisibility(View.INVISIBLE);
        this.message.setText(R.string.row_header_pulltorefresh_loading);
        this.progressBar.setVisibility(View.VISIBLE);
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
