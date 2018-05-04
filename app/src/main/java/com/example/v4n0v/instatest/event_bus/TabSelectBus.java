package com.example.v4n0v.instatest.event_bus;

import com.squareup.otto.Bus;

/**
 * Шина, для уведомления вкладок о переключении, для обновления информации внутри
 */

public class TabSelectBus {

    public static Bus getBus() {
        return bus;
    }

    private static Bus bus = new Bus();
}
