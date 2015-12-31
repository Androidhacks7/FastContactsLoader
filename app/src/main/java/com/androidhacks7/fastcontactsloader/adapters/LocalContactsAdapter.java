/*
 * Copyright (c) 2015 Androidhacks7
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.androidhacks7.fastcontactsloader.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidhacks7.fastcontactsloader.R;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class LocalContactsAdapter extends CursorAdapter {

    private Context context;

    public LocalContactsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String displayImageUri = cursor.getString(cursor
                .getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        ImageView displayImage = (ImageView) view.findViewById(R.id.contact_picture);
        TextView displayName = (TextView) view.findViewById(R.id.contact_name);
        TextView contactInitial = (TextView) view.findViewById(R.id.contact_initial);
        if (displayImageUri == null) {
            displayImage.setVisibility(View.GONE);
            contactInitial.setVisibility(View.VISIBLE);
            contactInitial.setText("" + contactName.charAt(0));
        } else {
            displayImage.setVisibility(View.VISIBLE);
            contactInitial.setVisibility(View.GONE);
            displayImage.setImageURI(Uri.parse(displayImageUri));
        }
        displayName.setText(contactName);
    }
}
