package com.androidhacks7.fastcontactsloader;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.androidhacks7.fastcontactsloader.model.Contact;
import com.androidhacks7.fastcontactsloader.ui.IndividualContactFragment;

/**
 *
 */
public class IndividualContactActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_green)));
        setContentView(R.layout.activity_individual_contact);
        Contact contact = getIntent().getParcelableExtra("Contact");
        getActionBar().setTitle(contact.getDisplayName());
        getActionBar().setDisplayHomeAsUpEnabled(true);

        IndividualContactFragment individualContactFragment = new IndividualContactFragment();
        individualContactFragment.setCurrentContact(contact);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.root_container, individualContactFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
