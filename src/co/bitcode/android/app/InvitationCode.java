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

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import co.bitcode.android.R;
import co.bitcode.android.view.inputmethod.KeyboardHelper;
import co.bitcode.android.widget.ViewUtils;

/**
 * A simple dialog which asks for an invitation code to continue using the application. It can be
 * used to create "private beta" applications on the Android Market.
 * 
 * This is by no means a way to protect your application from unauthorized uses since it can be
 * bypassed very easily.
 * 
 * Valid invitation codes entered by users are stored in a {@link SharedPreferences} and matched
 * with the expected invitation code every time the verify method is invoked. This allows you to
 * change the expected invitation code and invalidate previous codes.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class InvitationCode extends AlertDialog implements OnClickListener {
    private static final String PREFERENCES = "invitationcode";
    private static final String PREFERENCE_CODE = "code";

    private final OnVerifiedListener verifiedListener;
    private final String expectedCode;
    private final SharedPreferences preferences;

    private EditText invitationCode;
    private TextView errorString;

    // CHECKSTYLE IGNORE ALL CHECKS FOR NEXT 2 LINES
    public interface OnVerifiedListener {
        void onInviteCodeVerified();
    }

    private InvitationCode(final Context context, final String expectedCode,
            final SharedPreferences preferences, final OnVerifiedListener verifiedListener) {
        super(context);

        this.verifiedListener = verifiedListener;
        this.expectedCode = expectedCode;
        this.preferences = preferences;

        init();
    }

    /**
     * Verifies stored invitation code (if any). If the stored invitation code dosen't match the
     * expected invitation code it shows a non-cancelable dialog asking to insert it.
     * 
     * Closing the dialog will cause the application to die.
     * 
     * This method should be invoked as soon as possible in your main activity.
     * 
     * @param context
     *        The {@link Context}.
     * @param expectedCode
     *        Expected invitation code.
     * @param dismissListener
     *        Handler for when the dialog is being dismissed.
     */
    public static void verify(final Context context, final String expectedCode,
            final OnVerifiedListener dismissListener) {
        final SharedPreferences sp = ContextUtils.getPrivatePreferences(context, PREFERENCES);

        if (!sp.getString(PREFERENCE_CODE, "").equals(expectedCode)) {
            new InvitationCode(context, expectedCode, sp, dismissListener).show();
        } else {
            dismissListener.onInviteCodeVerified();
        }
    }

    @Override
    public void onClick(final View v) {
        if (v.equals(this.invitationCode)) {
            onPositiveButtonClicked();
        } else if (v.getId() == android.R.id.button1) {
            onPositiveButtonClicked();
        } else if (v.getId() == android.R.id.button2) {
            onNegativeButtonClicked();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.invitationCode = ViewUtils.find(this, R.id.dialog_invitationcode_code);
        this.errorString = ViewUtils.find(this, R.id.dialog_invitationcode_error);

        // By default the AlertDialog dismisses itself whenever a button is clicked (by setting up)
        // a proxy on-click listener. Forcefully overwrite that proxy listener with ours.
        findViewById(android.R.id.button1).setOnClickListener(this);
        findViewById(android.R.id.button2).setOnClickListener(this);

        KeyboardHelper.setEditorAction(this.invitationCode, this);

        hideErrorString();
    }

    private void init() {
        setView(getLayoutInflater().inflate(R.layout.dialog_invitationcode, null));

        // By default the AlertDialog dismisses itself whenever a button is clicked. This is just a
        // dummy call to have the AlertDialog display the buttons.
        setButton(BUTTON_POSITIVE, getContext().getString(android.R.string.ok), new Message());
        setButton(BUTTON_NEGATIVE, getContext().getString(android.R.string.cancel), new Message());

        setCancelable(false);
    }

    private void hideErrorString() {
        this.errorString.setVisibility(View.GONE);
    }

    private void onPositiveButtonClicked() {
        if (this.invitationCode.getText().toString().equals(this.expectedCode)) {
            final Editor editor = this.preferences.edit();

            editor.putString(PREFERENCE_CODE, this.expectedCode);
            editor.commit();

            dismiss();

            this.verifiedListener.onInviteCodeVerified();
        } else {
            showErrorString();
        }
    }

    private void showErrorString() {
        this.errorString.setVisibility(View.VISIBLE);
    }

    private void onNegativeButtonClicked() {
        // This is probably the worst way to forcefully terminate the application.
        System.exit(0);
    }
}
