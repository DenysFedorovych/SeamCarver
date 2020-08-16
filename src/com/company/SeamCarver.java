package com.company;

import edu.princeton.cs.algs4.Picture;

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

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam()

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam)
    {
        this.checkValidate(seam);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam)
    {
        this.checkValidate(seam);
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
    
    //  unit testing (optional)
    public static void main(String[] args){}
}
