/*
 * Copyright (c) 2017 by Tobias Koch
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import org.wahlzeit.utils.asserts.ObjectAssert;

import java.util.HashMap;

public class PowerManager {

    // Singleton
    private static final PowerManager instance = new PowerManager();

    private static final HashMap<String, Power> powerInstances = new HashMap<>();

    private PowerManager() {
        // do nothing
    }

    // Singleton access
    public static PowerManager getInstace() {
        return PowerManager.instance;
    }

    public Power createPower(String powerName, String powerStrType) {
        ObjectAssert.assertNotNull(powerName, "Power name is null!");
        ObjectAssert.assertNotNull(powerStrType, "Power type is null!");

        PowerType powerType = PowerType.getPowerType(powerStrType);

        synchronized (PowerManager.powerInstances) {
            if (!PowerManager.powerInstances.containsKey(powerName)) {
                Power power = powerType.createPower(powerName);
                PowerManager.powerInstances.put(powerName, power);
            }
        }

        return PowerManager.powerInstances.get(powerName);
    }
}
