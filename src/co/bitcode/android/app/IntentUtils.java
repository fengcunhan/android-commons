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

import android.content.Context;
import android.content.Intent;

import co.bitcode.android.R;

/**
 * Various intents for common tasks.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
public final class IntentUtils {
    private IntentUtils() {
    }

    /**
     * Starts an Intent to send an email message.
     * 
     * @param context
     *        The {@link Context}.
     * @param subject
     *        Email subject.
     * @param recipients
     *        Recipient email addresses.
     * @since 1.0.0
     */
    public static void sendEmail(final Context context, final String subject,
            final String... recipients) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        final Intent chooser;

        // This should filter out most applications which respond with "text/plain" and hopefully
        // leave us only with proper Email applications.
        intent.setType("multipart/alternative");

        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        chooser = Intent.createChooser(intent, context.getString(R.string.intent_sendEmail));

        context.startActivity(chooser);
    }
}
