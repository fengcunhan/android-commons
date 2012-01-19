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

package co.bitcode.com.google.android.c2dm;

/**
 * Constants used to interact with Google's Cloud to Device Messaging Framework Intent-based API.
 * 
 * @since 1.0.0
 * @author Lorenzo Villani
 */
// CHECKSTYLE:OFF
public final class C2DMContract {
    public static final String ACTION_RECEIVE = "com.google.android.c2dm.intent.RECEIVE";
    public static final String ACTION_REGISTER = "com.google.android.c2dm.intent.REGISTER";
    public static final String ACTION_REGISTRATION = "com.google.android.c2dm.intent.REGISTRATION";
    public static final String ACTION_UNREGISTER = "com.google.android.c2dm.intent.UNREGISTER";
    public static final String EXTRA_APP = "app";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_REGISTRATION_ID = "registration_id";
    public static final String EXTRA_SENDER = "sender";
    public static final String EXTRA_UNREGISTERED = "unregistered";

    private C2DMContract() {
    }
}
