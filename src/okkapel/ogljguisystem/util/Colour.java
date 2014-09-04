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

import org.lwjgl.opengl.GL11;

public class Colour
{

    private static final char hexNums[] = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static final Colour WHITE = new Colour(1f, 1f, 1f, 1f);
    public static final Colour GREEN = new Colour(0f, 1f, 0f, 1f);

    private float red, green, blue, alpha;

    public Colour(float r, float g, float b, float a)
    {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    public Colour(float r, float g, float b)
    {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = 1f;
    }

    /**
     * Values between 0 and 255 (inclusive)
     */
    public Colour(int r, int g, int b)
    {
        this.red = (float) r / 255f;
        this.green = (float) g / 255f;
        this.blue = (float) b / 255f;
        this.alpha = 1f;
    }

    /**
     * Values between 0 and 255 (inclusive)
     */
    public Colour(int r, int g, int b, int a)
    {
        this.red = (float) r / 255f;
        this.green = (float) g / 255f;
        this.blue = (float) b / 255f;
        this.alpha = (float) a / 255f;
    }

    public static Colour fromHexString(String s)
    {
        if (s.length() == 7) // in format of #ff00ff
        {
            char buf[] = new char[2];
            Colour ret = new Colour(1f, 1f, 1f, 1f);
            for (int i = 0; i < 3; i++)
            {
                buf[0] = s.charAt(1 + i * 2);
                buf[1] = s.charAt(1 + i * 2 + 1);
                switch (i)
                {
                    case 0: // red
                        ret.setRed((float) (hexDigitToDec(buf[0]) * 16 + hexDigitToDec(buf[1])) / 255f);
                        break;
                    case 1: // green
                        ret.setGreen((float) (hexDigitToDec(buf[0]) * 16 + hexDigitToDec(buf[1])) / 255f);
                        break;
                    case 2: // blue
                        ret.setBlue((float) (hexDigitToDec(buf[0]) * 16 + hexDigitToDec(buf[1])) / 255f);
                        break;
                }
            }
            return ret;
        }
        return null;
    }

    public void setGLColor()
    {
        GL11.glColor4f(red, green, blue, alpha);
    }

    public int getRedI()
    {
        return (int) Math.floor(this.red * 255);
    }

    public int getGreenI()
    {
        return (int) Math.floor(this.green * 255);
    }

    public int getBlueI()
    {
        return (int) Math.floor(this.blue * 255);
    }

    public int getAlphaI()
    {
        return (int) Math.floor(this.alpha * 255);
    }

    public int getRed4BitI()
    {
        return (int) Math.floor(this.red * 15);
    }

    public int getGreen4BitI()
    {
        return (int) Math.floor(this.green * 15);
    }

    public int getBlue4BitI()
    {
        return (int) Math.floor(this.blue * 15);
    }

    public int getAlpha4BitI()
    {
        return (int) Math.floor(this.alpha * 15);
    }

    public float getRed()
    {
        return this.red;
    }

    public float getGreen()
    {
        return this.green;
    }

    public float getBlue()
    {
        return this.blue;
    }

    public float getAlpha()
    {
        return this.alpha;
    }

    public void setRed(float v)
    {
        this.red = v;
    }

    public void setGreen(float v)
    {
        this.green = v;
    }

    public void setBlue(float v)
    {
        this.blue = v;
    }

    public void setAlpha(float v)
    {
        this.alpha = v;
    }

    /**
     * Note: this only uses the rightmost 24 bits.
     */
    public int getPackedRGB()
    {
        return (getRedI() << 16) | (getGreenI() << 8) | (getBlueI());
    }

    public int getPackedRGBA()
    {
        return (getRedI() << 24) | (getGreenI() << 16) | (getBlueI() << 8) | (getAlphaI());
    }

    public int getPackedARGB()
    {
        return (getAlphaI() << 24) | (getRedI() << 16) | (getGreenI() << 8) | (getBlueI());
    }

    /**
     * Note: this only uses the rightmost 12 bits.
     */
    public short getPackedShortRGB()
    {
        return (short) ((getRed4BitI() << 8) | (getGreen4BitI() << 4) | getBlue4BitI());
    }

    public short getPackedShortRGBA()
    {
        return (short) ((getRed4BitI() << 12) | (getGreen4BitI() << 8) | (getBlue4BitI() << 4) | getAlpha4BitI());
    }

    public short getPackedShortARGB()
    {
        return (short) (getAlpha4BitI() << 12 | (getRed4BitI() << 8) | (getGreen4BitI() << 4) | getBlue4BitI());
    }

    public String getAsHexStringRGB()
    {
        return "#" + getHexString(this.getRedI()) + getHexString(this.getGreenI()) + getHexString(this.getBlueI());
    }

    public String getAsHexStringRGBA()
    {
        return "#" + getHexString(this.getRedI()) + getHexString(this.getGreenI()) + getHexString(this.getBlueI())
                + getHexString(this.getAlphaI());
    }

    public String getAsHexStringARGB()
    {
        return "#" + getHexString(this.getAlphaI()) + getHexString(this.getRedI()) + getHexString(this.getGreenI())
                + getHexString(this.getBlueI());
    }

    public Colour lighten(float amount)
    {
        if (amount <= 0f)
        {
            return this;
        }
        this.red = MathHelper.clampFloat(this.red + amount, 0f, 1f);
        this.green = MathHelper.clampFloat(this.green + amount, 0f, 1f);
        this.blue = MathHelper.clampFloat(this.blue + amount, 0f, 1f);
        return this;
    }

    public Colour darken(float amount)
    {
        if (amount <= 0f)
        {
            return this;
        }
        this.red = MathHelper.clampFloat(this.red - amount, 0f, 1f);
        this.green = MathHelper.clampFloat(this.green - amount, 0f, 1f);
        this.blue = MathHelper.clampFloat(this.blue - amount, 0f, 1f);
        return this;
    }

    /**
     * Untested!
     */
    public Colour tintTowards(Colour target, boolean tintAlpha, float amount)
    {
        this.red -= MathHelper.clampFloat((this.red - target.getRed()) * amount, 0f, 1f);
        this.green -= MathHelper.clampFloat((this.green - target.getGreen()) * amount, 0f, 1f);
        this.blue -= MathHelper.clampFloat((this.blue - target.getBlue()) * amount, 0f, 1f);
        if (tintAlpha)
        {
            this.alpha -= MathHelper.clampFloat((this.alpha - target.getAlpha()) * amount, 0f, 1f);
        }
        return this;
    }

    public Colour getCopy()
    {
        return new Colour(this.red, this.green, this.blue, this.alpha);
    }

    private static int hexDigitToDec(char c)
    {
        for (int i = 0; i < hexNums.length; i++)
        {
            if (hexNums[i] == c)
            {
                return i;
            }
        }
        System.err.println("invalid character \"" + c + "\"");
        return -1;
    }

    private static String getHexString(int dec)
    {
        if (dec < 0 || dec > 255)
        {
            return "00";
        }
        else
        {
            return Character.toString(hexNums[(int) Math.floor(dec / 16)]) + Character.toString(hexNums[dec % 16]);
        }
    }

    public static int packARGB(int alpha, int red, int green, int blue)
    {
        return ((alpha << 24) | (red << 16) | (green << 8) | (blue));
    }

    public static int packRGBA(int red, int green, int blue, int alpha)
    {
        return ((red << 24) | (green << 16) | (blue << 8) | (alpha));
    }

    /**
     * Note: this only uses the rightmost 24 Bits
     */
    public static int packRGB(int red, int green, int blue)
    {
        return ((red << 16) | (green << 8) | (blue));
    }

    public static short pack16BitARGB(int alpha, int red, int green, int blue)
    {
        return (short) ((alpha << 12) | (red << 8) | (green << 4) | (blue));
    }

    /**
     * Note: this only uses the rightmost 12 Bits
     */
    public static short pack16BitRGB(int alpha, int red, int green, int blue)
    {
        return (short) ((red << 8) | (green << 4) | (blue));
    }

    public static short pack16BitRGBA(int alpha, int red, int green, int blue)
    {
        return (short) ((red << 12) | (green << 8) | (blue << 4) | (alpha));
    }

    /**
     * Not tested!
     */
    public static Colour unpackRGB(int packed)
    {
        return new Colour((packed >> 16) & 255, (packed >> 8) & 255, (packed) & 255);
    }

    /**
     * Not tested!
     */
    public static Colour unpackRGBA(int packed)
    {
        return new Colour((packed >> 24) & 255, (packed >> 16) & 255, (packed >> 8) & 255, (packed) & 255);
    }

    /**
     * Not tested!
     */
    public static Colour unpackARGB(int packed)
    {
        return new Colour((packed >> 16) & 255, (packed >> 8) & 255, (packed) & 255, (packed >> 24) & 255);
    }

    public static Colour unpack16BitRGB(short packed)
    {
        return createColourFrom16BitInts((packed >> 8) & 0xF, (packed >> 4) & 0xF, (packed) & 0xF);
    }

    public static Colour unpack16BitRGBA(short packed)
    {
        return createColourFrom16BitInts((packed >> 12) & 0xF, (packed >> 8) & 0xF, (packed >> 4) & 0xF,
                                         (packed) & 0xF);
    }

    public static Colour unpack16BitARGB(short packed)
    {
        return createColourFrom16BitInts((packed >> 8) & 0xF, (packed >> 4) & 0xF, (packed) & 0xF,
                                         (packed >> 12) & 0xF);
    }

    public static Colour createColourFrom16BitInts(int r, int g, int b, int a)
    {
        return new Colour(r / 15f, g / 15f, b / 15f, a / 15f);
    }

    public static Colour createColourFrom16BitInts(int r, int g, int b)
    {
        return new Colour(r / 15f, g / 15f, b / 15f, 1f);
    }

    /**
     * Resets the GL color to white
     */
    public static void resetGLColor()
    {
        GL11.glColor4f(1f, 1f, 1f, 1f);
    }

}
