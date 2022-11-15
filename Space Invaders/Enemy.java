// Enemy.java
// Jacob Gaisinsky
// Class that contains the enemy object

import java.awt.*;
import javax.swing.*;

public class Enemy {

    final int DOWN = 1; // constants for the direction of the enemy bullet

    private Image[] enemy = new Image[2]; // array of images of the enemy (used for animation)

    private int 
    state = 0, // state of the enemy and goes from 0 to 1 (used for animation)
    dir, // direction of the enemy
    x, // x position of the enemy
    y, // y position of the enemy
    type; // type of the enemy (used for the score and loading the correct image)

    private double 
    speed, // speed of the enemy
    fire_rate; // fire rate of the enemy
    private Rectangle hitbox; // hitbox of the enemy
    private Bullet bullet; // enemies bullet (null if the enemy is not shooting)
    
    public Enemy(int type, int x, int y, double f_r) { // constructor
        this.type = type;
        this.x = x;
        this.y = y;
        fire_rate = f_r;
        speed = 10;
        dir = 1;
        bullet = null;
        enemy[0] = new ImageIcon("assets/alien" + type + "_0.png").getImage();
        enemy[1] = new ImageIcon("assets/alien" + type + "_1.png").getImage();
        hitbox = new Rectangle(x, y, enemy[0].getWidth(null), enemy[0].getHeight(null));
    }
      
    
    public void draw(Graphics g) { // draws the enemy and its bullet
        g.drawImage(enemy[state], x, y, null);
        if (isShooting()) {
            bullet.draw(g);
        }
    }
    public boolean canMove() { // returns true if the enemy can move to the side and doesn't go off screen
        return x + (speed * dir) < 1200 - 20 - enemy[state].getWidth(null) && x + (speed * dir) > 0 + 15;
    }
    public void move() { // moves the enemy and changes the state of the enemy (used for animation)
        x += speed * dir;
        state = (state + 1) % 2;
        hitbox.translate((int) speed * dir, 0);
    }
    public void changeDir() { // changes the direction of the enemy and moves it down
        dir *= -1;
        y += 20;
        state = (state + 1) % 2;
        hitbox.translate(0, 20);
    }

    public boolean isHit(Bullet b) { // returns true if the enemy is hit by a bullet
        if (b != null) {
            return hitbox.intersects(b.getHitbox());
        }
        return false;
    }

    public void shoot() { // shoots a bullet only if the enemy is the farthest to the back and is not already shooting
        if (type == 0) { // if the enemy is the farthest to the back
            if (isShooting()) { // if the enemy is already shooting
                bullet.move(); // moves the bullet
                if (bullet.isOffScreen()) { // gets rid of the bullet if it goes off screen
                    bullet = null;
                }
            }
            else if (Math.random() <= fire_rate) { // if the enemy is not shooting and the random number is less than the fire rate 
                bullet = new Bullet(x + enemy[state].getWidth(null) / 2, y + enemy[state].getHeight(null), DOWN); // creates a new bullet
            }
        }
    }
    public boolean isShooting() { // returns true if the enemy is shooting
        return !(bullet == null);
    }

    public boolean isBelowForts() { // returns true if the enemy is below the forts
        return y + enemy[state].getHeight(null) > 784;
    }

    // Getters and Setters
    
    public int getDir() { 
        return dir;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getType() {
        return type;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }
    public Bullet getBullet() {
        return bullet;
    }
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
