package com.srgpanov.poker_rankings.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;
import com.srgpanov.poker_rankings.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srgpa on 25.05.2017.
 */

public class PlayersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int HIDDEN_NOT_FOUNDED = 0, FOUNDED = 1;
    Context mContext;
    List<PlayerProfile> mPlayerProfiles;
    private onPlayersClickListener mPlayersClickListener;

    public PlayersAdapter(List<PlayerProfile> profiles, onPlayersClickListener listener) {
        mPlayerProfiles = profiles;
        mPlayersClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (!mPlayerProfiles.get(position).isFounded() || mPlayerProfiles.get(position).isHidden()) {
            return HIDDEN_NOT_FOUNDED;
        } else return FOUNDED;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case HIDDEN_NOT_FOUNDED:
                View viewHidden = inflater.inflate(R.layout.item_players_hidden_or_not_founded, parent, false);
                viewHolder = new PlayerHiddenViewHolder(viewHidden);
                break;
            case FOUNDED:
                View viewFounded = inflater.inflate(R.layout.item_players, parent, false);
                viewHolder = new PlayerViewHolder(viewFounded);
                break;
            default:
                View viewFounded1 = inflater.inflate(R.layout.item_players, parent, false);
                viewHolder = new PlayerViewHolder(viewFounded1);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HIDDEN_NOT_FOUNDED:
                PlayerHiddenViewHolder hiddenViewHolder = (PlayerHiddenViewHolder) holder;
                configureHiddenHolder(hiddenViewHolder, position);
                break;
            case FOUNDED:
                PlayerViewHolder playerViewHolder = (PlayerViewHolder) holder;
                configurePlayerHolder(playerViewHolder, position);
                break;
        }

    }

    private void configurePlayerHolder(PlayerViewHolder holder, int position) {
        Log.d("ViewHolder", mPlayerProfiles.get(position).toString());

        holder.tv_nickname.setText(mPlayerProfiles.get(position).getNickName());
        if (mPlayerProfiles.get(position).getAvatar() != null) {
            holder.img_avatar.setImageBitmap(mPlayerProfiles.get(position).getAvatar());
        } else {
            holder.img_avatar.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.default_avatar));
            Log.d("avatar","avatar");
        }
        holder.prizes_text_view.setText(mPlayerProfiles.get(position).getPrizesWon());
        holder.profit_text_view.setText(mPlayerProfiles.get(position).getNetProfit());
        holder.roi_text_view.setText(mPlayerProfiles.get(position).getROI());
        holder.buy_in_text_view.setText(mPlayerProfiles.get(position).getAvgBuyIn());
        holder.field_size_text_view.setText(String.valueOf(mPlayerProfiles.get(position).getAvgFS()));
        holder.rebuy_text_view.setText(mPlayerProfiles.get(position).getRebuy_addon());
        holder.itm_percent_text_view.setText(mPlayerProfiles.get(position).getItm_percent());
    }

    private void configureHiddenHolder(PlayerHiddenViewHolder hiddenViewHolder, int position) {
        String hiddenProfileText;
        hiddenViewHolder.img_avatar.setImageBitmap(mPlayerProfiles.get(position).getAvatar());
        if (mPlayerProfiles.get(position).isHidden()) {
            hiddenProfileText = mPlayerProfiles.get(position).getNickName() + "has been excluded from Official Poker Rankings";
            hiddenViewHolder.tv_nickname.setText(mPlayerProfiles.get(position).getNickName());
            hiddenViewHolder.hide_notfounded.setText(hiddenProfileText);
        }
        if (!mPlayerProfiles.get(position).isFounded()) {
            hiddenProfileText = mPlayerProfiles.get(position).getNickName() + " not founded on Official Poker Rankings";
            hiddenViewHolder.tv_nickname.setText(mPlayerProfiles.get(position).getNickName());
            hiddenViewHolder.hide_notfounded.setText(hiddenProfileText);
        }

    }


    public void addItem(PlayerProfile profile) {
        mPlayerProfiles.add(0,profile);
        notifyItemInserted(mPlayerProfiles.size());

    }

    @Override
    public int getItemCount() {
        return mPlayerProfiles.size();
    }

    public interface onPlayersClickListener {
        void onPlayersItemClick(int position, View view);
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_nickname;
        private ImageView img_avatar;
        private TextView prizes_text_view;
        private TextView profit_text_view;
        private TextView roi_text_view;
        private TextView buy_in_text_view;
        private TextView field_size_text_view;
        private TextView rebuy_text_view;
        private TextView itm_percent_text_view;


        public PlayerViewHolder(View itemView) {
            super(itemView);
            tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            prizes_text_view = (TextView) itemView.findViewById(R.id.tv_prizes_value);
            profit_text_view = (TextView) itemView.findViewById(R.id.tv_profit_value);
            roi_text_view = (TextView) itemView.findViewById(R.id.tv_roi_value);
            buy_in_text_view = (TextView) itemView.findViewById(R.id.tv_avg_buy_in_value);
            field_size_text_view = (TextView) itemView.findViewById(R.id.tv_avg_fs_value);
            rebuy_text_view = (TextView) itemView.findViewById(R.id.tv_rebuy_addon_value);
            itm_percent_text_view = (TextView) itemView.findViewById(R.id.tv_itm_percent_value);
            tv_nickname.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mPlayersClickListener != null) {
                mPlayersClickListener.onPlayersItemClick(getAdapterPosition(), v);
            }
        }
    }

    class PlayerHiddenViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView img_avatar;
        private TextView tv_nickname;
        private TextView hide_notfounded;


        public PlayerHiddenViewHolder(View itemView) {
            super(itemView);
            img_avatar = (ImageView) itemView.findViewById(R.id.img_avatar);
            tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname_hide_not_founded);
            hide_notfounded = (TextView) itemView.findViewById(R.id.hide_not_founded);
        }

        @Override
        public void onClick(View view) {
            if (mPlayersClickListener != null) {
                mPlayersClickListener.onPlayersItemClick(getAdapterPosition(), view);
            }
        }
    }
}
