package com.srgpanov.poker_rankings.Presenter;

import com.srgpanov.poker_rankings.View.MainActivityInterface;

/**
 * Created by srgpanov on 05.08.2017.
 */

public interface MainActivityPresenterInterface extends Presenter<MainActivityInterface> {
    void searchPlayer(String nickname);
    void searchPlayers();
    void auth();
    void chooseScrn();
    void searchPlayers(String scrnPath);
}
