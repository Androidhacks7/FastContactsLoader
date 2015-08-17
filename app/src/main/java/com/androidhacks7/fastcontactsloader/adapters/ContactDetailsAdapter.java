package com.androidhacks7.fastcontactsloader.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.androidhacks7.fastcontactsloader.R;
import com.androidhacks7.fastcontactsloader.model.ContactEntity;

import java.util.LinkedList;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class ContactDetailsAdapter extends BaseAdapter {

    private LinkedList<ContactEntity> contactNumbers;
    private Context context;

    public ContactDetailsAdapter(Context context, LinkedList<ContactEntity> contactNumbers) {
        this.context = context;
        this.contactNumbers = contactNumbers;
    }

    @Override
    public int getCount() {
        return contactNumbers.size();
    }

    @Override
    public Object getItem(int position) {
        return contactNumbers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.contact_detail_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.phoneType = (TextView) view.findViewById(R.id.contact_type);
            viewHolder.phoneNumber = (TextView) view.findViewById(R.id.contact_number);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.phoneType.setText(contactNumbers.get(position).getType());
        viewHolder.phoneNumber.setText(contactNumbers.get(position).getValue());

        return view;
    }

    private static class ViewHolder {
        private TextView phoneType;
        private TextView phoneNumber;
    }
}
