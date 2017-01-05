package at.ac.tuwien.ims.svn.phantasmophobia;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.Log;

import java.util.Random;

/**
 * Created by Emil Etlinger on 04.01.2016.
 * Shows the Sprites on the foreground and makes the bird fly past over and over again.
 * @author Emil Etlinger
 */
public class Sprite
{
    static final private int spriteImgCounter =4;
    private int x = 0;
    private int y = 0;
    private int xSpeed;
    private int width;
    private int height;
    private Bitmap bmp;
    private GameSurfaceView gameView;
    private int screenWidth;
    private int screenHeight;
    private int currentFrame=0;


    public Sprite(Bitmap bmp, GameSurfaceView gameView, int screenHeight, int screenWidth)
    {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth()/ spriteImgCounter;
        this.height = bmp.getHeight();
        Random rnd = new Random();
        y = rnd.nextInt(screenHeight);
        y=y/2;
        xSpeed = rnd.nextInt(16 - 8 + 1) +  8;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    /**
     * moves the position of the Sprite across the board.
     */
    private void move()
    {
        x = x + xSpeed;
        currentFrame = ++currentFrame % spriteImgCounter;
        Random rnd = new Random();
        int rand = rnd.nextInt(screenHeight/2);
        if(x>screenWidth||x<0)
        {
            x=0;
            y = rnd.nextInt(screenHeight);
        }
        /*
        else if (y<rand)
        {
            y+=screenHeight;
            x=-width;
        }
        */
    }

    /**
     * Draws the Sprite on the screen.
     * @param canvas
     */

    public void draw(Canvas canvas)
    {
        int sourceBMP = currentFrame*width;
        Rect source = new Rect(sourceBMP,0,sourceBMP+width,height);
      //  Rect source = new Rect(sourceBMP,height/4,sourceBMP+width,height/2);
        Rect destination = new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bmp,source,destination,null);
    }
    public void update()
    {
        this.move();
    }
}