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

import com.googlecode.objectify.annotation.Subclass;
import org.wahlzeit.utils.DesignPattern;
import org.wahlzeit.utils.asserts.DoubleAssert;
import org.wahlzeit.utils.asserts.IntegerAssert;
import org.wahlzeit.utils.asserts.ObjectAssert;

import java.util.logging.Level;
import java.util.logging.Logger;

@DesignPattern(
        name = "Abstract Factory",
        participants = {"ConcreteProduct"}
)
@Subclass
public class PowerPhoto extends Photo {


    private static final Logger log = Logger.getLogger(PowerPhoto.class.getName());

    private Power power = null;

    public PowerPhoto() {
        super();
    }

    public PowerPhoto(PhotoId myId) {
        super(myId);
    }

    public Power getPower() {
        return this.power;
    }

    /**
     * Set power, if power is null this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param power  power to set
     */
    public void setPower(Power power) {
        try {
            ObjectAssert.assertNotNull(power, "Power is null!");
            this.doSetPower(power);
        }
        catch (IllegalArgumentException exc) {
            // do nothing, keep old value
            log.log(Level.WARNING, exc.getMessage());
        }
    }

    private void doSetPower(Power power) {
        this.power = power;
    }
}
