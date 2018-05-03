package com.example.v4n0v.instatest.event_bus;

import com.squareup.otto.Bus;

/**
 * Created by v4n0v on 03.05.18.
 */

public class TabSelectBus {

    public static Bus getBus() {
        return bus;
    }

    private static Bus bus = new Bus();
}
