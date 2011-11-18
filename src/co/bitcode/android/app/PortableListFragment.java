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
import android.support.v4.app.ListFragment;
import android.view.View;

import co.bitcode.android.R;

/**
 * Makes it easy to override the default {@link ListFragment} layout whilst supporting transitions
 * between progress view and content view.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public class PortableListFragment extends ListFragment {
    private static final int INTERNAL_EMPTY_ID = 0x00ff0001;
    private static final int INTERNAL_PROGRESS_CONTAINER_ID = 0x00ff0002;
    private static final int INTERNAL_LIST_CONTAINER_ID = 0x00ff0003;

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        changeId(view, R.id.include_list_content_container, INTERNAL_LIST_CONTAINER_ID);
        changeId(view, R.id.include_list_content_empty, INTERNAL_EMPTY_ID);
        changeId(view, R.id.include_list_content_progressContainer, INTERNAL_PROGRESS_CONTAINER_ID);

        super.onViewCreated(view, savedInstanceState);
    }

    private void changeId(final View parentView, final int oldId, final int newId) {
        final View view = parentView.findViewById(oldId);

        if (view != null) {
            view.setId(newId);
        }
    }
}
