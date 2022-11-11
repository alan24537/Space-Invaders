// Tank.java
// Jacob Gaisinsky
// Class that contains the tank object

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class Tank {

    final int UP = -1, DOWN = 1; // Constants for the direction of the tank

    private Image tank; // Image of the tank

    private int 
    x, // x coordinate of the tank
    y, // y coordinate of the tank
    speed, // speed of the tank
    lives; // number of lives the tank has

    private Rectangle hitbox; // hitbox of the tank
    private Bullet bullet; // bullet of the tank
    private SoundEffect shoot; // sound effect of the tank shooting
    
    public Tank(int x, int l) { // constructor
        tank = new ImageIcon("assets/tank.png").getImage();

        this.x = x;
        y = 850;
        speed = 10;
        lives = l;

        hitbox = new Rectangle(x, y, tank.getWidth(null), tank.getHeight(null));

        shoot = new SoundEffect("assets/sound_effects/tank_shoot.wav");

        bullet = null;
    }

    public void move(boolean[] keys) { // moves the tank and the bullet
        if (keys[KeyEvent.VK_LEFT] && x > 10) {
            x -= speed;
        }
        if (keys[KeyEvent.VK_RIGHT] && x < 1200 - tank.getWidth(null) - 10) {
            x += speed;
        }
        hitbox.translate( x - (int) hitbox.getX(), 0);
        if (isShooting()) { // if the tank is shooting  
            bullet.move();
            if (bullet.isOffScreen()) { // if the bullet is off the screen, remove it
                bullet = null;
            }
        }
    }

    public void draw(Graphics g) { // draws the tank and the bullet
        g.drawImage(tank, x, y, null);
        if (isShooting()) {
            bullet.draw(g);
        }
    }

    public void shoot(boolean[] keys) { // shoots the bullet if the tank is not already shooting
        if (keys[KeyEvent.VK_SPACE]) {
            if (!isShooting()) {
                bullet = new Bullet(x + tank.getWidth(null) / 2, y, UP);
                shoot.stop();
                shoot.play();
            }
        }
    }

    public boolean isShooting() { // returns whether the tank is shooting or not
        return !(bullet == null);
    }

    public boolean isHit(ArrayList<Bullet> bullets) { // returns whether the tank is hit by a bullet or not
        for (int i = 0; i < bullets.size(); i ++) {
            if (hitbox.intersects(bullets.get(i).getHitbox())) {
                return true;
            }
        }
        return false;
    }
    
    // Getters and Setters
    
    public Rectangle getHitbox() {
        return hitbox;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getLives() {
        return lives;
    }
    public Bullet getBullet() {
        return bullet;
    }
    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }
}
