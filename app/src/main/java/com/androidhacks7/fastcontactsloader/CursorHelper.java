/*
 * Copyright (c) 2015 Androidhacks7
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.androidhacks7.fastcontactsloader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.androidhacks7.fastcontactsloader.model.Contact;
import com.androidhacks7.fastcontactsloader.model.ContactEntity;

import java.util.LinkedList;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class CursorHelper {

    private static final String[] projectionFields = new String[]{
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.PHOTO_URI,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    public CursorLoader constructLoader(Context context, String searchString) {

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        if (searchString != null) {
            uri = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,
                    Uri.encode(searchString));
        }
        CursorLoader cursorLoader = new CursorLoader(context,
                uri, projectionFields,
                null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " COLLATE NOCASE ASC");
        return cursorLoader;
    }

    public Cursor eliminateDuplicatesInCursor(Cursor cursor) {

        MatrixCursor modifiedCursor = new MatrixCursor(projectionFields);
        if (cursor.moveToFirst()) {
            String lastName = new String();
            do {
                String displayName = cursor
                        .getString(
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                if (displayName.compareToIgnoreCase(lastName) != 0) {

                    modifiedCursor.addRow(new Object[]{cursor.getString(0),
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),
                            cursor.getString(5)}); // match original cursor fields

                    lastName = displayName;
                }
            } while (cursor.moveToNext());
        }
        return modifiedCursor;
    }

    public Contact getContactFromCursor(Cursor cursor, int position, Context context) {
        if (!cursor.moveToPosition(position)) {
            return null;
        }

        Contact contact = new Contact();
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        contact.setDisplayName(name);
        contact.setImageUri(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)));

        LinkedList<ContactEntity> numbers = new LinkedList<ContactEntity>();

        Cursor phoneNumberCursor = context
                .getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.Contacts.DISPLAY_NAME
                                + " = ?",
                        new String[]{name}, null);
        while (phoneNumberCursor.moveToNext()) {
            int type = phoneNumberCursor.getInt(phoneNumberCursor
                    .getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));

            String phoneNo = phoneNumberCursor.getString(phoneNumberCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactEntity contactEntity = new ContactEntity(type, phoneNo);
            numbers.add(contactEntity);
        }
        phoneNumberCursor.close();

        Cursor emailCursor = context
                .getContentResolver()
                .query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null,
                        ContactsContract.Contacts.DISPLAY_NAME
                                + " = ?",
                        new String[] { name }, null);
        while (emailCursor.moveToNext()) {

            int type = emailCursor.getInt(emailCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
            String email_address = emailCursor.getString(emailCursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

            ContactEntity contactEntity = new ContactEntity(type, email_address);
            numbers.add(contactEntity);
        }
        emailCursor.close();

        contact.setContactEntities(numbers);
        return contact;
    }
}
