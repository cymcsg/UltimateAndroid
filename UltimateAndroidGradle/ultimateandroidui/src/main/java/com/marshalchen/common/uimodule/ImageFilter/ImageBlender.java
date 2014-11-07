/* 
 * HaoRan ImageFilter Classes v0.1
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

/**
 * �������AndroidImage����
 * @author daizhj
 *
 */
public class ImageBlender {
	public float Mixture = 0.9f;//0.5f
	public static class BlendMode
	{
		public static int Normal = 0;
	    public static int Additive = 1;
	    public static int Subractive = 2;
	    public static int Multiply = 3;
	    public static int Overlay = 4;
	    public static int ColorDodge = 5;
	    public static int ColorBurn = 6;
	    public static int Lighten = 7;
	    public static int Darken = 8;
	    public static int Reflect = 9;
	    public static int Glow = 10;
	    public static int LinearLight = 11;
	    public static int Frame = 12;/*photo frame*/
	    
	}
 
	public int Mode = BlendMode.Multiply;
	
 	public Image Blend(Image source1, Image source2)
	{
	    int num = (int) (Mixture * 255f);
	    int num2 = 255 - num;
	    for (int x = 0; x < source1.getWidth(); x++)
        {
	        for (int y = 0; y < source1.getHeight(); y++)
	        {  
	        	int r = 0, g = 0, b = 0;
		        int r1 = source1.getRComponent(x, y);
		        int g1 = source1.getGComponent(x, y);
		        int b1 = source1.getBComponent(x, y);
		        int r2 = source2.getRComponent(x, y);
		        int g2 = source2.getGComponent(x, y);
		        int b2 = source2.getBComponent(x, y);
		        switch (Mode)
		        {
		            case 1:{//Additive
		                r = r1 + r2;
		                g = g1 + g2;
		                b = b1 + b2;
		                r = (r > 255) ? 255 : r;
		                g = (g > 255) ? 255 : g;
		                b = (b > 255) ? 255 : b;
		                break;
		            }
		            case 2:{//Subractive
		            	r = r1 + r2;
			            g = g1 + g2;
			            b = b1 + b2;
		                r = (r < 255) ? 0 : (r - 255);
		                g = (g < 255) ? 0 : (g - 255);
		                b = (b < 255) ? 0 : (b - 255);
		                break;
		            }
		            case 3:{//Multiply
		                r = (r1 * r2) / 255;
		                g = (g1 * g2) / 255;
		                b = (b1 * b2) / 255;
		                break;
		            }
		            case 4:{//Overlay
		                r = (r2 < 0x80) ? (((2 * r1) * r2) / 255) : (255 - (((2 * (255 - r1)) * (255 - r2)) / 255));
		                g = (g2 < 0x80) ? (((2 * g1) * g2) / 255) : (255 - (((2 * (255 - g1)) * (255 - g2)) / 255));
		                b = (b2 < 0x80) ? (((2 * b1) * b2) / 255) : (255 - (((2 * (255 - b1)) * (255 - b2)) / 255));
		                break;
		            }
		            case 5:{//ColorDodge
		                r = (r1 << 8) / (255 - ((r2 != 255) ? r2 : 0xfe));
		                g = (g1 << 8) / (255 - ((g2 != 255) ? g2 : 0xfe));
		                b = (b1 << 8) / (255 - ((b2 != 255) ? b2 : 0xfe));
		                r = (r2 == 255) ? r2 : ((r < 255) ? r : 255);
		                g = (g2 == 255) ? g2 : ((g < 255) ? g : 255);
		                b = (b2 == 255) ? b2 : ((b < 255) ? b : 255);
		                break;
		            }
		            case 6:{//ColorBurn
		                r = 255 - (((255 - r1) << 8) / ((r2 != 0) ? r2 : 1));
		                g = 255 - (((255 - g1) << 8) / ((g2 != 0) ? g2 : 1));
		                b = 255 - (((255 - b1) << 8) / ((b2 != 0) ? b2 : 1));
		                r = (r2 == 0) ? r2 : ((r > 0) ? r : 0);
		                g = (g2 == 0) ? g2 : ((g > 0) ? g : 0);
		                b = (b2 == 0) ? b2 : ((b > 0) ? b : 0);
		                break;
		            }
		            case 7://Lighten
		                r = (r2 > r1) ? r2 : r1;
		                g = (g2 > g1) ? g2 : g1;
		                b = (b2 > b1) ? b2 : b1;
		                break;
		            case 8:{//Darken
		                r = (r2 > r1) ? r1 : r2;
		                g = (g2 > g1) ? g1 : g2;
		                b = (b2 > b1) ? b1 : b2;
		                break;
		            }
		            case 9:{//Reflect
		                r = (r1 * r1) / (255 - ((r2 != 255) ? r2 : 0xfe));
		                g = (g1 * g1) / (255 - ((g2 != 255) ? g2 : 0xfe));
		                b = (b1 * b1) / (255 - ((b2 != 255) ? b2 : 0xfe));
		                r = (r2 == 255) ? r2 : ((r < 255) ? r : 255);
		                g = (g2 == 255) ? g2 : ((g < 255) ? g : 255);
		                b = (b2 == 255) ? b2 : ((b < 255) ? b : 255);
		                break;
		            }
		            case 10:{//Glow
		                r = (r2 * r2) / (255 - ((r1 != 255) ? r1 : 0xfe));
		                g = (g2 * g2) / (255 - ((g1 != 255) ? g1 : 0xfe));
		                b = (b2 * b2) / (255 - ((b1 != 255) ? b1 : 0xfe));
		                r = (r1 == 255) ? r1 : ((r < 255) ? r : 255);
		                g = (g1 == 255) ? g1 : ((g < 255) ? g : 255);
		                b = (b1 == 255) ? b1 : ((b < 255) ? b : 255);
		                break;
		            }
		            case 11:{//LinearLight
		                /*if (r2 >= 128){
		                	r = r1 + (2 * (r2 - 128));
		     		        r = (r > 255) ? 255 : r;
		                    break;
		                }
		                r = r1 + (2 * r2);
		                r = (r < 255) ? 0 : (r - 255);
		                */
		                if (r2 < 128)
				        {
				            r = r1 + (2 * r2);
				            r = (r < 255) ? 0 : (r - 255);
				        }
				        else
				        {
				            r = r1 + (2 * (r2 - 128));
				            r = (r > 255) ? 255 : r;
				        }
		                if (g2 < 128)
				        {
				            g = g1 + (2 * g2);
				            g = (g < 255) ? 0 : (g - 255);
				        }
				        else
				        {
				            g = g1 + (2 * (g2 - 128));
				            g = (g > 255) ? 255 : g;
				        }
				        if (b2 < 128)
				        {
				            b = b1 + (2 * b2);
				            b = (b < 255) ? 0 : (b - 255);
				        }
				        else
				        {
				            b = b1 + (2 * (b2 - 128));
				            b = (b > 255) ? 255 : b;
				        }
				        break;
		            }
		            case 12:{ //Frame
		            	if((r2 ==0 && g2 == 0 && r2 ==0)){//̽��߿���ɫ(r2 > 230 && g2 > 230 && b2 > 230) || 
		            		r = r1;
				            g = g1;
				            b = b1;    
				    	}
				    	else{
				    		r = r2;
			                g = g2;
			                b = b2;
				    	}
		            	break;
		            }
				    default:
				        r =  r2;
			            g =  g2;
			            b =  b2;   	
			            break;
		        }
		    
		        r = (r1 * num2) + (r * num);
		        g = (g1 * num2) + (g * num);
		        b = (b1 * num2) + (b * num); 
		        source1.setPixelColor(x, y, r >> 8, g >> 8, b >> 8);
		        //input1.setPixelColour(x, y, r, g, b);
		    }
        }
	    return source1;
	}
}
