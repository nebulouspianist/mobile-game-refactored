package edu.ucsd.flappycow.view.components.draw;

import android.graphics.Bitmap;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.util.Util;

public class FrontgroundView extends SpriteView{
    public static Bitmap globalBitmap;

    public FrontgroundView(GameActivity gameActivity){
        super (gameActivity);
        if (globalBitmap == null) {
            globalBitmap = Util.getDownScaledBitmapAlpha8(gameActivity, R.drawable.fg);
        }
        this.bitmap = globalBitmap;
    }

}
