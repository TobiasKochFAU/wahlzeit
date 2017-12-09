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

package org.wahlzeit.utils.asserts;

/**
 * Class providing assertion methods for Integers.
 */
public class IntegerAssert {

    /**
     * Check for a positive value.
     * @param val   value to check
     * @param message   message to show
     * @throws IllegalArgumentException     if val <= 0
     */
    public static void assertPositive(int val, String message) throws IllegalArgumentException {
        if (val <= 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Check for a positive or zero value.
     * @param val   value to check
     * @param message   message to show
     * @throws IllegalArgumentException     if val < 0
     */
    public static void assertPositiveZero(int val, String message) throws IllegalArgumentException {
        if (val < 0) {
            throw new IllegalArgumentException(message);
        }
    }
}
