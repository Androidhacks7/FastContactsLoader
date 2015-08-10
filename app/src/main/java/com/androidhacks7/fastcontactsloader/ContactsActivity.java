package com.androidhacks7.fastcontactsloader;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import com.androidhacks7.fastcontactsloader.listeners.ContactSelectListener;
import com.androidhacks7.fastcontactsloader.model.Contact;
import com.androidhacks7.fastcontactsloader.ui.ContactListFragment;

/**
 *
 */
public class ContactsActivity extends Activity implements ContactSelectListener {

    private ContactListFragment contactListFragment;

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
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
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
            contactListFragment.onSearch("");
        }
    }
}
