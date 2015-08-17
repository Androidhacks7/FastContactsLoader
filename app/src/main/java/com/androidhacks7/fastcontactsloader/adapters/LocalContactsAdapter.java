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
