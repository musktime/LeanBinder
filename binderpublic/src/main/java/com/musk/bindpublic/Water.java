package com.musk.bindpublic;

import android.os.Parcel;
import android.os.Parcelable;

public class Water implements Parcelable{

    private int type;
    private long value;

    public Water() {
    }

    public Water(Parcel in){
        type=in.readInt();
        value=in.readLong();
    }

    public static final Creator<Water> CREATOR = new Creator<Water>() {
        @Override
        public Water createFromParcel(Parcel in) {
            return new Water(in);
        }

        @Override
        public Water[] newArray(int size) {
            return new Water[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeLong(value);
    }
}
