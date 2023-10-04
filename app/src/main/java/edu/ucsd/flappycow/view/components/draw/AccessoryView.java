package edu.ucsd.flappycow.view.components.draw;

import android.graphics.Canvas;

import edu.ucsd.flappycow.presenter.GameActivity;

public class AccessoryView extends SpriteView{

    public AccessoryView( GameActivity gameActivity) {
        super( gameActivity);
    }
    @Override
    public void draw(Canvas canvas, int x, int y) {
        if (this.bitmap != null) {
            super.draw(canvas, x, y);
        }
    }
}
