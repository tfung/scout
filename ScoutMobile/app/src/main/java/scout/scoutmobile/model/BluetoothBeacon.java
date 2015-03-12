package scout.scoutmobile.model;

import com.estimote.sdk.Beacon;
import com.parse.ParseObject;

import scout.scoutmobile.constants.Consts;
import scout.scoutmobile.utils.Logger;

/**
 * Represents a physical beacon of a business owner.
 */
public class BluetoothBeacon {

    private String mMacAddress;

    // a string that identifies beacons in the region (beacons can share a UUID)
    // UUIDs could always be changed by the user
    private String mUUID;

    // A number that can be used to identify specific beacons
    // (several beacons in a region can have the same major value)
    private Integer mMajor;

    // A number that can be used to identify a specific beacon
    // (the minor value should be unique to one beacon within a region)
    private Integer mMinor;

    //Number representing the x position of the beacon
    private int mCoordX;

    //Number representing the y position of the beacon
    private int mCoordY;

    private Logger mLogger;

    public BluetoothBeacon(String macAddress, String uuid, Integer major, Integer minor) {
        this.mMacAddress = macAddress;
        this.mUUID = uuid;
        this.mMajor = major;
        this.mMinor = minor;
        this.mCoordX = -1;
        this.mCoordY = -1;
    }

    public BluetoothBeacon(Beacon beacon) {
        this(beacon.getMacAddress(), beacon.getProximityUUID(), beacon.getMajor(),
                beacon.getMinor());
    }

    public BluetoothBeacon(ParseObject beacon) {
        this(beacon.getString(Consts.COL_BEACON_MACADDRESS), beacon.getString(Consts.COL_BEACON_UUID),
                beacon.getInt(Consts.COL_BEACON_MAJOR), beacon.getInt(Consts.COL_BEACON_MINOR));
        mLogger = new Logger("BluetoothBeacon");
        this.mCoordX = beacon.getInt(Consts.COL_BEACON_COORDX);
        this.mCoordY = beacon.getInt(Consts.COL_BEACON_COORDY);

        mLogger.log("Position X: " + mCoordX);
        mLogger.log("Position Y: " + mCoordY);
    }

    public String getMacAddress() {
        return mMacAddress;
    }

    public String getUUID() {
        return mUUID;
    }

    public Integer getMajor() {
        return mMajor;
    }

    public Integer getMinor() {
        return mMinor;
    }

    public Integer getCoordY() {
        return mCoordY;
    }

    public Integer getCoordX() {
        return mCoordX;
    }
}
