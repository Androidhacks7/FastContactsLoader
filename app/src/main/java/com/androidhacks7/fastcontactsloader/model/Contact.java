package com.androidhacks7.fastcontactsloader.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

/**
 * Created by androidhacks7 on 8/7/2015.
 */
public class Contact implements Parcelable {

    private String displayName;
    private String imageUri;
    private LinkedList<ContactEntity> contactEntities = new LinkedList<>();

    public Contact() {
    }

    public Contact(Parcel in) {
        this.displayName = in.readString();
        this.imageUri = in.readString();
        int phoneNumberCount = in.readInt();
        for (int i = 0; i < phoneNumberCount; i++) {
            contactEntities.add(new ContactEntity(in.readString(), in.readString()));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(imageUri);
        dest.writeInt(contactEntities.size());
        for (ContactEntity contactEntity : contactEntities) {
            dest.writeString(contactEntity.getType());
            dest.writeString(contactEntity.getValue());
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public LinkedList<ContactEntity> getContactEntities() {
        return contactEntities;
    }

    public void setContactEntities(LinkedList<ContactEntity> contactEntities) {
        this.contactEntities = contactEntities;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

}
