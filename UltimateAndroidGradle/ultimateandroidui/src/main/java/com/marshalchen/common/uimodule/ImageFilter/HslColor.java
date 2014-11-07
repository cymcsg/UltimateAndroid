/* 
 * HaoRan ImageFilter Classes v0.4
 * Copyright (C) 2012 Zhenjun Dai
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation.
 */


package com.marshalchen.common.uimodule.ImageFilter;

public class HslColor
{

    public float h;
    public float l;
    public float s;

    public HslColor(float h, float s, float l)
    {
        this.h = h;
        this.s = s;
        this.l = l;
    }

    // HSL to RGB helper routine
    private static double Hue_2_RGB(double v1, double v2, double vH)
    {
        if (vH < 0)
            vH += 1;
        if (vH > 1)
            vH -= 1;
        if ((6 * vH) < 1)
            return (v1 + (v2 - v1) * 6 * vH);
        if ((2 * vH) < 1)
            return v2;
        if ((3 * vH) < 2)
            return (v1 + (v2 - v1) * ((2.0 / 3) - vH) * 6);
        return v1;
    }

    public static int HslToRgb(HslColor hsl)
    {
        int r, g, b;
        if (hsl.h == 0)
        {
            // gray values
            r = g = b = (byte)(hsl.l * 255);
        }
        else
        {
            double v1, v2;
            double hue = (double)hsl.h / 360;

            v2 = (hsl.l < 0.5) ?
                (hsl.l * (1 + hsl.s)) :
                ((hsl.l + hsl.s) - (hsl.l * hsl.s));
            v1 = 2 * hsl.l - v2;

            r = (byte)(255 * Hue_2_RGB(v1, v2, hue + (1.0 / 3)));
            g = (byte)(255 * Hue_2_RGB(v1, v2, hue));
            b = (byte)(255 * Hue_2_RGB(v1, v2, hue - (1.0 / 3)));
        }
        return (255 << 24) + (r << 16) + (g << 8) + b;
    }

    private static float HueToRgb(float t1, float t2, float h)
    {
        if (h < 0f)
        {
            h++;
        }
        if (h > 1f)
        {
            h--;
        }
        if ((6f * h) < 1f)
        {
            return (t1 + (((t2 - t1) * 6f) * h));
        }
        if ((2f * h) < 1f)
        {
            return t2;
        }
        if ((3f * h) < 2f)
        {
            return (t1 + (((t2 - t1) * (0.6666667f - h)) * 6f));
        }
        return t1;
    }

    public HslColor Interpolate(HslColor c2, float amount)
    {
        return new HslColor(this.h + ((c2.h - this.h) * amount), this.s + ((c2.s - this.s) * amount), this.l + ((c2.l - this.l) * amount));
    }

    public static void RgbToHsl(int color, HslColor hsl)
    {
        RgbToHsl(0xff & (color >> 0x10), 0xff & (color >> 8), 0xff & color, hsl);
    }

    public static void RgbToHsl(int rr, int gg, int bb, HslColor hsl)
    {
        double r = (rr / 255.0);
        double g = (gg / 255.0);
        double b = (bb / 255.0);

        double min = Math.min(Math.min(r, g), b);
        double max = Math.max(Math.max(r, g), b);
        double delta = max - min;

        // get luminance value
        hsl.l = (float)(max + min) / 2;

        if (delta == 0)
        {
            // gray color
            hsl.h = 0;
            hsl.s = 0.0f;
        }
        else
        {
            // get saturation value
            hsl.s = (float)((hsl.l < 0.5) ? (delta / (max + min)) : (delta / (2 - max - min)));

            // get hue value
            double del_r = (((max - r) / 6) + (delta / 2)) / delta;
            double del_g = (((max - g) / 6) + (delta / 2)) / delta;
            double del_b = (((max - b) / 6) + (delta / 2)) / delta;
            double hue;

            if (r == max)
                hue = del_b - del_g;
            else if (g == max)
                hue = (1.0 / 3) + del_r - del_b;
            else
                hue = (2.0 / 3) + del_g - del_r;

            // correct hue if needed
            if (hue < 0)
                hue += 1;
            if (hue > 1)
                hue -= 1;

            hsl.h = (int)(hue * 360);
        }
    }
}
