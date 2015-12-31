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
