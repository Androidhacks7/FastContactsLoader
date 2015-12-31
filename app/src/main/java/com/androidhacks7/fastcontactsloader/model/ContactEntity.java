/*
 * Copyright (c) 2015 Androidhacks7
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.androidhacks7.fastcontactsloader.model;


import com.androidhacks7.fastcontactsloader.AppConstants;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class ContactEntity {

    private String type;

    private String value;

    public ContactEntity(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public ContactEntity(int type, String value) {
        this.type = resolveNumberToString(type);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String resolveNumberToString(int numberType) {
        if (numberType == AppConstants.TYPE_MOBILE) {
            return "Mobile";
        } else if (numberType == AppConstants.TYPE_HOME) {
            return "Home";
        } else if (numberType == AppConstants.TYPE_HOME_FAX) {
            return "Home Fax";
        } else if (numberType == AppConstants.TYPE_WORK) {
            return "Work";
        } else if (numberType == AppConstants.TYPE_PAGER) {
            return "Pager";
        } else if (numberType == AppConstants.TYPE_WORK_FAX) {
            return "Work Fax";
        } else if (numberType == AppConstants.TYPE_CUSTOM) {
            return "Custom";
        } else {
            return "Other";
        }
    }
}
