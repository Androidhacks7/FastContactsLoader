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

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuCompat;
import android.view.Menu;
import android.widget.SearchView;

import com.androidhacks7.fastcontactsloader.listeners.ContactSelectListener;
import com.androidhacks7.fastcontactsloader.model.Contact;
import com.androidhacks7.fastcontactsloader.ui.ContactListFragment;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class ContactsActivity extends Activity implements ContactSelectListener {

    private ContactListFragment contactListFragment;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_green)));
        if (savedInstanceState == null) {
            contactListFragment = new ContactListFragment();
            contactListFragment.setContactSelectionListener(this);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.container, contactListFragment);
            ft.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (contactListFragment != null) {
                    contactListFragment.onSearch(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public void onContactSelected(Contact contact) {
        Intent intent = new Intent(ContactsActivity.this, IndividualContactActivity.class);
        intent.putExtra("Contact", contact);
        startActivityForResult(intent, AppConstants.INDIVIDUAL_CONTACT_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.INDIVIDUAL_CONTACT_ACTIVITY) {
            if (searchView != null && !searchView.isIconified()) {
                onBackPressed();
            }
            contactListFragment.onSearch("");
        }
    }
}
