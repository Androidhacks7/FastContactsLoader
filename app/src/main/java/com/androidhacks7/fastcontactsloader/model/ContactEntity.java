package com.androidhacks7.fastcontactsloader.model;


import com.androidhacks7.fastcontactsloader.AppConstants;

/**
 *
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
