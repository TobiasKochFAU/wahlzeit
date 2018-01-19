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
import com.googlecode.objectify.annotation.Serialize;
import org.wahlzeit.utils.asserts.ObjectAssert;

import java.util.*;

@Entity
public class PowerType {

    // Power types, e.g. nuclear, wind, etc...
    @Id
    private final String typeName;

    // Used value object pattern.
    private static final Hashtable<Integer, PowerType> instances = new Hashtable<>();

    @Serialize
    private PowerType superType = null;

    @Serialize
    private Set<PowerType> subTypes = new HashSet<>();

    private PowerType(String typeName) {
        ObjectAssert.assertNotNull(typeName, "Type name is null!");
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public Power createPower() {
        return new Power(this);
    }

    public static PowerType getPowerType(String typeName) {
        ObjectAssert.assertNotNull(typeName, "Type name is null!");
        return PowerType.doGetPowerType(typeName);
    }

    private static PowerType doGetPowerType(String typeName) {
        PowerType pt = new PowerType(typeName);
        int hashCode = pt.hashCode();
        if (!PowerType.instances.containsKey(hashCode)) {
            PowerType.instances.put(hashCode, pt);
        }
        return PowerType.instances.get(hashCode);
    }

    public PowerType getSuperType() {
        return this.superType;
    }

    public void setSuperType(PowerType superType) {
        this.superType = superType;
    }

    public void addSubType(PowerType powerType) {
        ObjectAssert.assertNotNull(powerType, "Power type is null!");
        powerType.setSuperType(this);
        this.subTypes.add(powerType);
    }

    public boolean isSubtype() {
        return this.superType != null;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean isEqual = false;
        if (obj instanceof PowerType) {
            PowerType other = (PowerType) obj;
            if (this.superType != null) {
                isEqual = this.typeName.equals(other.typeName)
                        && this.superType.equals(other.superType)
                        && this.subTypes.equals(other.subTypes);
            }
            else if (other.superType == null) {
                isEqual = this.typeName.equals(other.typeName)
                        && this.subTypes.equals(other.subTypes);
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.typeName, this.superType, this.subTypes);
    }
}
