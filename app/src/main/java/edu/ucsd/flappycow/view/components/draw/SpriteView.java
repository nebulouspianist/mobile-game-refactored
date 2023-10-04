package edu.ucsd.flappycow.view.components.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import edu.ucsd.flappycow.presenter.GameActivity;

public class SpriteView {


    /** The bitmaps that holds the frames that should be drawn */
    protected Bitmap bitmap;

    /** Height and width of one frame of the bitmap */
    protected int height, width;

    /** x and y coordinates on the canvas */
    protected int x, y;


    /** The source frame of the bitmap that should be drawn */
    protected Rect src;

    /** The destination area that the frame should be drawn to */
    protected Rect dst;

    /** Coordinates of the frame in the spritesheet */
    protected byte col, row;

    protected short frameTime;

    /**
     * Counter for the frames
     * Cycling through the columns
     */
    protected short frameTimeCounter;

    public SpriteView(GameActivity gameActivity) {
//        this.view = view;
//        this.gameActivity = gameActivity;
        frameTime = 1;
        src = new Rect();
        dst = new Rect();
    }

    /**
     * Draws the frame of the bitmap specified by col and row
     * at the position given by x and y
     * @param canvas Canvas that should be drawn on
     */
    public void draw(Canvas canvas, int x, int y) {
        src.set(col * width, row * height, (col + 1) * width, (row + 1) * height);
        dst.set(x, y, x + width, y + height);
        canvas.drawBitmap(bitmap, src, dst, null);
    }
}
