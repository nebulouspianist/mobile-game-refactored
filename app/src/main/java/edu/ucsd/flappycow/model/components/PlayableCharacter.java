/**
 * The SuperClass of every character that is controlled by the player
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package edu.ucsd.flappycow.model.components;

import edu.ucsd.flappycow.presenter.GameActivity;
import edu.ucsd.flappycow.util.Contract;

public abstract class PlayableCharacter extends Sprite {

    protected boolean isDead = false;

    protected int viewHeight;
    protected int viewWidth;

    public PlayableCharacter(GameActivity gameActivity, int viewHeight, int viewWidth) {
        super(gameActivity);
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
        move();

    }

    /**
     * Calls super.move
     * Moves the character to 1/6 of the horizontal screen
     * Manages the speed changes -> Falling
     */
    @Override
//    public void move() {
//        this.x = viewWidth / 6;
//
//        if (speedY < 0) {
//            // The character is moving up
//            speedY = speedY * 2 / 3 + getSpeedTimeDecrease(viewHeight) / 2;
//        } else {
//            // the character is moving down
//            this.speedY += getSpeedTimeDecrease(viewHeight);
//        }
//
//        if (this.speedY > getMaxSpeed(viewHeight)) {
//            // speed limit
//            this.speedY = getMaxSpeed(viewHeight);
//        }
//
//        super.move();
//
//
//
//    }


    public void move(){
        try {
            // Preconditions
            Contract.require(this.x >= 0 && this.x <= viewWidth, "x must be within viewWidth");
            Contract.require(this.y >= 0 && this.y<= viewHeight, "y should be within viewHeight");


            //invariant
            Contract.invariant(viewHeight > 0, "viewHeight should be positive");
            Contract.invariant(viewWidth > 0, "viewWidth should be positive");
//            Contract.invariant(viewHeight > 0, "viewWidth should be positive");

            this.x = viewWidth / 6;

            if (speedY < 0) {
                // The character is moving up
                speedY = speedY * 2 / 3 + getSpeedTimeDecrease(viewHeight) / 2;
            } else {
                // the character is moving down
                this.speedY += getSpeedTimeDecrease(viewHeight);
            }

            if (this.speedY > getMaxSpeed(viewHeight)) {
                // speed limit
                this.speedY = getMaxSpeed(viewHeight);
            }

            super.move();

            // Postconditions
            Contract.ensure(this.x == viewWidth / 6, "x must be equal to viewWidth / 6");
            Contract.ensure(this.speedY <= getMaxSpeed(viewHeight), "speedY must be less than or equal to maxSpeed");

            // Class invariant
            Contract.invariant(viewWidth > 0, "viewWidth should be positive");
        } catch (Contract.ViolationException ex) {
            // handle exception
            ex.printStackTrace();
        }
    }

    /**
     * A dead character falls slowly to the ground.
     */
    public void dead() {
        this.isDead = true;
        this.speedY = getMaxSpeed(this.viewHeight) / 2;
    }

    /**
     * Let the character flap up.
     */
//    public void onTap() {
//        System.out.println("[***DBG***]: this.speedY: " + this.speedY + ", this.viewHeight: " + this.viewHeight +
//                ", this.y: " + this.y + ", this.viewHeight: " + this.viewHeight);
//        this.speedY = getTabSpeed(this.viewHeight);
//        this.y += getPosTabIncrease(this.viewHeight);
//    }

    public void onTap() {
        try {
            // Preconditions
//            Contract.require(this.speedY >= 0, "speedY should be non-negative");
            Contract.require(this.viewHeight > 0, "viewHeight should be positive");
            Contract.require(this.y >= 0 && this.y <= viewHeight, "y should be within the viewHeight range");

            // Class invariant
            Contract.invariant(viewHeight > 0, "viewHeight should be positive");

            System.out.println("[***DBG***]: this.speedY: " + this.speedY + ", this.viewHeight: " + this.viewHeight +
                    ", this.y: " + this.y + ", this.viewHeight: " + this.viewHeight);
            this.speedY = getTabSpeed(this.viewHeight);
            this.y += getPosTabIncrease(this.viewHeight);

            // Postconditions
            Contract.ensure(this.speedY == getTabSpeed(viewHeight), "speedY should be equal to getTabSpeed(viewHeight)");
            Contract.ensure(this.y >= 0 && this.y <= viewHeight, "y should be within the viewHeight range");

            // Class invariant
            Contract.invariant(viewHeight > 0, "viewHeight should be positive");
        } catch (Contract.ViolationException ex) {
            // handle exception
            ex.printStackTrace();
        }
    }

    /**
     * Falling speed limit
     *
     * @return
     */
    protected float getMaxSpeed(int viewHeight) {
        // 25 @ 720x1280 px
        return viewHeight / 51.2f;
    }

    /**
     * Every run cycle the speed towards the ground will increase.
     *
     * @return
     */
    protected float getSpeedTimeDecrease(int viewHeight) {
        // 4 @ 720x1280 px
        return viewHeight / 320;
    }

    /**
     * The character gets this speed when taped.
     *
     * @return
     */
    protected float getTabSpeed(int viewHeight) {
        // -80 @ 720x1280 px
        return -viewHeight / 16f;
    }

    /**
     * The character jumps up the pixel height of this value.
     *
     * @return
     */
    protected int getPosTabIncrease(int viewHeight) {
        // -12 @ 720x1280 px
        return -viewHeight / 100;
    }

    public void revive() {
        this.isDead = false;
        this.row = 0;
    }

    public void upgradeBitmap(int points) {
        // Change bitmap, maybe when a certain amount of point is reached.
    }

    public void wearMask() {
        // Change bitmap to have a mask.
    }

    public boolean isDead() {
        return this.isDead;
    }
}
