package com.srgpanov.poker_rankings.Model.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srgpa on 19.05.2017.
 */

public class AVGFinishesPercent implements Parcelable {
    private int early;
    private int early_middle;
    private int middle;
    private int middle_late;
    private int late;

    public AVGFinishesPercent() {
    }

    public AVGFinishesPercent(int early, int early_middle, int middle, int middle_late, int late) {
        this.early = early;
        this.early_middle = early_middle;
        this.middle = middle;
        this.middle_late = middle_late;
        this.late = late;
    }

    public int getEarly() {
        return early;
    }

    public void setEarly(int early) {
        this.early = early;
    }

    public int getEarly_middle() {
        return early_middle;
    }

    public void setEarly_middle(int early_middle) {
        this.early_middle = early_middle;
    }

    public int getMiddle() {
        return middle;
    }

    public void setMiddle(int middle) {
        this.middle = middle;
    }

    public int getMiddle_late() {
        return middle_late;
    }

    public void setMiddle_late(int middle_late) {
        this.middle_late = middle_late;
    }

    public int getLate() {
        return late;
    }

    public void setLate(int late) {
        this.late = late;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.early);
        dest.writeInt(this.early_middle);
        dest.writeInt(this.middle);
        dest.writeInt(this.middle_late);
        dest.writeInt(this.late);
    }

    protected AVGFinishesPercent(Parcel in) {
        this.early = in.readInt();
        this.early_middle = in.readInt();
        this.middle = in.readInt();
        this.middle_late = in.readInt();
        this.late = in.readInt();
    }

    public static final Parcelable.Creator<AVGFinishesPercent> CREATOR = new Parcelable.Creator<AVGFinishesPercent>() {
        @Override
        public AVGFinishesPercent createFromParcel(Parcel source) {
            return new AVGFinishesPercent(source);
        }

        @Override
        public AVGFinishesPercent[] newArray(int size) {
            return new AVGFinishesPercent[size];
        }
    };
}
