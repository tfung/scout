package scout.scoutmobile.utils;

import android.content.Context;
import android.content.Intent;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import scout.scoutmobile.activities.LoginActivity;
import scout.scoutmobile.constants.Consts;
import scout.scoutmobile.model.CustomerSingleton;
import scout.scoutmobile.services.BluetoothBeaconService;

public class GeneralUtils {

    private static Logger m_Logger = new Logger("GeneralUtils");

    public static void logUserOut(Context context) {
        final ParseUser user = CustomerSingleton.getInstance().getCurUser();
        user.remove(Consts.COL_USER_LOGGEDIN);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    user.saveEventually();
                }
                ParseUser.logOut();
            }
        });

        context.stopService(new Intent(context, BluetoothBeaconService.class));
        startMainActivity(context, LoginActivity.class);
    }

    /**
     * NOTE: This should be called whenever an action occurs, should normally be called on application
     * resume.
     *
     * Checks whether or not current user logged in is the one registered in Parse database, this
     * handles unique user login by checking User tables loggedin field for bt mac address matches
     * the current users bt mac address. If the current parseUser does not have the same mac
     * address as the one registered in Parse database, we log the current user out
     */
    public static void verifyUserLoggedIn(Context context) {
        ParseUser curUser = CustomerSingleton.getInstance().getCurUser();
        boolean userLoggedIn = false;
        try {
            curUser.fetchIfNeeded();
            String parseLoggedInUser = curUser.getString(Consts.COL_USER_LOGGEDIN);
            userLoggedIn = ((parseLoggedInUser != null) &&
                    (parseLoggedInUser != Consts.USER_LOGGED) &&
                    !parseLoggedInUser.isEmpty());
            m_Logger.log("user logged in " + userLoggedIn);
        } catch (ParseException e) {
            m_Logger.logError(e);
        }

        if (userLoggedIn) {
            logUserOut(context);
        }
    }

    /**
     * NOTE: finish() should be called in the activity that calls this to prevent malformed behaviour
     * on back button presses
     * starts an unique activity with given context and mainclass, this pretty much starts the applcation
     * over again.
     * @param context
     * @param mainClass
     */
    public static void startMainActivity(Context context, Class<?> mainClass) {
        startMainActivity(context, mainClass, "");
    }

    /**
     * Overloaded class to handle sending error messages into the login screen. e.g. when user is
     * kicked out of a session
     * @param context
     * @param mainClass
     */
    public static void startMainActivity(Context context, Class<?> mainClass, String err) {
        m_Logger.log("Starting main activity");
        Intent mainActivity = new Intent(context, mainClass);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK|
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (err != "") {
            mainActivity.putExtra(Consts.LOGIN_ERROR_EXTRA, err);
        }
        context.startActivity(mainActivity);
    }

    private GeneralUtils() {
    }

}
