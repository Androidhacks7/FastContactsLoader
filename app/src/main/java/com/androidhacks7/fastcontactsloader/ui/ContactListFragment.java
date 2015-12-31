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
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidhacks7.fastcontactsloader.CursorHelper;
import com.androidhacks7.fastcontactsloader.R;
import com.androidhacks7.fastcontactsloader.adapters.LocalContactsAdapter;
import com.androidhacks7.fastcontactsloader.listeners.ContactSelectListener;
import com.androidhacks7.fastcontactsloader.model.Contact;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class ContactListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private LocalContactsAdapter localContactsAdapter;
    private ContactSelectListener contactSelectionListener;
    private CursorHelper cursorHelper;
    private String searchString;

    public void setContactSelectionListener(ContactSelectListener contactSelectionListener) {
        this.contactSelectionListener = contactSelectionListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ListView contactsList = (ListView) rootView.findViewById(R.id.contact_list);
        localContactsAdapter = new LocalContactsAdapter(getActivity(), null, false);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (contactSelectionListener != null) {
                    Cursor cursor = localContactsAdapter.getCursor();
                    Contact contact = cursorHelper.getContactFromCursor(cursor, position, getActivity());
                    cursor.close();
                    contactSelectionListener.onContactSelected(contact);
                }
            }
        });
        contactsList.setAdapter(localContactsAdapter);
        cursorHelper = new CursorHelper();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return cursorHelper.constructLoader(getActivity(), searchString);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        localContactsAdapter.swapCursor(cursorHelper.eliminateDuplicatesInCursor(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        localContactsAdapter.swapCursor(null);
    }


    public void onSearch(String searchText) {
        searchString = searchText.isEmpty() ? null : searchText;
        getLoaderManager().restartLoader(0, null, this);
    }
}
