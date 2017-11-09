/*
 * Copyright (c) 2006-2009 by Tobias Koch
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

@Subclass
public class PowerPhoto extends Photo {
    /**
     * attributes to describe a power plant
     * this could be an extra class with specific subclasses
     * maybe I will do this later
     */
    protected String name = ""; // no setter, name should not changed
    protected String type = ""; // no setter, type should not change
    protected int yearStartUp = 0;  // no setter, yearStartUp should not change
    protected double capacitySinceStart = 0.0d; // in GWh, capacity gained till year yearCapacity
    protected int yearCapacity = 0; // the year until the capacity was measured

    public PowerPhoto(String name, String type, int yearStartUp, double capacitySinceStart, int yearCapacity) {
        super();
        this.initialize(name, type, yearStartUp, capacitySinceStart, yearCapacity);
    }

    public PowerPhoto(PhotoId myId, String name, String type, int yearStartUp, double capacitySinceStart,
                      int yearCapacity) {
        super(myId);
        this.initialize(name, type, yearStartUp, capacitySinceStart, yearCapacity);
    }

    /**
     * Sets the attributes.
     * @param name  power plant name
     * @param type  power plant type
     * @param yearStartUp   power plant start-up
     * @param capacitySinceStart    capacity measured since start until yearCapacity
     * @param yearCapacity  year of measured capacity
     */
    protected void initialize(String name, String type, int yearStartUp, double capacitySinceStart, int yearCapacity) {
        this.name = name;
        this.type = type;
        this.yearStartUp = yearStartUp;
        this.setCapacitySinceStart(capacitySinceStart, yearCapacity);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getCapacitySinceStart() {
        return capacitySinceStart;
    }

    /**
     * private as the capacity should not be set without the related year.
     * @param capacitySinceStart    capacity measured since start until yearCapacity
     */
    private void setCapacitySinceStart(double capacitySinceStart) {
        this.capacitySinceStart = capacitySinceStart;
    }

    public int getYearStartUp() {
        return yearStartUp;
    }

    public int getYearCapacity() {
        return yearCapacity;
    }

    /**
     * private as the year should not be set without the related capacity.
     * Throws exception if yearCapacity is smaller than this.yearStartUp.
     * @param yearCapacity      year of measured capacity
     */
    private void setYearCapacity(int yearCapacity) {
        if (yearCapacity < this.yearStartUp) {
            throw new IllegalArgumentException("year in which the capacity was measured " +
                    "can not be smaller than the plant start up year");
        }
        this.yearCapacity = yearCapacity;
    }

    /**
     * Capacity has to be set together with related year.
     * @param capacitySinceStart    capacity measured since start until yearCapacity
     * @param yearCapacity      year of measured capacity
     */
    public void setCapacitySinceStart(double capacitySinceStart, int yearCapacity) {
        this.setYearCapacity(yearCapacity);
        this.setCapacitySinceStart(capacitySinceStart);
    }
}
