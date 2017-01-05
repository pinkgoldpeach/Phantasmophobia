package at.ac.tuwien.ims.svn.phantasmophobia;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Carla Jancik on 23.12.2015.
 * Draws the bricks on the background.
 * @author Carla Jancik
 */
public class Brick extends GameObject {

    private Bitmap brick;
    private int speed;
    private int bottom;
    private int right;
    private int top;
    private Rect bgSource;
    private Rect bgDestination;
    private int screenHeight;
    private int brickLeft;

    public Brick(Bitmap brick, int top,int screenWidth, int screenHeight)
    {
        this.brick = brick;
        this.top = top;
        this.bottom = top + brick.getHeight();

        this.playing = false;
        this.speed = 1;
        this.screenHeight = screenHeight;

        Random random = new Random();

        this.brickLeft = random.nextInt(screenWidth - brick.getWidth());
        this.right = brickLeft + brick.getWidth();

        this.bgDestination = new Rect(brickLeft, top, right, bottom);
        this.bgSource = new Rect(0, 0, brick.getWidth(), brick.getHeight());

    }

    public Brick(Bitmap brick, int left, int top, int screenWidth, int screenHeight)
    {
        this.brick = brick;
        this.top = top;
        this.brickLeft = left;
        this.bottom = top + brick.getHeight();
        this.right = screenWidth;

        this.playing = false;
        this.speed = 1;
        this.screenHeight = screenHeight;
        this.bgDestination = new Rect(brickLeft, top, right, bottom);
        this.bgSource = new Rect(0, 0, brick.getWidth(), brick.getHeight());

    }

    /**
     * Moves the Bricks with the background, so they stay on the same spot.
     */
    public void update(){

        top += speed;
        bottom += speed;
    }

    public int getX()
    {
        return brickLeft;
    }
    public int getWidth()
    {
        return brick.getWidth();
    }

    /**
     * draw the two images at the right positions
     * x = 0; always
     */

    public void draw(Canvas canvas){

        bgDestination.set(brickLeft, top, right, bottom);
        canvas.drawBitmap(brick, bgSource, bgDestination, new Paint());

    }



    public int getTop(){
        return top;
    }

    public int getLeft(){
        return brickLeft;
    }

    public int getRight()
    {
        return right;
    }
    public Rect getRect(){
        return bgDestination;
    }



}
