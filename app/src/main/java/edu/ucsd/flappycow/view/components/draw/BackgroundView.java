package edu.ucsd.flappycow.view.components.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.ucsd.flappycow.R;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Util;

public class BackgroundView extends SpriteView {

    public static Bitmap globalBitmap;

    public BackgroundView(GameActivity gameActivity){

        super(gameActivity);

        if (globalBitmap == null) {
            globalBitmap = Util.getDownScaledBitmapAlpha8(gameActivity, R.drawable.bg);
        }
        this.bitmap = globalBitmap;
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        double factor = (1.0 * canvas.getHeight()) / bitmap.getHeight();

        if (-x > bitmap.getWidth()) {
            // The first bitmap is completely out of the screen
            x += bitmap.getWidth();
        }

        int endBitmap = Math.min(-x + (int) (canvas.getWidth() / factor), bitmap.getWidth());
        int endCanvas = (int) ((endBitmap + x) * factor) + 1;
        src.set(-x, 0, endBitmap, bitmap.getHeight());
        dst.set(0, 0, endCanvas, canvas.getHeight());
        canvas.drawBitmap(this.bitmap, src, dst, null);

        if (endBitmap == bitmap.getWidth()) {
            // draw second bitmap
            src.set(0, 0, (int) (canvas.getWidth() / factor), bitmap.getHeight());
            dst.set(endCanvas, 0, endCanvas + canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(this.bitmap, src, dst, null);
        }
    }
}
