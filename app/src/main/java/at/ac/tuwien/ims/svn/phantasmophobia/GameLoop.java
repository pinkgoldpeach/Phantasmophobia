package at.ac.tuwien.ims.svn.phantasmophobia;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Carla Jancik on 24.10.2015.
 * tutorial for framerateindependency http://obviam.net/index.php/the-android-game-loop/
 * if you want to display your game with 30 fps, one frame will need 1000/30 milliseconds to update and render
 * because some frames take longer/shorter and differnt devices have different capacities, you need to handle framerate independency
 * if a frame updates and renders too fast, just put the thread to sleep for the remaining time.
 * if a frame takes too long, skip the next frame and update instead. then update and render the next frame.
 * this will also give some rest to your cpu.,
 * @author Carla Jancik
 */
public class GameLoop extends Thread {

    private SurfaceHolder holder;
    private GameSurfaceView view;
    private boolean running = true;
    //this would be the ideal amount of frames per second
    private int idealFPS = 50;
    //this is the maximum number of frames, that can be skipped, to still have a fluid game
    private int maxSkipped = 3;
    //time one frame should need in nanoseconds - might be shorter (sleep) or longer (skip)
    private long framePeriod = 1000000 / idealFPS;
    private boolean pause;

    public static Canvas canvas;

    public GameLoop(SurfaceHolder holder, GameSurfaceView gameSurfaceView) {
        this.holder = holder;
        this.view = gameSurfaceView;
        this.pause = false;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }


    /**
     * runs the game
     */
    @Override
    public void run() {
        //time in milliseconds
        long previousTime = System.currentTimeMillis();
        float tpf = 0;
        canvas = null;
        running = true;
        while (running) {
            // updateGame(tpf);

            canvas = null;

            int framesSkipped = 0;

            try {
                canvas = this.holder.lockCanvas();
                synchronized (holder) {
                    if (canvas != null) {

                        if(!pause) {
                            view.update();
                        }
                        view.draw(canvas);

                    }
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.currentTimeMillis();
            long updateLength = now - previousTime;
            float delta = (updateLength / ((float) framePeriod))/10;
            long sleeptime = framePeriod - updateLength;

            if (sleeptime / 1000000 > 0) {
                try {
                    sleep(sleeptime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (sleeptime < 0 && framesSkipped < maxSkipped) {
                if(!pause) {
                    view.update();
                }
               // view.update(delta);
                sleeptime += framePeriod;
                framesSkipped++;
            }
        }
        //"Destroy"
    }

    public void exit() {
        running = false;
    }

    public void pause(){
        this.pause = true;
    }

    public void resumeGameloop(){
        this.pause = false;
    }
}
