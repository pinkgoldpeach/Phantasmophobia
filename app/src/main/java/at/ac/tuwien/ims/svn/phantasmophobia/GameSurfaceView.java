package at.ac.tuwien.ims.svn.phantasmophobia;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Emil Etlinger on 24.10.2015.
 * everything happens in the view, a touch takes place and the resulting image gets displayed.
 * load tower and sven images and draw them on canvas
 * starts gameloop
 * @author Emil Etlinger
 */
public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback{


    private GameLoop gameLoop;
    private Sven sven;
    private Background backgroundRun;
    private Background backgroundStart;
    private Background backgroundEnd;
    private float speed;
    private int screenHeight, screenWidth;
    private LinkedList<Brick> bricks;
    private Brick brick;
    private boolean start = false;
    private boolean collision;
    private boolean jump;
    private Bitmap svenIdleBmp;
    private Dialog pauseDialog;
    private static final String TAG = GameSurfaceView.class.getSimpleName();
    private static GameSurfaceView instance;
    private MainActivity mainActivity;
    private long startTime;
    private long pauseStart;
    private long pauseDuration;
    private boolean gameStart;
    private boolean firstRun;
    private int level;
    private Sprite sprite;
    private int jumpCounter;
    private int life;
    private Bitmap lifeBmp;
    private Rect lifeSource;
    private Rect[] lives;

    public GameSurfaceView(Context context, int level){

        super(context);
        getHolder().addCallback(this);
        // Log.d(TAG, "Creating...");
        this.level = level;
        this.collision = true;
        this.jump = false;
        this.mainActivity = (MainActivity) context;
        this.pauseStart = 0;
        this.pauseDuration = 0;
        this.gameStart = false;
        this.firstRun = true;
        this.life = 3;

        if(level == 1){
            this.speed = 1;
        }else{
            this.speed = 4;
        }

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        //public void getSize (Point outSize) - Gets the size of the display, in pixels.
        display.getSize(size);
        this.screenWidth = size.x;
        this.screenHeight = size.y;

        lifeBmp = BitmapFactory.decodeResource(getResources(), R.drawable.life);
        lifeSource = new Rect(0, 0, this.lifeBmp.getWidth(), this.lifeBmp.getHeight());
        lives = new Rect[3];
        int left = 5;
        int top = 5;
        for(int i = 0; i < 3; i++){
            lives[i] = new Rect(left, top, lifeBmp.getWidth()+left, lifeBmp.getHeight()+top);
            left += lifeBmp.getWidth()+ 3;
        }

        Bitmap bitmapSprite = BitmapFactory.decodeResource(getResources(), R.drawable.bird);
        sprite = new Sprite(bitmapSprite,this,screenHeight,screenWidth);

        Bitmap bitmapStart = BitmapFactory.decodeResource(getResources(), R.drawable.tower_start_braid_compressed);
        Bitmap bitmapRun = BitmapFactory.decodeResource(getResources(), R.drawable.tower_middle_braid_compressed);
        Bitmap bitmapEnd = BitmapFactory.decodeResource(getResources(), R.drawable.tower_end_braid_compressed);
        backgroundStart = new Background(bitmapStart, bitmapRun, screenHeight, screenWidth);
        backgroundRun = new Background(bitmapRun, screenHeight, screenWidth);
        backgroundEnd = new Background(bitmapRun, bitmapEnd, screenHeight, screenWidth);

        this.svenIdleBmp = BitmapFactory.decodeResource(getResources(), R.drawable.sven_idle);
        Bitmap svenWalk1Bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sven_walk1);
        Bitmap svenWalk2Bmp = BitmapFactory.decodeResource(getResources(), R.drawable.sven_walk2);
        Bitmap svenJumpBmp = BitmapFactory.decodeResource(getResources(), R.drawable.sven_jump);


        this.sven = new Sven(svenIdleBmp, svenWalk1Bmp, svenWalk2Bmp, svenJumpBmp, screenHeight, screenWidth);

        this.bricks = new LinkedList<>();

        Bitmap brickBmps[] = new Bitmap[3];
        if(level == 1) {
            brickBmps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.brick1);
            brickBmps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.brick2);
            brickBmps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.brick3);
        }else{
            brickBmps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.brick1_level2);
            brickBmps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.brick2_level2);
            brickBmps[2] = BitmapFactory.decodeResource(getResources(), R.drawable.brick3_level2);
        }

        int brickTop = screenHeight;
        Random random = new Random();
        int previous = screenHeight;
        Bitmap brickFirst = BitmapFactory.decodeResource(getResources(), R.drawable.brick_first);
        bricks.add(new Brick(brickFirst, 0, screenHeight-20, screenWidth, screenHeight));

        for(int i = 1; i < 8000; i++) {
            Bitmap brickBmp = brickBmps[i%3];
            brickTop -= (random.nextInt(40)+(screenHeight/8));
            int thisPosition = brickTop;
            if(previous - thisPosition < 20){
                brickTop -= 20;
            }
            if(brickTop < 0 ){
                brickTop = 20;
            }
            brick = new Brick(brickBmp, brickTop, screenWidth, screenHeight);
            bricks.add(brick);
        }


        setFocusable(true);

        getHolder().addCallback(this);
        this.instance = this;
    }

    /**
     *
     * @deprecated
     */

    public static GameSurfaceView getInstance(Context context){
        if(instance == null ){
            return instance = new GameSurfaceView(context, 1);
        }
        return instance;
    }

    /**
     * starts gameloop
     * @author Carla Jancik
     */

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        gameLoop = new GameLoop(holder, this);
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Log.d(TAG, "Destroying...");
        endGame();
    }

    private void endGame() {
        gameLoop.setRunning(false);
        try
        {
            gameLoop.join();
            System.exit(1);
        }
        catch (InterruptedException e)
        {
            Log.e("Error", e.getMessage());
        }
    }

    /**
     * checks where the User clicks, so the right movement will be made.
     *
     * @param event
     * @return irrelevant
     * @author Emil Etlinger, Carla Jancik
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int xTouch=(int) event.getX();
        int yTouch=(int) event.getY();

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!sven.isPlaying())
            {
                sven.setPlaying(true);
              //  sven.movement(xTouch - sven.getRect().centerX());
             //   sven.setDY(yTouch - sven.getRect().centerY());
                update();

            }
            else
            {

                int maxmove = 35;
                if(!jump && yTouch <= screenHeight/2 && checkCollision()) {
                    sven.jump(true, level);
                    jump = true;
                    gameStart = true;
                    jumpCounter=10;
                    if(firstRun) {
                        startTime = System.currentTimeMillis();
                        mainActivity.setStartTime(startTime);
                        firstRun = false;
                    }
                }
                else {
                    int move = sven.getLeft() + sven.getWidth() / 2;
                    move -= xTouch;
                    if (xTouch >= screenWidth / 2)
                        move = maxmove;
                    else if (xTouch < screenWidth / 2)
                        move = -maxmove;
                    sven.movement(move);
                }
                    update();
            }

            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            if(yTouch <= screenHeight/2) {
                sven.jump(false, level);
                jump = false;
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas){

    }

    /**
     * Draws all parts the game needs to draw.
     * @param canvas
     * @author Emil Etlinger, Carla Jancik
     */

    @Override
    public void draw(Canvas canvas)
    {
        /**
         * to scale the canvas, but maybe we don't need this.
         */
        if(bricks.get(0).getTop() >= screenHeight){
            bricks.poll();
        }
        final float scaleFactorX = getWidth()/screenWidth;
        final float scaleFactorY = getHeight()/screenHeight;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            if (backgroundStart.getCounter() == 0) {
                backgroundStart.draw(canvas);
                sven.draw(canvas);
                sprite.draw(canvas);
                for(int i = 0; i < 8; i++){
                    bricks.get(i).draw(canvas);
                }

            }
            /**
            else if(backgroundEnd.getCounter() >= 5 && backgroundEnd.getCounter() < 7)
            {
                backgroundEnd.draw(canvas);
                sven.draw(canvas);
                for(int i = 0; i < 8; i++){
                    bricks.get(i).draw(canvas);
                }
            }
             */
            else if(backgroundRun.getCounter() >0)
            {
                backgroundRun.draw(canvas);
                sven.draw(canvas);
                sprite.draw(canvas);
                for(int i = 0; i < 8; i++){
                    bricks.get(i).draw(canvas);
                }
            }
            for(int i = 0; i < life; i++){
                canvas.drawBitmap(lifeBmp, lifeSource, lives[i], new Paint());
            }

            // this is very important, because you would always keep scaling, if you wouldn't reset at the end of draw()
            int score = 0;
            if(!firstRun) {
                score = Math.round((System.currentTimeMillis() - startTime - pauseDuration) / 1000);
                score = Math.max(score, 0);
            }

            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("" + score, screenWidth - 60, 50, paint);

            canvas.restoreToCount(savedState);
        }
    }

    /**
     * Updates alle parts of the game.
     * @author Emil Etlinger, Carla Jancik
     */
    public void update(){

        if(gameStart && sven.isPlaying()) {
            if(bricks.get(0).getTop() >= screenHeight){
                bricks.poll();
            }
            if (backgroundStart.getCounter() == 0) {
                backgroundStart.update();

/**
            } else if (backgroundEnd.getCounter() >= 5 && backgroundEnd.getCounter() < 7) {

                if (backgroundEnd.getCounter() == 9) {
                    gameLoop.exit();
                } else {
                    backgroundEnd.update();
                    checkCollision();
                    if(jump){
                        sven.setDY(-40);
                    }
                    sven.update();
                    for(int i = 0; i < 8; i++){
                        bricks.get(i).update();
                    }
                }
 */
            } else if (backgroundRun.getCounter() > 0) {
                backgroundRun.update();
            }
            //checkCollision();

            if(jumpCounter>0)
            {
                jumpCounter--;
                sven.jump(true,level);
            }
            else if(jump && checkCollision()){
                // Log.d(TAG, "Jumping...");
                sven.jump(true, level);
                jumpCounter--;
            }else if(checkCollision()){
                // Log.d(TAG, "Moving...");
                sven.setDY((int)speed);
            }else if(!checkCollision()){
                // Log.d(TAG, "Falling...");
                sven.jump(false, level);
            }
            sven.update();
            for(int i = 0; i < 8; i++){
                bricks.get(i).update();
            }
            if(sven.getBottom() > screenHeight)
            {
                life--;
                if (life > 0)
                {
                    sven.setPosition(bricks.get(1).getX(), bricks.get(1).getTop());
                }
                else
                mainActivity.gameOver(level);
            }
            sprite.update();
        }
    }

    /*

    Older try on the update method.

    public void update(float delta){
        if(backgroundStart.getCounter() == 0) {
            backgroundStart.update(delta);
        }else if(backgroundEnd.getCounter() >= 5 && backgroundEnd.getCounter() < 7) {

            if(backgroundEnd.getCounter() ==6) {
                gameLoop.exit();
            }else {
                backgroundEnd.update(delta);
            }
        }
        else if(backgroundRun.getCounter() >0 && backgroundRun.getCounter()<5)
        {
            backgroundRun.update(delta);
        }
    }
    */

    /**
     * Checks if Sven is standin on something(Brick/Ground).
     * @return if collision occured
     * @author Carla Jancik
     */
    public boolean checkCollision()
    {
        Rect svenRect = sven.getRect();
        Brick b;
        for (int i = 0; i < 8; i++) {
            b = bricks.get(i);
            Rect brickRect = b.getRect();
            if (svenRect.intersect(brickRect)) {
                brick = b;
                if (sven.getBottom() + 5 >= b.getTop() && sven.getBottom() - 12 <= b.getTop()) {
                    if ((sven.getLeft() >= b.getLeft() && sven.getRight() <= b.getRight())
                            || ((b.getLeft() - sven.getLeft()) <= (sven.getWidth() / 2 + 10) &&
                            sven.getRight() <= (b.getRight() + sven.getWidth() / 2 + 10))) {
                        sven.setX(b.getTop() - sven.getBottom()+1);
                        sven.setFallingFalse();
                        return true;
                    }
                }
            }
        }
        sven.setFalling(true);
        return false;

    }

    public void pause(){
        // Log.d(TAG, "Pausing...");
        gameLoop.pause();
        pauseStart = System.currentTimeMillis();
    }

    /**
     * resumes the gameloop, saves duration of pausetime, for score-calculation
     * @author Carla Jancik
     */
    public void resume(){
        // Log.d(TAG, "Resuming...");
        if(gameLoop != null) {
            gameLoop.resumeGameloop();
        }
        pauseDuration += System.currentTimeMillis() - pauseStart;
    }

    /**
     * ends the gameloop
     * @author Carla Jancik
     */
    public void exit(){
        if(gameLoop != null){
            gameLoop.exit();
        }
    }
}