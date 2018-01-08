package com.srgpanov.poker_rankings.Model.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srgpa on 19.05.2017.
 */

public class ITMFinishes implements Parcelable {
    private int first;
    private int first_ties;
    private int second;
    private int third;
    private int forty_tenty;

    public ITMFinishes() {

    }

    public ITMFinishes(int first, int first_ties, int second, int third, int forty_tenty) {
        this.first = first;
        this.first_ties = first_ties;
        this.second = second;
        this.third = third;
        this.forty_tenty = forty_tenty;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getFirst_ties() {
        return first_ties;
    }

    public void setFirst_ties(int first_ties) {
        this.first_ties = first_ties;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public int getForty_tenty() {
        return forty_tenty;
    }

    public void setForty_tenty(int forty_tenty) {
        this.forty_tenty = forty_tenty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.first);
        dest.writeInt(this.first_ties);
        dest.writeInt(this.second);
        dest.writeInt(this.third);
        dest.writeInt(this.forty_tenty);
    }

    protected ITMFinishes(Parcel in) {
        this.first = in.readInt();
        this.first_ties = in.readInt();
        this.second = in.readInt();
        this.third = in.readInt();
        this.forty_tenty = in.readInt();
    }

    public static final Parcelable.Creator<ITMFinishes> CREATOR = new Parcelable.Creator<ITMFinishes>() {
        @Override
        public ITMFinishes createFromParcel(Parcel source) {
            return new ITMFinishes(source);
        }

        @Override
        public ITMFinishes[] newArray(int size) {
            return new ITMFinishes[size];
        }
    };
}
