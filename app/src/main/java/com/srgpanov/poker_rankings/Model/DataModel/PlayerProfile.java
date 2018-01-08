package com.srgpanov.poker_rankings.Model.DataModel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srgpa on 19.05.2017.
 */

public class PlayerProfile implements Parcelable {
    private String nickName;
    private String prizesWon;
    private String netProfit;
    private String ROI;
    private String avgBuyIn;
    private int avgFS;
    private String rebuy_addon;
    private String itm_played;
    private String itm_percent;
    private BestWon bestWon;
    private ITMFinishes itmFinishes;
    private AVGFinishesPercent finishesPercent;
    private Bitmap avatar;
    private boolean founded = true;
    private boolean hidden =false;

    public PlayerProfile() {
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isFounded() {
        return founded;
    }

    public void setFounded(boolean founded) {
        this.founded = founded;
    }

    @Override
    public String toString() {
        return "Nickname:"+nickName+
                " PrizesWon:"+prizesWon+
                " NetProfit:"+netProfit+
                " ROI:"+ROI+
                " avgBuyIn:"+avgBuyIn+
                " avgFS:"+avgFS+
                " RebuyAddon:"+rebuy_addon+
                " itmPlayed:"+itm_played+
                " itmPercent:"+itm_percent+
                " founded:"+founded+
                " hidden:"+hidden;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPrizesWon() {
        return prizesWon;
    }

    public void setPrizesWon(String prizesWon) {
        this.prizesWon = prizesWon;
    }

    public String getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(String netProfit) {
        this.netProfit = netProfit;
    }

    public String getROI() {
        return ROI;
    }

    public void setROI(String ROI) {
        this.ROI = ROI;
    }

    public String getAvgBuyIn() {
        return avgBuyIn;
    }

    public void setAvgBuyIn(String avgBuyIn) {
        this.avgBuyIn = avgBuyIn;
    }

    public int getAvgFS() {
        return avgFS;
    }

    public void setAvgFS(int avgFS) {
        this.avgFS = avgFS;
    }

    public String getRebuy_addon() {
        return rebuy_addon;
    }

    public void setRebuy_addon(String rebuy_addon) {
        this.rebuy_addon = rebuy_addon;
    }

    public String getItm_played() {
        return itm_played;
    }

    public void setItm_played(String itm_played) {
        this.itm_played = itm_played;
    }

    public String getItm_percent() {
        return itm_percent;
    }

    public void setItm_percent(String itm_percent) {
        this.itm_percent = itm_percent;
    }

    public BestWon getBestWon() {
        return bestWon;
    }

    public void setBestWon(BestWon bestWon) {
        this.bestWon = bestWon;
    }

    public ITMFinishes getItmFinishes() {
        return itmFinishes;
    }

    public void setItmFinishes(ITMFinishes itmFinishes) {
        this.itmFinishes = itmFinishes;
    }

    public AVGFinishesPercent getFinishesPercent() {
        return finishesPercent;
    }

    public void setFinishesPercent(AVGFinishesPercent finishesPercent) {
        this.finishesPercent = finishesPercent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nickName);
        dest.writeString(this.prizesWon);
        dest.writeString(this.netProfit);
        dest.writeString(this.ROI);
        dest.writeString(this.avgBuyIn);
        dest.writeInt(this.avgFS);
        dest.writeString(this.rebuy_addon);
        dest.writeString(this.itm_played);
        dest.writeString(this.itm_percent);
        dest.writeParcelable(this.bestWon, flags);
        dest.writeParcelable(this.itmFinishes, flags);
        dest.writeParcelable(this.finishesPercent, flags);
        dest.writeParcelable(this.avatar, flags);
        dest.writeByte(this.founded ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hidden ? (byte) 1 : (byte) 0);
    }

    protected PlayerProfile(Parcel in) {
        this.nickName = in.readString();
        this.prizesWon = in.readString();
        this.netProfit = in.readString();
        this.ROI = in.readString();
        this.avgBuyIn = in.readString();
        this.avgFS = in.readInt();
        this.rebuy_addon = in.readString();
        this.itm_played = in.readString();
        this.itm_percent = in.readString();
        this.bestWon = in.readParcelable(BestWon.class.getClassLoader());
        this.itmFinishes = in.readParcelable(ITMFinishes.class.getClassLoader());
        this.finishesPercent = in.readParcelable(AVGFinishesPercent.class.getClassLoader());
        this.avatar = in.readParcelable(Bitmap.class.getClassLoader());
        this.founded = in.readByte() != 0;
        this.hidden = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PlayerProfile> CREATOR = new Parcelable.Creator<PlayerProfile>() {
        @Override
        public PlayerProfile createFromParcel(Parcel source) {
            return new PlayerProfile(source);
        }

        @Override
        public PlayerProfile[] newArray(int size) {
            return new PlayerProfile[size];
        }
    };
}
