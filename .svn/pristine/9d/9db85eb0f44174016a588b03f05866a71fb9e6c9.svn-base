package at.ac.tuwien.ims.svn.phantasmophobia;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Carla Jancik on 23.12.2015.
 */
public abstract class GameObject {

    protected boolean playing;


    public GameObject(){
        this.playing = false;
    }

    public boolean isPlaying(){
        return playing;
    }

    public void setPlaying(boolean playing){
        this.playing = playing;
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);
}
