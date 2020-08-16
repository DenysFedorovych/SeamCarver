package com.company;

import edu.princeton.cs.algs4.Picture;
import java.lang.Math;
public class SeamCarver {
    private int width;
    private int height;
    private Picture picture;
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture)
    {
        this.checkValidate(picture);
        this.picture = new Picture(picture);
        this.height = picture.height();
        this.width = picture.width();
    }

    // current picture
    public Picture picture()

    // width of current picture
    public int width(){return this.width;}

    // height of current picture
    public int height(){return this.height;}

    // energy of pixel at column x and row y
    public double energy(int x, int y)
    {
        this.checkValidate(x,y);
        if(x==0||x==width-1||y==0||y==height-1){return 1000;}
        else{
            int a=gradX(x,y);
            int b=gradY(x,y);
            return Math.sqrt(a^2+b^2);
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        if(this.height==1||this.width!=seam.length){throw new IllegalArgumentException();}
        this.checkValidate(seam);
        for(int i=0;i<seam.length;i++){
            if(i!=seam.length-1){
                int k=seam[i]-seam[i+1];
                if(k>1||k<(-1)){throw new IllegalArgumentException();}
            }
        }
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
        if(this.width==1||this.height!=seam.length){throw new IllegalArgumentException();}
        this.checkValidate(seam);
        for(int i=0;i<seam.length;i++){
            if(i!=seam.length-1){
                int k=seam[i]-seam[i+1];
                if(k>1||k<(-1)){throw new IllegalArgumentException();}
            }
        }
    }

    private void checkValidate(Object a) {
        if (a == null) {
            throw new IllegalArgumentException();
        }
    }
    private void checkValidate(int a, int b)
    {
        if(a<0 || b<0 || a>this.width-1 || b>this.height-1)
        {
            throw new IllegalArgumentException();
        }
    }

    private int gradX(int x, int y)
    {
        int a = picture.get(x-1,y).getRed()-picture.get(x+1,y).getRed();
        int b = picture.get(x-1,y).getBlue()-picture.get(x+1,y).getBlue();
        int c = picture.get(x-1,y).getGreen()-picture.get(x+1,y).getGreen();
        return a^2+b^2+c^2;
    }
    private int gradY(int x, int y)
    {
        int a = picture.get(x,y-1).getRed()-picture.get(x,y+1).getRed();
        int b = picture.get(x,y-1).getBlue()-picture.get(x,y+1).getBlue();
        int c = picture.get(x,y-1).getGreen()-picture.get(x,y+1).getGreen();
        return a^2+b^2+c^2;
    }
    //  unit testing (optional)
    public static void main(String[] args){}
}
