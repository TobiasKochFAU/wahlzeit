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
import org.wahlzeit.utils.asserts.DoubleAssert;
import org.wahlzeit.utils.asserts.IntegerAssert;
import org.wahlzeit.utils.asserts.ObjectAssert;

@Subclass
public class PowerPhoto extends Photo {

    /**
     * attributes to describe a power plant
     * this could be an extra class with specific subclasses
     * maybe I will do this later
     */
    protected String name = "";
    protected String type = "";
    protected int yearStartUp = 0;
    protected double capacitySinceStart = 0.0d; // in GWh, capacity gained till year yearCapacity
    protected int yearCapacity = 0; // the year until the capacity was measured

    public PowerPhoto() {
        super();
    }

    public PowerPhoto(PhotoId myId) {
        super(myId);
    }

    public String getName() {
        return name;
    }

    /**
     * Set name, if name is null this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param name  name to set
     */
    public void setName(String name) {
        try {
            ObjectAssert.assertNotNull(name, "Name is null!");
            this.doSetName(name);
        }
        catch (IllegalArgumentException exc) {
            // do nothing, keep old value
            // could write to log or something
        }
    }

    private void doSetName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    /**
     * Set type, if type is null this method will
     * do nothing as this does not lead to an enormous invalid program state.
     * @param type  type to set
     */
    public void setType(String type) {
        try {
            ObjectAssert.assertNotNull(type, "Type is null!");
            this.doSetType(type);
        }
        catch (IllegalArgumentException exc) {
            // do nothing, keep old value
            // could write to log or something
        }
    }

    private void doSetType(String type) {
        this.type = type;
    }

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
            // could write to log or something
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
            // could write to log or something
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
}
