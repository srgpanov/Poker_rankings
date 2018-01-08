package com.srgpanov.poker_rankings.View;


import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;

import java.util.List;

/**
 * Created by srgpa on 17.05.2017.
 */

public interface MainActivityInterface  {
    void showAllPlayers(List<PlayerProfile> players);
    void showPlayers(PlayerProfile profile);



}
