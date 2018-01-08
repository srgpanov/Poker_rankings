package com.srgpanov.poker_rankings.Model.DataModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srgpa on 19.05.2017.
 */

public class BestWon  implements Parcelable{
    private int top1;
    private int top2;
    private int top3;
    private int top4;
    private int top5;

    public BestWon() {

    }

    public BestWon(int top1, int top2, int top3, int top4, int top5) {
        this.top1 = top1;
        this.top2 = top2;
        this.top3 = top3;
        this.top4 = top4;
        this.top5 = top5;
    }

    public int getTop1() {
        return top1;
    }

    public void setTop1(int top1) {
        this.top1 = top1;
    }

    public int getTop2() {
        return top2;
    }

    public void setTop2(int top2) {
        this.top2 = top2;
    }

    public int getTop3() {
        return top3;
    }

    public void setTop3(int top3) {
        this.top3 = top3;
    }

    public int getTop4() {
        return top4;
    }

    public void setTop4(int top4) {
        this.top4 = top4;
    }

    public int getTop5() {
        return top5;
    }

    public void setTop5(int top5) {
        this.top5 = top5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.top1);
        dest.writeInt(this.top2);
        dest.writeInt(this.top3);
        dest.writeInt(this.top4);
        dest.writeInt(this.top5);
    }

    protected BestWon(Parcel in) {
        this.top1 = in.readInt();
        this.top2 = in.readInt();
        this.top3 = in.readInt();
        this.top4 = in.readInt();
        this.top5 = in.readInt();
    }

    public static final Creator<BestWon> CREATOR = new Creator<BestWon>() {
        @Override
        public BestWon createFromParcel(Parcel source) {
            return new BestWon(source);
        }

        @Override
        public BestWon[] newArray(int size) {
            return new BestWon[size];
        }
    };
}
