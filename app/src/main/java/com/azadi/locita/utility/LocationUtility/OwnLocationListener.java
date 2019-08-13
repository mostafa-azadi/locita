package com.azadi.locita.utility.LocationUtility;

/**
 * Created by Mostafa on 09/13/18.
 */

/**
 * Callback that can be implemented in order to listen for events
 */

public interface OwnLocationListener {
    void locationOn();

    void onPositionChanged();

    void locationCancelled();
}
