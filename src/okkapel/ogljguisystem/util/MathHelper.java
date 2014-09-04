/*
 * Copyright (C) 2014 Celestibytes
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package okkapel.ogljguisystem.util;

import java.util.Random;

/**
 * The reason to have our own math helper is that we don't need to change the method names after updates and our helper
 * has better documentation
 */
public class MathHelper
{
    private static Random random = new Random();

    /**
     * @param value the number to clamp
     * @param max maximum possible value
     * @param min minimum possible value
     */
    public static int clampInt(int value, int min, int max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    /**
     * @param value the number to clamp
     * @param max maximum possible value
     * @param min minimum possible value
     */
    public static double clampDouble(double value, double min, double max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    /**
     * @param value the number to clamp
     * @param max maximum possible value
     * @param min minimum possible value
     */
    public static float clampFloat(float value, float min, float max)
    {
        return (value < min) ? min : (value > max) ? max : value;
    }

    /**
     * @param value the number to clamp
     * @param max maximum possible value
     *
     * @return an int clamped between 0 and max
     */
    public static int clampZero_int(int value, int max)
    {
        return clampInt(value, 0, max);
    }

    /**
     * @param value the number to clamp
     * @param max maximum possible value
     *
     * @return a float clamped between 0 and max
     */
    public static float clampZero_float(float value, float max)
    {
        return clampFloat(value, 0f, max);
    }

    /**
     * @param value the number to clamp
     * @param max maximum possible value
     *
     * @return a double clamped between 0 and max
     */
    public static double clampZero_double(double value, double max)
    {
        return clampDouble(value, 0d, max);
    }

    public static int nextRandom(int oldIndex, int max)
    {
        return nextRandom(oldIndex, random, max);
    }

    public static int nextRandom(int oldIndex, Random random, int max)
    {
        int i = random.nextInt(max);
        return i != oldIndex ? i : nextRandom(oldIndex, random, max);
    }

    public static int nextRandom(int oldIndex)
    {
        return nextRandom(oldIndex, random);
    }

    public static int nextRandom(int oldIndex, Random random)
    {
        int i = random.nextInt();
        return i != oldIndex ? i : nextRandom(oldIndex, random);
    }
}
