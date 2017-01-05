package at.ac.tuwien.ims.svn.phantasmophobia;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Carla Jancik on 18.11.2015.
 * Background will be drawn, and runs in a loop from top to bottom, simulating the movement upwards.
 * @author Carla Jancik
 */
public class Background {

    private Bitmap background;
    private Bitmap background2;
    private final int x = 0;
    private int y1, y2;
    private float dy;
    private int screenHeight;
    private int screenWidth;
    private Rect bgSource;
    private Rect bgSource2;
    private Rect bgDestination1;
    private Rect bgDestination2;
    private Paint paint;
    private static int counter = 0;


    public Background(Bitmap background, int screenHeight, int screenWidth){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.background = background;
        this.background2 = background;
        this.bgSource = new Rect(0, 0, background.getWidth(), background.getHeight());
        this.bgSource2 = new Rect(0, 0, background.getWidth(), background.getHeight());
        this.paint = new Paint();
        this.y1 = 0;
        this.y2 = - screenHeight;
        this.bgDestination1 = new Rect(0, y1, screenWidth, screenHeight+y1);
        this.bgDestination2 = new Rect(0, y2, screenWidth, screenHeight+y2);
        this.dy = 1f;

    }
    public Background(Bitmap background,Bitmap background2, int screenHeight, int screenWidth){
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.background = background;
        this.background2 = background2;
        this.bgSource = new Rect(0, 0, background.getWidth(), background.getHeight());
        this.bgSource2 =  new Rect(0, 0, background2.getWidth(), background2.getHeight());
        this.bgDestination1 =new Rect(0, 0, screenWidth, screenHeight);;
        this.bgDestination2 = new Rect(0, 0, screenWidth, screenHeight);
        this.paint = new Paint();
        this.y1 = 0;
        this.y2 = -screenHeight;
        this.dy = 1f;

    }

    /**
     * This method updates the position of the Background, so it can be drawn properly later.
     *
     * needs 2 different Y's for correct drawing
     * start one image at 0,0 and the other one at 0,-screenheight - to move the images into the screen
     */

    public void update(){
        y1 = Math.round(y1+dy);
        y2 = Math.round(y2+dy);
        if(y1 >= screenHeight){
            y1 = -screenHeight;
            counter++;
        }
        if(y2 >= screenHeight){
            y2 = -screenHeight;
            counter++;
        }
    }

/*

    Old try for the update method.

    public void update(float delta){
        System.out.println("delta: "+delta);
        System.out.println("dy*delta: " + Math.round(dy*delta));
        y1 += (Math.round(dy*delta));
        y2 += (Math.round(dy*delta));
        System.out.println("y1: "+ y1);
        System.out.println("y2: " + y2);
        if(y1 >= screenHeight){
            y1 = -screenHeight;
            counter++;
        }
        if(y2 >= screenHeight){
            y2 = -screenHeight;
            counter++;
        }
    }
*/

    /**
     * IMPORTANT!!
     * You can't set the y's in the constructor, because screenheight isn't there.
     * y2 would always be 0!
     */
    public void setYs(){
        this.y1 = 0;
        this.y2 = -screenHeight;
    }

    /**
     * draw the two images at the right positions
     * x = 0; always
     */

    public void draw(Canvas canvas){
        bgDestination1.set(x, y1, screenWidth, screenHeight + y1);
        canvas.drawBitmap(background, bgSource, bgDestination1, paint);
        bgDestination2.set(x, y2, screenWidth, screenHeight + y2);
        canvas.drawBitmap(background2, bgSource2, bgDestination2, paint);
    }



    public void setScreenHeight(int screenHeight)
    {
        this.screenHeight=screenHeight;
    }
    public void setScreenWidth(int screenWidth)
    {
        this.screenWidth=screenWidth;
    }

    public void setVector(float dy){
        this.dy = dy;
    }

    public int getCounter(){
        return counter;
    }
}
