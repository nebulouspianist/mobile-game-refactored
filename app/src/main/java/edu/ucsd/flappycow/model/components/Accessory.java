package edu.ucsd.flappycow.model.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import edu.ucsd.flappycow.presenter.GameActivity;

import edu.ucsd.flappycow.util.Contract;

public class Accessory extends Sprite {

    public Accessory(GameActivity gameActivity) {
        super(gameActivity);

        // Invariant: GameActivity should not be null.
        Contract.invariant(gameActivity != null, "GameActivity can't be null.");
    }

    public void moveTo(int x, int y) {
        // Preconditions: x and y should be valid coordinates.
        Contract.require(x >= 0, "x coordinate can't be negative.");
        Contract.require(y >= 0, "y coordinate can't be negative.");

        this.x = x;
        this.y = y;

        // Postconditions: The object should be moved to the correct position.
        Contract.ensure(this.x == x && this.y == y, "The object hasn't been moved to the correct position.");
    }

    public void setBitmap(Bitmap bitmap) {
        // Preconditions: bitmap should not be null.
        Contract.require(bitmap != null, "Bitmap can't be null.");

        this.bitmap = bitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();

        // Postconditions: The object's bitmap, width, and height should be set correctly.
        Contract.ensure(this.bitmap == bitmap && this.width == bitmap.getWidth() && this.height == bitmap.getHeight(),
                "The object's bitmap, width, and height haven't been set correctly.");
    }

    @Override
    public void draw(Canvas canvas) {
        // Preconditions: Canvas should not be null.
        Contract.require(canvas != null, "Canvas can't be null.");

        if (this.bitmap != null) {
            super.draw(canvas);
        }

        // Postconditions: Can be checked depending on the base Sprite's draw behavior.
    }
}
