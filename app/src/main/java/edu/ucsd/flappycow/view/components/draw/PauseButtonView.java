package edu.ucsd.flappycow.view.components.draw;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.R;
import edu.ucsd.flappycow.util.Util;

public class PauseButtonView extends SpriteView {

    public PauseButtonView( GameActivity gameActivity) {
        super(gameActivity);
        this.bitmap = Util.getScaledBitmapAlpha8(gameActivity, R.drawable.pause_button);
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

}
