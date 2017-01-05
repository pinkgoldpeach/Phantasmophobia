package at.ac.tuwien.ims.svn.phantasmophobia;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Emil Etlinger on 01/12/2015.
 * Handles Svens location, the appropriate png to show, and his movement.
 * @author Emil Etlinger
 */
public class Sven
{

    private Bitmap sven_idle;
    private Bitmap sven_walk1;
    private Bitmap sven_walk2;
    private Bitmap sven_jump;
    private Bitmap sven;
    private int dx;
    private int dy;
    private int screenHeight;
    private int screenWidth;
    private Rect bgSource;
    private Rect bgDestination;
    private boolean playing;
    private boolean jump;
    private int bottom;
    private boolean first;
    private int right;
    private int left;
    private int top;
    private boolean walk1;
    private boolean walk2;
    private boolean falling;

    public Sven(Bitmap sven_idle, Bitmap sven_walk1, Bitmap sven_walk2, Bitmap sven_jump, int screenHeight, int screenWidth)
    {
        this.sven_idle = sven_idle;
        this.sven_walk1 = sven_walk1;
        this.sven_walk2 = sven_walk2;
        this.sven_jump = sven_jump;
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
        this.playing = false;
        this.jump = false;
        this.dy = 1;

        this.sven = sven_idle;
        this.walk1 = false;
        this.walk2 = false;
        this.falling = false;

        this.left = screenWidth/2-sven.getWidth()/2;
        this.right = screenWidth/2+sven.getWidth()/2;
        this.top = screenHeight - sven.getHeight()-1;
        this.bottom = screenHeight-18;

        this.bgDestination = new Rect(left, top, right, bottom);
        this.bgSource = new Rect(0, 0, this.sven.getWidth(), this.sven.getHeight());
    }

    /**
     * Updates Svens location, so he can be drawn properly.
     */
    public void update(){
        if(isPlaying()) {
            top += dy;
            left += dx;
            bottom += dy;
            right += dx;

            if(this.top < 0){
                this.top = 0;
                this.bottom = sven.getHeight();
            }
        }
    }

    /**
     * draw Sven at the right position.
     * x = 0; always
     */

    public void draw(Canvas canvas){

        bgDestination.set(left, top, right, bottom);
        canvas.drawBitmap(sven, bgSource, bgDestination, new Paint());

    }

    public boolean isPlaying(){
        return playing;
    }

    /**
     * sven = playing ( after first jump); score starts counting
     * @author Carla Jancik
     */

    public void setPlaying(boolean playing){
        this.playing = playing;
        this.first = true;
    }

    /**
     * called, when a touch-event happened; sven is either jumping or falling;
     * different jumping and falling velocities for different levels
     * @author Carla Jancik
     */

    public void jump(boolean jump, int level){
        this.jump = jump;
        this.falling = true;

        if(jump) {
            this.sven = sven_jump;
            dy = -screenHeight / 20;
        }
        else
         {
             if(level == 1) {
                 dy = 5;
             }else{
                 dy = 10;
             }
        }
    }

    public int getLeft() {
        return left;
    }

    public int getY() {
        return top;
    }

    public int getBottom(){
        return bottom;
    }

    public int getRight(){
        return right;
    }

    public int getWidth()
    {
        return sven.getWidth();
    }

    public void setDY(int dy)
    {
        this.dy = dy;
    }

    public void setDX(int dx){
        this.dx = dx;
    }

    public Rect getRect(){
        return bgDestination;
    }

    /**
     * changes pictures of sven, when moving left and right;
     * sets new position of sven after left-right-movement
     * @author Carla Jancik
     */

    public void movement(int dx){
        this.left+=dx;
        this.right+=dx;

        if(this.left < 0){
            this.left = -30;
            this.right = sven.getWidth()-30;
        }else if(this.right > screenWidth){
            this.right = screenWidth;
            this.left = screenWidth-sven.getWidth();
        }
        if(walk1 && !falling){
            Log.d("walk2", "walk2");
            sven = sven_walk2;
            walk1 = false;
            walk2 = true;
        }else if(walk2 && !falling){
            Log.d("idle", "idle");
            sven = sven_idle;
            walk1 = false;
            walk2 = false;
        }else if(!falling){
            Log.d("walk1", String.valueOf(falling));
            sven = sven_walk1;
            walk1 = true;
            walk2 = false;
        }

    }

    /**
     * sets new position after jump
     * @author Carla Jancik
     */
    public void setX(int movement){
        this.top += movement;
        this.bottom += movement;
    }

    /**
     * relocate Sven
     * @author Emil Etlinger
     */
    public void setPosition(int x, int y)
    {
        bottom = y;
        top = y - sven.getHeight();
        left = x;
        right = x + sven.getWidth();
    }

    /**
     * necessary for the correct image, when sven is moving/falling
     * @author Emil Etlinger
     */

    public void setFalling(boolean b)
    {
        falling=b;
        sven=sven_jump;
    }

    /**
     * necessary for the correct image, when sven is moving/falling
     * @author Emil Etlinger
     */
    public void setFallingFalse()
    {
        falling = false;
        sven=sven_idle;
    }
}