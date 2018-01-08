package com.srgpanov.poker_rankings.Model.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.srgpanov.poker_rankings.Model.DataModel.PlayerProfile;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by srgpa on 17.05.2017.
 */

public class OPRManager {
    private Map<String, String> cookie;

    private String login, password;
    private static final String COOCKIE = "COOKIE";
    private static final String KEY_VALUE_DELIMITER = ";";

    public OPRManager() {

    }

    public OPRManager(Context context) {
        this.cookie = loadCookie(context);

    }

    public void setCookie(Map<String, String> cookie) {
        this.cookie = cookie;
    }

    public Map<String, String> getCookie() {
        return cookie;
    }

    public PlayerProfile searchProfile(final PlayerProfile profile) throws IOException {
        String nickname = profile.getNickName();
        Connection.Response firstResponse = Jsoup.connect("http://www.officialpokerrankings.com/playersearch.html")
                .method(Connection.Method.POST)
                .data("playersearch", nickname)
                .data("pr", "4")
                .data("image.x", "6")
                .data("image.y", "5")
                .data("rc", "99877")
                .cookies(cookie)
                .execute();
        Element element = firstResponse.parse();
        if (element.getElementsByAttributeValue("class", "TourTableText").first() != null) {


            Elements element1 = element.getElementsByAttributeValue("class", "TourTableText").first().getElementsByTag("td");
            for (Element element2 : element1) {
                if (element2.text().contains("PokerStars")) {
                    Log.d("Choice", element2.parent().toString());
                    String onmousedown = element2.parent().attr("onmousedown");
                    String[] strings = onmousedown.split("'");
                    Log.d("Choice1", strings[1]);
                    String url = "http://www.officialpokerrankings.com" + strings[1];
                    Connection.Response response = Jsoup.connect(url)
                            .method(Connection.Method.GET)
                            .referrer("http://www.officialpokerrankings.com/playersearch.html")
                            .execute();
                    String[] playerID1 = strings[1].split("playerid=");
                    String[] strings1 = playerID1[1].split("&");
                    String playerID = strings1[0].substring(1, strings1[0].length() - 1);
                    Document document = Jsoup.connect("http://www.officialpokerrankings.com/redir.asp?playerid=" + playerID + "&MonthID=999999")
                            .method(Connection.Method.GET)
                            .cookies(response.cookies())
                            .execute()
                            .parse();
                    Element element3 = document.getElementsByAttributeValue("id", "PlayerContentBackgroundTD")
                            .first()
                            .getElementsByAttributeValue("bgcolor", "#D7E4F1")
                            .first();
                    if (element3 != null) {
                        profile.setPrizesWon(element3.child(1).text());
                        profile.setNetProfit("Not auth");
                        profile.setROI("Not auth");
                        profile.setAvgBuyIn("Not auth");
                        profile.setAvgFS(Integer.valueOf(element3.child(3).text()));
                        profile.setRebuy_addon(element3.child(4).text());
                        profile.setItm_played(element3.child(5).text());
                        profile.setItm_percent(element3.child(6).text());
                        return profile;
                    }

                    Log.d("Choice1", playerID1[1]);
                    Log.d("Choice1", strings1[0]);
                    Log.d("Choice1", playerID);
                }
            }
            Log.d("Choice", element1.toString());
        }
        String notFoundProfile = element
                .getElementsByAttributeValue("id", "ContentBackgroundTable")
                .first()
                .child(0)
                .child(0)
                .child(1)
                .select("font")
                .first()
                .text();
        Log.d("NotFound", notFoundProfile);
        if (notFoundProfile.equals("Search Results")) {
            Log.d("NotFound", String.valueOf(notFoundProfile.equals("Search Results")));
            profile.setFounded(false);
            return profile;
        }
        try {
            String hiddenProfile = element
                    .getElementsByAttributeValue("id", "PlayerContentBackgroundTD")
                    .first()
                    .child(0)
                    .child(0)
                    .child(6)
                    .select("b")
                    .first()
                    .text();
            Log.d("hiddenProfile", hiddenProfile);
            if (hiddenProfile.equals(nickname + " has been excluded from Official Poker Rankings.")) {
                Log.d("hiddenProfile", String.valueOf(true));
                profile.setHidden(true);
                return profile;
            }
        } catch (NullPointerException e) {
            Log.d("hiddenProfile", String.valueOf(false));
        }


        Element openProfile = element
                .getElementsByAttributeValue("id", "PlayerContentBackgroundTD")
                .first()
                .getElementsByAttributeValue("bgcolor", "#D7E4F1")
                .first();
        if (openProfile.children().size() == 9) {

            profile.setPrizesWon(openProfile.child(1).text());
            Log.d("PrizesWon", openProfile.child(1).text());
            profile.setNetProfit(openProfile.child(2).text());
            Log.d("NetProfit", openProfile.child(2).text());
            profile.setROI(openProfile.child(3).text());
            Log.d("ROI", openProfile.child(3).text());
            profile.setAvgBuyIn(openProfile.child(4).text());
            Log.d("AvgBuyIn", openProfile.child(4).text());
            profile.setAvgFS(Integer.valueOf(openProfile.child(5).text()));
            Log.d("AvgAvgFS", openProfile.child(5).text());
            profile.setRebuy_addon(openProfile.child(6).text());
            Log.d("Rebuy_addon", openProfile.child(6).text());
            profile.setItm_played(openProfile.child(7).text());
            Log.d("Itm_played", openProfile.child(7).text());
            profile.setItm_percent(openProfile.child(8).text());
            Log.d("Itm_percent", openProfile.child(8).text());
            Log.d("profile", openProfile.toString());


        }else {
            profile.setPrizesWon(openProfile.child(1).text());
            profile.setNetProfit("Not auth");
            profile.setROI("Not auth");
            profile.setAvgBuyIn("Not auth");
            profile.setAvgFS(Integer.valueOf(openProfile.child(3).text()));
            profile.setRebuy_addon(openProfile.child(4).text());
            profile.setItm_played(openProfile.child(5).text());
            profile.setItm_percent(openProfile.child(6).text());
        }

        Log.d("searched_profile", profile.toString());
        return profile;
    }

    public AuthResponse authOnOPR(String login, String password) throws IOException {

        Log.d("auth", login);
        Log.d("auth", password);

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36 Edge/15.15063";
        Connection.Response response = Jsoup.connect("http://www.officialpokerrankings.com/login.asp")
                .method(Connection.Method.POST)
                .data("login_user", login)
                .data("login_password", password)
                .data("login_remember", "on")
                .data("x", "21")
                .data("y", "11")
                .header("Host", "www.officialpokerrankings.com")
                .header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
                .header("Cache-Control", "no-cache")
                .header("Referer", "http://www.officialpokerrankings.com/login.html")
                .userAgent(userAgent)
                .execute();

        Element unSuccess = response.parse().getElementsByAttributeValue("href", "/login.html").first();
        Element success = response.parse().getElementsByAttributeValue("href", "/logout.asp").first();


        if (unSuccess != null) {
            return new AuthResponse(new HashMap<String, String>(), false);
        }

        if (success != null) {
            return new AuthResponse(response.cookies(), true);
        }
        return new AuthResponse(new HashMap<String, String>(), false);

    }

    public void saveCookie(Map<String, String> cookie, Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        StringBuilder sb = new StringBuilder();
        for (String key : cookie.keySet()) {
            sb.append(key).append(KEY_VALUE_DELIMITER).append(cookie.get(key)).append(KEY_VALUE_DELIMITER);
        }
        editor.putString(COOCKIE, sb.toString()).commit();
    }

    public Map<String, String> loadCookie(Context context) {
        Map<String, String> map = new HashMap<>();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String string = preferences.getString(COOCKIE, "");
        if (!string.isEmpty()) {
            String[] split = string.split(KEY_VALUE_DELIMITER);
            if (split.length > 1) {
                for (int i = 0; i < split.length - 1; i = i + 2) {
                    map.put(split[i], split[i + 1]);
                }
            }
        }
        return map;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public class AuthResponse {
        private Map<String, String> coockie;
        private boolean auth;

        public AuthResponse(Map<String, String> coockie, boolean auth) {
            this.coockie = coockie;
            this.auth = auth;
        }

        public Map<String, String> getCoockie() {
            return coockie;
        }

        public void setCoockie(Map<String, String> coockie) {
            this.coockie = coockie;
        }

        public boolean isAuth() {
            return auth;
        }


    }
}
