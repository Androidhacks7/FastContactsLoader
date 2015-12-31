/*
 * Copyright (c) 2015 Androidhacks7
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.androidhacks7.fastcontactsloader.ui;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidhacks7.fastcontactsloader.R;
import com.androidhacks7.fastcontactsloader.adapters.ContactDetailsAdapter;
import com.androidhacks7.fastcontactsloader.model.Contact;

import java.util.Random;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class IndividualContactFragment extends Fragment {

    private Contact contact;

    public void setCurrentContact(Contact contact) {
        this.contact = contact;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_individual_contact, container, false);
        ListView detailsView = (ListView) rootView.findViewById(R.id.detail_list);
        if (contact.getImageUri() != null) {
            ImageView displayImage = (ImageView) rootView.findViewById(R.id.display_image);
            displayImage.setImageURI(Uri.parse(contact.getImageUri()));
        } else {
            int colors[] = getResources().getIntArray(R.array.contact_colors);
            int randomNum = new Random().nextInt(6);
            TextView contactInitial = (TextView) rootView.findViewById(R.id.contact_initial);
            contactInitial.setVisibility(View.VISIBLE);
            contactInitial.setBackgroundColor(colors[randomNum]);
            contactInitial.setText("" + contact.getDisplayName().charAt(0));
        }
        ContactDetailsAdapter contactDetailsAdapter = new ContactDetailsAdapter(getActivity(), contact.getContactEntities());
        detailsView.setAdapter(contactDetailsAdapter);
        return rootView;
    }
}
