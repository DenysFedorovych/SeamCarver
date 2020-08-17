package com.company;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Point2D;
import java.lang.Math;
import java.util.ArrayList;

public class SeamCarver {
    private class ShortestPath{
        private Point2D[][] edgeTo;
        private double[][] distTo;
        private SeamCarver SC;
        private boolean vertical;
        public int[] path;
        public ShortestPath(SeamCarver SC, boolean vertical){
            this.SC = SC;
            this.vertical = vertical;
            edgeTo = new Point2D[SC.width+1][SC.height+1];
            distTo = new double[SC.width+1][SC.height+1];
            for(int i=0;i<SC.width;i++)
            {
                for(int j=0;j<SC.height;j++)
                {
                    distTo[i][j]=Double.POSITIVE_INFINITY;
                }
            }
            distTo[0][SC.height]=0;
            distTo[SC.width][0]=0;
            IndexMinPQ<Double> pq = new IndexMinPQ<>(SC.height*SC.width+2);
            if(vertical)
            {
                Point2D a = new Point2D(0,SC.height);
                this.relax(a);
                for(int i=0;i<SC.width;i++)
                {
                    for(int j=0;j<SC.height-1;j++)
                    {this.relax(new Point2D(i,j));}
                }
                int mdis=0;
                for (int i = 0; i < SC.width; i++) {
                    if (distTo[i][SC.height - 1] < distTo[mdis][SC.height - 1]) {
                        mdis=i;
                    }
                }
                path=new int[SC.height];
                path[SC.height-1]=mdis;
                for(int i=SC.height-2;i>=0;i--)
                {
                    path[i]=(int) edgeTo[path[i+1]][i].x();
                }
            }
            else{
                Point2D a = new Point2D(SC.width,0);
                this.relax(a);
                for(int i=0;i<SC.width-1;i++)
                {
                    for(int j=0;j<SC.height;j++)
                    {this.relax(new Point2D(i,j));}
                }
                int mdis=0;
                for (int i = 0; i < SC.height; i++) {
                    if (distTo[SC.height - 1][i] < distTo[SC.height - 1][mdis]) {
                        mdis=i;
                    }
                }
                path=new int[SC.width];
                path[SC.width-1]=mdis;
                for(int i=SC.width-2;i>=0;i--)
                {
                    path[i]=(int) edgeTo[i][path[i+1]].x();
                }
            }

        }
        public void relax(Point2D point)
        {
            for(Point2D e : this.adj(point)) {
                int x = (int) e.x();
                int y = (int) e.y();
                int a = (int) point.x();
                int b = (int) point.y();
                if (distTo[x][y] > distTo[a][b] + SC.energy(x, y)) {
                    distTo[x][y] = distTo[a][b] + SC.energy(x, y);
                    edgeTo[x][y] =  point;
                }
            }
        }
        public Iterable<Point2D> adj(Point2D p)
        {
            ArrayList<Point2D> a = new ArrayList<>();
            double x=p.x();
            double y=p.y();
            if (vertical) {
                if(y==SC.height){
                    for(int i=0;i<SC.width;i++){a.add(new Point2D(i,0));}
                    return a;
                }
                if(y==SC.height-1){a.add(new Point2D(0,SC.height+1));}
                else{
                    if(x==0){a.add(new Point2D(0,y+1)); a.add(new Point2D(1,y+1));}
                    else{
                    if(x==SC.width-1){a.add(new Point2D(SC.width-1,y+1)); a.add(new Point2D(SC.width-2,y+1));}
                    else{
                        a.add(new Point2D(x-1,y+1));
                        a.add(new Point2D(x,y+1));
                        a.add(new Point2D(x+1,y+1));
                    }}
                }
            }
            else{
                if(x==SC.width){
                    for(int i=0;i<SC.width;i++){a.add(new Point2D(0,i));}
                    return a;
                }
                if(x==SC.width-1){a.add(new Point2D(SC.width+1,0));}
                else{
                    if(y==0){a.add(new Point2D(x+1,0)); a.add(new Point2D(x+1,1));}
                    else{
                        if(y==SC.height-1){a.add(new Point2D(x+1,SC.height-1)); a.add(new Point2D(x+1,SC.height-2));}
                        else{
                            a.add(new Point2D(x+1,y-1));
                            a.add(new Point2D(x+1,y));
                            a.add(new Point2D(x+1,y+1));
                        }}
                }
            }
            return a;
            }
        }


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
    {return this.picture;}

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
    {
        ShortestPath a = new ShortestPath(this,false);
        return a.path;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()
    {
        ShortestPath a = new ShortestPath(this,true);
        return a.path;
    }

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
        int[] path = findHorizontalSeam();
        Picture one = new Picture(width,height-1);
        for(int i=0;i<width;i++)
        {
            int k=0;
            for(int j=0; j<height;j++)
            {
                if(j!=path[i]){one.set(i,j-k,picture.get(i,j));}
                else{k++;}
            }
        }
        this.picture = one;
        this.height--;
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
        int[] path = findHorizontalSeam();
        Picture one = new Picture(width-1,height);
        for(int i=0;i<height;i++)
        {
            int k=0;
            for(int j=0; j<width;j++)
            {
                if(j!=path[i]){one.set(j-k,i,picture.get(j,i));}
                else{k++;}
            }
        }
        this.picture = one;
        this.width--;
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
