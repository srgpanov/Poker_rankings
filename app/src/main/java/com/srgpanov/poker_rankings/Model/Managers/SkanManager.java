package com.srgpanov.poker_rankings.Model.Managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srgpa on 23.05.2017.
 */

public class SkanManager {
    private TextRecognizer decoder;
    private int bitmapWidth;
    private int bitmapHeight;
    private List<PlayerProfile> mProfiles;
    private Bitmap srcBitmap;
    private List<PlayersArea> mPlayersBitmap;
    private int playersNickWidth;
    private int playersNickHeight;

    public SkanManager(Context context) {
        decoder = new TextRecognizer.Builder(context).build();
    }
    public List<PlayerProfile> getPlayersProfile(String scrnPath) {
        srcBitmap = BitmapFactory.decodeFile(scrnPath).copy(Bitmap.Config.ARGB_8888, true);
        bitmapWidth = srcBitmap.getWidth();
        bitmapHeight = srcBitmap.getHeight();
        playersNickWidth = (int) (bitmapWidth / 8.35);
        playersNickHeight = (int) (bitmapHeight/ 21.6);
        mProfiles = new ArrayList<>();
        mPlayersBitmap = new ArrayList<>();
        for (int i = 0; i < srcBitmap.getWidth(); i++) {
            for (int j = 0; j < srcBitmap.getHeight(); j++) {
                if (srcBitmap.getPixel(i, j) == Color.rgb(192, 248, 181)) {
                    if (dotInPlayerArea(i, j)) {
                        PlayersArea playersArea = findNearDot(srcBitmap, i, j);
                        mPlayersBitmap.add(playersArea);
                        PlayersArea cashArea = selectCashArea(srcBitmap, playersArea);
                        Bitmap avatarBitmap =selectAvatar(srcBitmap, cashArea);
                        Bitmap nickBitmap = selectNick(srcBitmap,cashArea);
                        String nickname = getPlayersNickName(nickBitmap);
                        PlayerProfile profile = new PlayerProfile();
                        profile.setNickName(nickname);
                        profile.setAvatar(avatarBitmap);
                        mProfiles.add(profile);
                    }
                }
            }
        }
        return mProfiles;
    }
    private Bitmap selectAvatar(Bitmap bitmap, PlayersArea cashArea) {
        int left, right, top, bottom;
        top = (int) (cashArea.getBottom() - ((cashArea.getBottom() - cashArea.getTop()) * 2.125));
        bottom = cashArea.getBottom();
        if (cashArea.getLeft() > bitmapWidth / 2) {
            Log.d("arealeft",String.valueOf(cashArea.getLeft()));
            left = cashArea.getRight();
            right = (int) (cashArea.getRight() + ((cashArea.getRight() - cashArea.getLeft()) / 2.3));
            return Bitmap.createBitmap(
                    bitmap,
                    left+3,
                    top,
                    right - left + 3,
                    bottom - top);
        } else {
            Log.d("arearight",String.valueOf(cashArea.getLeft()));
            Log.d("arearight",String.valueOf(bitmapWidth/2));
            right = cashArea.getLeft();
            left =(int)(cashArea.getRight()-((cashArea.getRight()-cashArea.getLeft())*1.47));
            return Bitmap.createBitmap(
                    bitmap,
                    left,
                    top,
                    right-left-3,
                    bottom-top);
        }
    }
    private Bitmap selectNick(Bitmap  bitmap, PlayersArea area){
        int left, right, top, bottom;
        top = (int) (area.getBottom()-((area.getBottom()-area.getTop())*2.125));
        left=area.getLeft()-(bitmapWidth/384);
        return Bitmap.createBitmap(bitmap,left,top,area.getRight()-area.getLeft()+(bitmapWidth/384),(int)((area.getBottom()-area.getTop())*1.1));
    }

    private PlayersArea selectCashArea(Bitmap originalBitmap, PlayersArea area) {
        PlayersArea cashArea = new PlayersArea(0, 0, 0, 0);
        for (int i = area.getLeft() - 5; i >= 0; i--) {
            int pixel = originalBitmap.getPixel(i, area.getCentreVertical());
            if (Color.green(pixel) < 60) {
                originalBitmap.setPixel(i, area.getCentreVertical(), Color.RED);
            } else {
                cashArea.setLeft(i + 1);
                break;
            }
        }
        for (int i = area.getRight() + 5; i <= bitmapWidth; i++) {
            int pixel = originalBitmap.getPixel(i, area.getCentreVertical());
            if (Color.green(pixel) < 60) {
                originalBitmap.setPixel(i, area.getCentreVertical(), Color.RED);
            } else {
                cashArea.setRight(i - 1);
                break;
            }
        }

        for (int i = area.getTop() - 5; i >= 0; i--) {
            int pixel = originalBitmap.getPixel(area.getCentreHorizontal(), i);
            if (Color.green(pixel) < 60) {
                originalBitmap.setPixel(area.getCentreHorizontal(), i, Color.RED);
            } else {
                cashArea.setTop(i + 1);
                break;
            }
        }
        for (int i = area.getBottom() + 5; i <= bitmapHeight; i++) {
            int pixel = originalBitmap.getPixel(area.getCentreHorizontal(), i);
            if (Color.green(pixel) < 60) {
                originalBitmap.setPixel(area.getCentreHorizontal(), i, Color.RED);
            } else {
                cashArea.setBottom(i - 1);
                break;
            }
        }
        return cashArea;
    }


    private boolean dotInPlayerArea(int x, int y) {
        for (PlayersArea area : mPlayersBitmap) {
            boolean horizontal = area.getLeft() <= x && area.getRight() >= x;
            boolean vertical = area.getTop() <= y && area.getBottom() >= y;
            if (horizontal && vertical) return false;
        }
        return true;
    }

    private PlayersArea findNearDot(Bitmap bitmap, int x, int y) {
        int top, bottom, left, right;
        right = x;
        left = x;
        top = y;
        bottom = y;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        for (int i = x - playersNickWidth; i <= x + playersNickWidth; i++) {
            for (int j = y - playersNickHeight; j <= y + playersNickHeight; j++) {
                if (i >= 0 && i < bitmapWidth) {
                    if (bitmap.getPixel(i, j) == Color.rgb(192, 248, 181)) {
                        if (i < left) left = i;
                        if (i > right) right = i;
                    }
                }
            }
        }
        for (int i = left; i <= right; i++) {
            for (int j = y - playersNickHeight; j <= y + playersNickHeight; j++) {
                if (j >= 0 && j < bitmapHeight) {
                    if (bitmap.getPixel(i, j) == Color.rgb(192, 248, 181)) {
                        if (j < top) top = j;
                        if (j > bottom) bottom = j;
                    }

                }
            }
        }
        Log.d("RX56", String.valueOf(left));
        Log.d("RX57", String.valueOf(right));
        Log.d("RX5", String.valueOf(top));
        Log.d("RX5", String.valueOf(bottom));
        return new PlayersArea(left, right, top, bottom);

    }




    public String getPlayersNickName(Bitmap bitmap) {
        String playerNickName = "";
        if(!checkVisionNick(bitmap)) return playerNickName;
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<TextBlock> textBlocks = decoder.detect(frame);
        Log.d("Google", String.valueOf(textBlocks.size()));
        for (int i = 0; i < textBlocks.size(); i++) {
            TextBlock tb = textBlocks.get(i);
            Log.d("Google", tb.getValue());
            playerNickName += tb.getValue();
        }
        return playerNickName;
    }
    private boolean checkVisionNick(Bitmap bitmap){
        int halfHeight = bitmap.getHeight()/2;
        int width = bitmap.getWidth();
        boolean visionNick=true;
        for (int i =0;i<width;i++){
            if(bitmap.getPixel(i, halfHeight) == Color.parseColor("#F6FF62")){
                visionNick = false;
                break;
            }
        }
        return visionNick;
    }
    private class PlayersArea {
        private int top;
        private int bottom;
        private int left;
        private int right;

        public PlayersArea(int left, int right, int top, int bottom) {
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public void setBottom(int bottom) {
            this.bottom = bottom;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public void setRight(int right) {
            this.right = right;
        }

        public int getBottom() {
            return bottom;
        }

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public int getCentreHorizontal() {
            return (right + left) / 2;
        }


        public int getCentreVertical() {
            return (bottom + top) / 2;
        }

    }
}
