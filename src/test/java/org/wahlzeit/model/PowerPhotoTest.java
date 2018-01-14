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

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

import static org.junit.Assert.*;

public class PowerPhotoTest {

    @ClassRule
    public static TestRule chain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    private PowerPhoto p1 = null;

    @Before
    public void arrange() {
        PowerManager pm = PowerManager.getInstace();

        Power beer1 = pm.createPower("beer");
        beer1.setName("plant1");
        beer1.setYearStartUp(2012);
        beer1.setCapacitySinceStart(5.2d, 2017);

        p1 = new PowerPhoto();
        p1.setPower(beer1);
    }

    /**
     *
     */
    @Test
    public void testAttributes() {
        PowerManager pm = PowerManager.getInstace();

        Power beer2 = pm.createPower("beer");
        beer2.setName("plant2");
        beer2.setYearStartUp(2014);
        beer2.setCapacitySinceStart(2.5d, 2017);

        PhotoId id = new PhotoId(1337);
        PowerPhoto p2 = new PowerPhoto(id);
        p2.setPower(beer2);

        Power wine = pm.createPower("wine");
        wine.setName("plant3");
        wine.setYearStartUp(2014);
        wine.setCapacitySinceStart(42.42d, 2042);

        PowerPhoto p3 = new PowerPhoto(id);
        p3.setPower(wine);

        assertNotEquals(p1.getId(), p2.getId());
        assertEquals(p1.getPower().getName(), "plant1");
        assertNotEquals(p1.getPower().getName(), p2.getPower().getName());
        assertEquals(p1.getPower().getPowerType(), p2.getPower().getPowerType());
        assertEquals(p1.getPower().getYearStartUp(), 2012);
        assertEquals(p1.getPower().getCapacitySinceStart(), 5.2d, 1e-7);
        assertEquals(p1.getPower().getYearCapacity(), 2017);

        assertEquals(p2.getId(), p3.getId());
        assertNotEquals(p2.getPower().getName(), p3.getPower().getName());
        assertNotEquals(p2.getPower().getPowerType(), p3.getPower().getPowerType());
        assertEquals(p2.getPower().getYearStartUp(), p3.getPower().getYearStartUp());
        assertNotEquals(p2.getPower().getCapacitySinceStart(), p3.getPower().getCapacitySinceStart(), 1e-7);
        assertNotEquals(p2.getPower().getYearCapacity(), p3.getPower().getYearCapacity());
    }

    /**
     *  Test exception handling.
     */
    @Test
    public void testExceptionHandling() {
        assertEquals(p1.getPower().getCapacitySinceStart(), 5.2d, 0.0d);
        assertEquals(p1.getPower().getYearCapacity(), 2017);

        p1.getPower().setCapacitySinceStart(7.3, 2018);
        p1.getPower().setCapacitySinceStart(2.4, 2011);
        assertEquals(p1.getPower().getCapacitySinceStart(), 7.3, 0.0d);
        assertEquals(p1.getPower().getYearCapacity(), 2018);

        p1.getPower().setName(null);
        assertEquals(p1.getPower().getName(), "plant1");
    }
}
