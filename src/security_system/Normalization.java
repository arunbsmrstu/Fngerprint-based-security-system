/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security_system;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Arun Biswas
 */
public class Normalization {
    
    
   

    /*
	public int[][] getRGB(){
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		int c = 0;
		int [][] rgb = new int[width][height];
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height ; y++){
				c = sourceImage.getRGB(x,y);
				rgb[x][y] = (c&0x00ff0000)>>16;
				rgb[x][y] = (c&0x0000ff00)>>8;
				rgb[x][y] = c&0x000000ff;
			}
		}
		return rgb;
	}*/
	/*
	public float[] RGBToGreyOld(){
		int width = sourceImage.getWidth();
		int height = sourceImage.getHeight();
		int size = width * height;
		int c = 0, counter = 0, r, g, b;
		greyScale = new float[size];
		for(int i = 0; i<width; i++){
			for(int j = 0; j<height ; j++){
				c = sourceImage.getRGB(i,j);
				r = (c&0x00ff0000)>>16;
			g = (c&0x0000ff00)>>8;
			b = c&0x000000ff;
			greyScale[counter] = (float) (0.3 * r + 0.59 * g + 0.11 * b);
			counter++;
			}
		}
		return greyScale;
	}*/
    
	public int[][] RGBToGrey(BufferedImage source){
		int greyScale[][] = new int[source.getWidth()][source.getHeight()];
		for(int x=0; x<source.getWidth(); x++){
			for(int y=0; y<source.getHeight(); y++){
				int c = source.getRGB(x, y);
				float r = (c&0x00ff0000)>>16;
				float g = (c&0x0000ff00)>>8;
				float b = c&0x000000ff;
				greyScale[x][y] = (int)(0.3*r + 0.59*g + 0.11*b);
			}
		}
		return greyScale;
	}
	

   /*public float findMinimum(){
	   find = this.RGBToGrey();
	   float minValue= find[0];
		    for(int i=1;i<find.length;i++){  
		        if(find[i] < minValue){  
		            minValue = find[i];  
		        }  
		    }
		return minValue;  
   }
   
   public float findMaximum(){
	   find = this.RGBToGrey();
	   float maxValue= find[0];
		    for(int i=1;i<find.length;i++){  
		        if(find[i] < maxValue){  
		            maxValue = find[i];  
		        }  
		    } 
		return maxValue;
   }
   */
	public int findMinimum(int[][] input, int width, int height){
		int min = input[0][0];
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				int n = input[x][y];
				if(n<min){
					min = n;
				}
			}
		}
		return min;
	}
	public int findMaximum(int[][] input, int width, int height){
		int max = input[0][0];
		for(int x=0; x<width; x++){
			for(int y=0; y<height; y++){
				int n = input[x][y];
				if(n>max){
					max = n;
				}
			}
		}
		return max;
	}/*
   public float[] NormalizeMagic(){
	   /*for each pixel loop
	    * value a= 1.current grayScale - minium (Intensity-minimum)
	    * value b=255-0 (new max-new min)
	    * value c=max-min
	    * d=0
	    * total equation should look like a(b/c)+d
	    
	   int width = sourceImage.getWidth();
	   int height = sourceImage.getHeight();
	   magicGreyScale=new float[greyScale.length];
	   int count=0;
	   float a=0, b=255, d=0;
	   float c=findMaximum()-findMinimum();	   
	   for(int i = 0; i<width; i++){
		   for(int j = 0; j<height ; j++){
			   a=greyScale[count];
			   float e=b/c;
			   magicGreyScale[count]=e;
			   count++;
		   }
	   }
	return magicGreyScale;
   }*/
   public int[][] normalizeMagic(int[][] input, int width, int height){
	   int[][] output = new int[width][height];
	   int a=0, b=255;
	   int c = findMaximum(input,width,height)-findMinimum(input,width,height);
	   for(int x=0; x<width; x++){
		   for(int y=0; y<height; y++){
			   a = (input[x][y])-findMinimum(input,width,height);
			   int e =b/c;
			   output[x][y] = e*a;
		   }
	   }
	   return output;
   }
   
   /*
    *now, draw a new image with the magically normalized GreyScale! 
    */
/*
   public BufferedImage makeNewBufferedImage(float[]newGrayScale) {
       int[] newBufferedImageData = new int[rows * cols];
       int index;
       for (int row = 0; row < rows; row++) {
           for (int col = 0; col < cols; col++) {
               index = (row * cols) + col;
               
           }
       }

       BufferedImage newImage = new BufferedImage(cols, rows, BufferedImage.TYPE_BYTE_GRAY);
       for (int row = 0; row < rows; row++) {
           for (int col = 0; col < cols; col++) {
               index = (row * cols) + col;
               newImage.setRGB(col, row, newBufferedImageData[index]);
           }
       }

       return newImage;
   }*/
   public BufferedImage makeNewBufferedImage(int[][] gs, int width, int height){
	   BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	   int[] iArray = {0,0,0,255};
	   WritableRaster r = image.getRaster();
	   for(int x=0; x<width; x++){
		   for(int y=0; y<height; y++){
			   int v = gs[x][y];
			   iArray[0] = v;
			   iArray[1] = v;
			   iArray[2] = v;
			   r.setPixel(x, y, iArray);
		   }
	   }
	   image.setData(r);
	   return image;
   }

    
}
