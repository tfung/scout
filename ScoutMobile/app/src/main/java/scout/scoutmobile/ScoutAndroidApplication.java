package scout.scoutmobile;

import android.app.Application;

import com.parse.Parse;


public class ScoutAndroidApplication extends Application {

    private static final String APP_ID = "DiEded8eK6muPcH8cdHGj8iqYUny65Mva143CpQ3";
    private static final String CLIENT_KEY = "V7CmhQ7lDeGZQ4AWaZFnyOklBONwc2EaAVZAgyA6";

    public void onCreate() {
        Parse.initialize(this, APP_ID, CLIENT_KEY);
    }
}