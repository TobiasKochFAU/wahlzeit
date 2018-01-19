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

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import org.wahlzeit.utils.asserts.DoubleAssert;
import org.wahlzeit.utils.asserts.IntegerAssert;
import org.wahlzeit.utils.asserts.ObjectAssert;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Entity
public class Power {

    @Id
    private final String name;

    private PowerType powerType;
    private int yearStartUp = 0;
    private double capacitySinceStart = 0.0d; // in GWh, capacity gained till year yearCapacity
    private int yearCapacity = 0; // the year until the capacity was measured

    private static final Logger log = Logger.getLogger(PowerPhoto.class.getName());

    public Power(String name, PowerType powerType) {
        this.name = name;
        this.powerType = powerType;
    }

    public PowerType getPowerType() {
        return powerType;
    }

    /**
     * Set type, if type is null this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param powerType  power Type to set
     */
    public void setPowerType(PowerType powerType) {
        try {
            ObjectAssert.assertNotNull(powerType, "Power type is null!");
            this.doSetPowerType(powerType);
        }
        catch (IllegalArgumentException exc) {
            // do nothing, keep old value
            log.log(Level.WARNING, exc.getMessage());
        }
    }

    public String getName() {
        return this.name;
    }

    private void doSetPowerType(PowerType powerType) {
        this.powerType = powerType;
    }

    /**
     * Set name, if name is null this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param name  name to set
     */
//    public void setName(String name) {
//        try {
//            ObjectAssert.assertNotNull(name, "Name is null!");
//            this.doSetName(name);
//        }
//        catch (IllegalArgumentException exc) {
//            // do nothing, keep old value
//            log.log(Level.WARNING, exc.getMessage());
//        }
//    }
//
//    private void doSetName(String name) {
//        this.name = name;
//    }

    public int getYearStartUp() {
        return yearStartUp;
    }

    /**
     * Set year start up, if yearStartUp is negative this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param yearStartUp  yearStartUp to set
     */
    public void setYearStartUp(int yearStartUp) {
        try {
            IntegerAssert.assertPositiveZero(yearStartUp, "Year start up is negative!");
            this.doSetYearStartUp(yearStartUp);
        }
        catch (IllegalArgumentException exc) {
            // do nothing, keep old value
            log.log(Level.WARNING, exc.getMessage());
        }
    }

    private void doSetYearStartUp(int yearStartUp) {
        this.yearStartUp = yearStartUp;
    }

    public double getCapacitySinceStart() {
        return capacitySinceStart;
    }

    /**
     * Capacity has to be set together with related year.
     * If capacitySinceStart or yearCapacity are negative this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param capacitySinceStart  capacity measured since start until yearCapacity
     * @param yearCapacity  year of measured capacity
     */
    public void setCapacitySinceStart(double capacitySinceStart, int yearCapacity) {
        try {
            DoubleAssert.assertPositiveZero(capacitySinceStart, "Capacity since start is negative!");
            IntegerAssert.assertPositiveZero(yearCapacity, "Year capacity is negative!");
            this.assertValidYearCapacity(yearCapacity);
            this.doSetCapacitySinceStart(capacitySinceStart, yearCapacity);
        }
        catch (IllegalArgumentException exc) {
            // do nothing, keep old values
            log.log(Level.WARNING, exc.getMessage());
        }
    }

    private void doSetCapacitySinceStart(double capacitySinceStart, int yearCapacity) {
        this.doSetYearCapacity(yearCapacity);
        this.doSetCapacitySinceStart(capacitySinceStart);
    }

    /**
     * private as the capacity should not be set without the related year.
     * @param capacitySinceStart    capacity measured since start until yearCapacity
     */
    private void doSetCapacitySinceStart(double capacitySinceStart) {
        this.capacitySinceStart = capacitySinceStart;
    }

    public int getYearCapacity() {
        return yearCapacity;
    }

    private void doSetYearCapacity(int yearCapacity) {
        this.yearCapacity = yearCapacity;
    }

    private void assertValidYearCapacity(int yearCapacity) {
        if (yearCapacity < this.yearStartUp) {
            throw new IllegalArgumentException("year in which the capacity was measured " +
                    "can not be smaller than the plant start up year");
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof Power) {
            Power other = (Power) obj;
            isEqual = this.yearStartUp == other.yearStartUp
                    && this.yearCapacity == other.yearCapacity
                    && this.capacitySinceStart == other.capacitySinceStart
                    && this.name.equals(other.name)
                    && this.powerType.equals(other.powerType);
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.yearStartUp, this.yearCapacity,
                this.capacitySinceStart, this.name, this.powerType);
    }
}
