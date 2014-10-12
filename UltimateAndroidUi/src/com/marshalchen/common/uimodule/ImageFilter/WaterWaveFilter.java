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
 * ˮ����Ч��
 * @author daizhj
 *
 */
public class WaterWaveFilter  extends RadialDistortionFilter{
	int width;
	int height;
	short[] buf2;
	short[] buf1;
	int[] temp;
	int[] source;
	//@Override
    public Image process(Image imageIn) {
    	width = imageIn.getWidth();
    	height = imageIn.getHeight();
    	buf2 = new short[width*height];
		buf1 = new short[width*height];
	    source = imageIn.colorArray;
	    temp = new int[source.length];
	    DropStone(width/2, height/2, Math.max(width, height)/4, Math.max(width, height));
		for(int i = 0 ; i < 170; i++){
			RippleSpread();
			render();
		}	
		imageIn.colorArray = temp;
		return imageIn;
    }
    
    void DropStone(int x /*x���*/, int y /*y���*/, int stonesize /*��Դ�뾶*/, int stoneweight /*��Դ����*/)
    {
    	if ((x + stonesize) > width || (y + stonesize) > height || (x - stonesize) < 0 || (y - stonesize) < 0){
           return;
    	}
        for (int posx = x - stonesize; posx < x + stonesize; posx++){
            for (int posy = y - stonesize; posy < y + stonesize; posy++){
                if ((posx - x) * (posx - x) + (posy - y) * (posy - y) <= stonesize * stonesize){
                    buf1[width * posy + posx] = (short)-stoneweight;
                }
            }
        }
    }


    void RippleSpread() {
    	 for (int i=width; i<width*height-width; i++){
		      //������ɢ
		      buf2[i] =(short)(((buf1[i-1]+buf1[i+1]+buf1[i-width]+buf1[i+width])>>1)- buf2[i]);
		      //����˥��
		      buf2[i] -= buf2[i]>>5;
	     }
	     //����������ݻ�����
	     short []tmp =buf1;
	     buf1 = buf2;
	     buf2 = tmp;
    }
   
   
    /* ��Ⱦ��ˮ��Ч�� */
    void render() {
    	 int xoff, yoff;
	     int k = width;
	     for (int i=1; i<height-1; i++) {
		      for (int j=0; j<width; j++) {
			       //����ƫ����
			       xoff = buf1[k-1]-buf1[k+1];
			       yoff = buf1[k-width]-buf1[k+width];
			       //�ж�����Ƿ��ڴ��ڷ�Χ��
			       if ((i+yoff )< 0 || (i+yoff )>= height || (j+xoff )< 0 || (j+xoff )>= width) {
			    	   k++; 
			    	   continue;
			       }
			       //�����ƫ�����غ�ԭʼ���ص��ڴ��ַƫ����		       
			       // image.setPixelColour(j, i, clone.getPixelColour(j+xoff, i+yoff));	
			       int pos1, pos2;
	    		   pos1=width*(i+yoff)+ (j+xoff);
	    		   pos2=width*i+ j;    			
	    		   temp[pos2++]=source[pos1++];
			       k++;
		      }
	     }
     }
}
