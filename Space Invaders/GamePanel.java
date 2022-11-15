// GamePanel.java
// Jacob Gaisinsky
// class that contains the game logic and draws the game to the screen

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    
    private Timer 
    timer, // Timer that updates the game (every 20 ms)
    enemy_timer, // Timer that moves the enemies (starts at 750 ms and decreases every level or every time the aliens move down)
    ufo_timer, // Timer that spawns the UFO (random time between 10 and 20 seconds)
    pause_timer; // Timer that listens to key presses in the pause menu (every 20 ms)

    private boolean 
    state = false, // State of the panel
    respawning = false, // to see if the tank is respawning
    queueDeath = false, // to see if the tank is dead (we need this so that the tank dies after the screen is drawn)
    muted = false, // to see if the sound effects are muted
    paused = false; // to see if the game is paused

    private int 
    score = 0, // the score
    level = 0, // current level
    timer_delay = 750, // delay of the enemy timer (starts at 1000 ms and decreases every level or every time the aliens move down)
    move_sound_state = 0; // to play different sounds each time the aliens move
    
    private Image 
    alien_death, // image of the alien death animation
    muted_image, // image of the muted button
    unmuted_image; // image of the unmuted button

    private RespawnPanel respawn; // the respawn panel
    private String high_score; // the current high score
    private Scanner readFile; // scanner to read the high score file
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>(); // the enemies
    private ArrayList<int[]> dead_enemies = new ArrayList<int[]>(); // to store the dead enemies (so that we can draw the death animation)
    private int[] ufo_death_animation = new int[4]; // to store the ufo death animation (so that we can draw the death animation)
    private Tank tank; // the tank
    private UFO ufo; // the UFO (null if there is no UFO)
    private Fort[] forts; // the forts
    private boolean[] keys = new boolean[1000]; // to see if a key is pressed
    private Font font; // the font used for the score and the level
    private SoundEffect[] move_sound = new SoundEffect[4]; // to play different sounds each time the aliens move
    private SoundEffect tank_death, ufo_death, ufo_move, enemy_death; // all sound effects

    public GamePanel() { // constructor
        setPreferredSize(new Dimension(SpaceInvaders.WIDTH, SpaceInvaders.HEIGHT));
        setBackground(Color.BLACK);

        fillEnemyList(200, 0.001);

        alien_death = new ImageIcon("assets/alien_death.png").getImage();
        muted_image = new ImageIcon("assets/muted.png").getImage();
        unmuted_image = new ImageIcon("assets/unmuted.png").getImage();

        tank = new Tank(171, 3);
        ufo = null;
        forts = new Fort[4];
        forts[0] = new Fort(150);
        forts[1] = new Fort(412);
        forts[2] = new Fort(674);
        forts[3] = new Fort(936);

        try {
            readFile = new Scanner(new BufferedInputStream(new FileInputStream("assets/high_score.txt")));
            high_score = readFile.nextLine();
            readFile.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        InputStream is = GamePanel.class.getResourceAsStream("assets/pixel_font.ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            font = font.deriveFont(40f);
        } 
        catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 4; i ++) {
            move_sound[i] = new SoundEffect("assets/sound_effects/move" + i + ".wav");
        }
        tank_death = new SoundEffect("assets/sound_effects/tank_death.wav");
        ufo_death = new SoundEffect("assets/sound_effects/ufo_death.wav"); 
        ufo_move = new SoundEffect("assets/sound_effects/ufo_move.wav"); 
        enemy_death = new SoundEffect("assets/sound_effects/enemy_death.wav"); 

        respawn = new RespawnPanel(this);
        add(respawn);

        timer = new Timer(20, this);
        enemy_timer = new Timer(timer_delay, this);
        ufo_timer = new Timer(Util.randint(10000, 20000), this);
        pause_timer = new Timer(20, this);
    }

    @Override
    public void keyReleased(KeyEvent e) { // when a key is released
        keys[e.getKeyCode()] = false; 
    }
    @Override
    public void keyPressed(KeyEvent e) { // when a key is pressed
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) { // when a timer is triggered
        if (e.getSource() == timer) { 
            move();
            shoot();
            removeEnemy();
            damageFort();
            removeTank();
            controlSound();
            pauseScreen();
            if (queueDeath) {
                updateHighScoreFile();
                state = false;
            }
        }
        else if (e.getSource() == enemy_timer) {
            if (canMove()) { // if the aliens can move to the side 
                for (int i = 0; i < enemies.size(); i++) { // move the aliens
                    enemies.get(i).move();
                    if (enemies.get(i).isBelowForts()) { // if the aliens are below the forts
                        enemies.clear(); // get rid of all the enemies
                        queueDeath = true; // queue the death of the tank
                    }
                }
            }
            else { // if the aliens can't move to the side
                for (int i = 0; i < enemies.size(); i++) { // change the direction of the aliens and moves them down
                    enemies.get(i).changeDir(); 
                }
                timer_delay *= 0.8; // make the aliens move faster
                enemy_timer.setDelay((int) (timer_delay));
            }
            move_sound[move_sound_state].play(); // play the sound effect
            move_sound_state = (move_sound_state + 1) % 4; // change the sound effect
        }
        else if (e.getSource() == ufo_timer) { 
            ufo = new UFO(); // spawn the UFO
            ufo_timer.setDelay(Util.randint(10000, 20000)); // set the delay of the timer to another random time between 10 and 20 seconds
            ufo_move.play(); // play the ufo move sound effect
        }
        else if (e.getSource() == pause_timer) {
            unpauseScreen();
            quitPauseScreen();
        }
    }
    
    @Override
    public void paint(Graphics g) { // draws the screen
        if (!respawning) { // if the tank is not respawning
            draw(g);
        }
        else { // if the tank is respawning
            respawn.draw(g);
        }
    }

    public void move() { // moves the tank and the UFO
        tank.move(keys);
        if (ufo != null) { // if there is a UFO
            ufo.move();
            if (ufo.isOffScreen()) { // if the UFO is off screen
                ufo = null; // remove the UFO
                ufo_move.stop(); // stop the ufo move sound effect
            }
        }
    }

    public void shoot() { // shoots the tank and the enemies
        tank.shoot(keys);
        for (int i = 0; i < enemies.size(); i ++) {
            enemies.get(i).shoot();
        }
    }

    public void controlSound() {
        if (keys[KeyEvent.VK_M] && !muted) { // if the M key is pressed and the game is not muted
            muteAllSound(); // mute all of the sound effects
            keys[KeyEvent.VK_M] = false; // so that the sound doesn't get muted again
            muted = true; // the game is now muted
        }
        else if (keys[KeyEvent.VK_M] && muted) { // if the M key is pressed and the game is muted
            unmuteAllSound(); // unmute all of the sound effects
            keys[KeyEvent.VK_M] = false; // so that the sound doesn't get unmuted again
            muted = false; // the game is now unmuted
        }

    }

    public void pauseScreen() {
        if (keys[KeyEvent.VK_ESCAPE]) { // if the escape key is pressed and the game is not paused
            paused = true; // pause/unpause the game
            stopTimers();
            pause_timer.start(); // start the pause timer
            keys[KeyEvent.VK_ESCAPE] = false; // so that the game doesn't get paused again
        }
    }

    public void unpauseScreen() {
        if (keys[KeyEvent.VK_ESCAPE]) { // if the escape key is pressed and the game is paused
            paused = false; // pause/unpause the game
            startTimers();
            pause_timer.stop(); // stop the pause timer
            keys[KeyEvent.VK_ESCAPE] = false; // so that the game doesn't get unpaused again
        }
    }

    public void quitPauseScreen() {
        if (keys[KeyEvent.VK_Q]) { // if the game is paused and the Q key is pressed, quit the game
            updateHighScoreFile();
            queueDeath = true;
            paused = false;
            startTimers();
            pause_timer.stop();
            keys[KeyEvent.VK_Q] = false;
        }
    }

    public void draw(Graphics g) {
        // draw the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); 

        // draw the score, high score and lives
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("SCORE", 10, 50);
        g.drawString("HIGH SCORE", 400, 50);
        g.drawString("LIVES", 975, 50);
        g.setColor(Color.GREEN);
        g.drawString("" + score, 175, 50);
        g.drawString("" + high_score, 675, 50);
        g.drawString("" + tank.getLives(), 1125, 50);

        if (muted) { // if the game is muted, draw the mute icon
            g.drawImage(muted_image, 10, SpaceInvaders.HEIGHT - 10 - muted_image.getHeight(null), null);
        }
        else { // if the game is not muted, draw the unmute icon
            g.drawImage(unmuted_image, 10, SpaceInvaders.HEIGHT - 10 - unmuted_image.getHeight(null), null);
        }

        tank.draw(g); // draw the tank

        for (int i = 0; i < enemies.size(); i ++) { // draw the enemies
            enemies.get(i).draw(g);
        }
        for (int i = 0; i < forts.length; i ++) { // draw the forts
            forts[i].draw(g);
        }
        for (int i = dead_enemies.size() - 1; i >= 0; i --) { // draw the dead enemies
            g.drawImage(alien_death, dead_enemies.get(i)[0], dead_enemies.get(i)[1], null);
            dead_enemies.get(i)[2] --; // decrease the counter of the dead enemy animation
            if (dead_enemies.get(i)[2] == 0) { // if the counter is 0, get rid of the animation
                dead_enemies.remove(i);
            }
        }
        if (ufo_death_animation[2] > 0) { // if the UFO is dying
            if (ufo_death_animation[2] % 10 < 5) { // if the counter is even
                g.setColor(Color.WHITE);
                g.setFont(font.deriveFont(30f));
                g.drawString(Integer.toString(ufo_death_animation[3]), ufo_death_animation[0], ufo_death_animation[1]); // draws the ufo's death animation
            }
            ufo_death_animation[2] --; // decrease the counter of the UFO death animation
        }
        if (ufo != null) { // if there is a UFO, draw it
            ufo.draw(g);
        }

        if (paused) { // if the game is paused
            // draw a transparent white rectangle over the screen
            g.setColor(new Color(0, 0, 0, 100)); 
            g.fillRect(0, 0, getWidth(), getHeight());
            // draw the pause text
            g.setColor(Color.WHITE);
            g.setFont(font.deriveFont(50f));
            g.drawString("PAUSED", Util.centreText("PAUSED", SpaceInvaders.WIDTH, g.getFont()), 200);
            // draw the instructions
            g.setFont(font.deriveFont(20f));
            g.drawString("Press ESC to unpause", Util.centreText("Press ESC to unpause", SpaceInvaders.WIDTH, g.getFont()), 350);
            g.drawString("Press \"Q\" to quit", Util.centreText("Press \"Q\" to quit", SpaceInvaders.WIDTH, g.getFont()), 400);
        }
    }

    public void fillEnemyList(int y, double fire_rate) { // fills the enemy list with 55 aliens (5 rows of 11 aliens)
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(0, 98 + (i * (38 + 30)), y, fire_rate));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(1, 98 + (i * (51 + 16)), y + 70, fire_rate));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(1, 94 + (i * (51 + 16)), y + 140, fire_rate));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(2, 90 + (i * (56 + 12)), y + 205, fire_rate));
        }
        for (int i = 0; i < 11; i ++) {
            enemies.add(new Enemy(2, 90 + (i * (56 + 12)), y + 275, fire_rate));
        }
    }
    
    public boolean canMove() { // checks if all aliens can move to the side
        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).canMove()) {
                return false;
            }
        }
        return true;
    }

    public void removeEnemy() { // removes an enemy if it is dead
        for (int i = enemies.size() - 1; i >= 0; i --) {
            if (enemies.get(i).isHit(tank.getBullet())) { // if the enemy is hit by the tank's bullet
                dead_enemies.add(new int[] {enemies.get(i).getX(), enemies.get(i).getY(), 5}); // add the dead enemy animation

                // increase the score depending on the type of enemy
                if (enemies.get(i).getType() == 0) {
                    score += 40;
                }
                else if (enemies.get(i).getType() == 1) {
                    score += 20;
                }
                else if (enemies.get(i).getType() == 2) {
                    score += 10;
                }
                enemies.remove(i); // remove the enemy for the ArrayList
                tank.getBullet().remove(); // remove the bullet
                enemy_death.play(); // play the enemy death sound effect
            }
        }
        if (enemies.size() == 0 && !queueDeath) { // if there are no more enemies and the tank is not dead
            level ++; 
            tank.setLives(tank.getLives() + 1); // give the tank an extra life
            fillEnemyList(200, 0.001 + (level * 0.002)); // fill the enemy list with the next level of aliens that shoot faster
            timer_delay = 750 - (level * 50); // set the delay of the timer to the next level of aliens that move faster
            enemy_timer.setDelay(timer_delay);
        }
        if (ufo != null ) { // if there is a UFO
            if (ufo.isHit(tank.getBullet())) { // if the UFO is hit by the tank's bullet`
                ufo_death_animation[0] = ufo.getX(); // set the x position of the UFO death animation
                ufo_death_animation[1] = ufo.getY(); // set the y position of the UFO death animation
                ufo_death_animation[2] = 30; // set the counter of the UFO death animation
                ufo_death_animation[3] = ufo.getScore(); // set the score of the UFO death animation
                score += ufo.getScore(); // increase the score by the amount of points the UFO is worth
                ufo = null; // remove the UFO
                tank.getBullet().remove(); // remove the bullet
                ufo_death.play(); // play the ufo death sound effect
                ufo_move.stop(); // stop the ufo move sound effect
            }
        }
    }
    
    public void damageFort() { // damages the forts if they are hit by the tank's bullet
        for (int i = forts.length - 1; i >= 0; i --) {
            if (forts[i].damage(tank.getBullet())) { // if the fort is damaged (fort gets damaged in the if statment)
                tank.getBullet().remove(); // remove the bullet
            }
        }
        for (int i = enemies.size() - 1; i >= 0; i --) {
            for (int j = forts.length - 1; j >= 0; j --) {
                if (forts[j].damage(enemies.get(i).getBullet())) { // if the fort is damaged (fort gets damaged in the if statment)
                    enemies.get(i).getBullet().remove(); // remove the bullet
                }
            }
        }
    }

    public void removeTank() { // damages the tank if it is hit by an enemy's bullet
        for (int i = enemies.size() - 1; i >= 0; i --) {
            if (enemies.get(i).isShooting()) { // if the enemy is shooting
                if (enemies.get(i).getBullet().getHitbox().intersects(tank.getHitbox())) { // if the enemy's bullet hits the tank
                    respawning = true; // start the respawning process
                    stopTimers(); // stop the timers
                    respawn.startRespawning(); // start the respawning animation
                    tank_death.play(); // play the tank death sound effect
                    for (int j = 0; j < enemies.size(); j ++) { // remove all the enemies' bullets
                        enemies.get(j).setBullet(null);
                    }
                    tank.setBullet(null); // remove the tank's bullet
                }
            }
        }
    }

    public void updateHighScoreFile() { // updates the high score file
        try {
            PrintWriter outFile = new PrintWriter(new BufferedWriter (new FileWriter ("assets/high_score.txt"))); 
            outFile.print(Math.max(Integer.parseInt(high_score), score)); // write the high score to the file
            outFile.close();
        }
        catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
    
    public void startGame() { // starts the game panel
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        startTimers();
    }
    
    public void stopGame() { // stops the game panel and removes all the timers
        stopTimers();
        ufo_timer = new Timer(Util.randint(10000, 20000), this);
        removeKeyListener(this);
        setFocusable(false);

        // resets all variables
        tank = new Tank(171, 3);
        if (muted) {
            tank.getSound().mute();
        }
        ufo = null;
        enemies = new ArrayList<Enemy>();
        forts = new Fort[4];
        forts[0] = new Fort(150);
        forts[1] = new Fort(412);
        forts[2] = new Fort(674);
        forts[3] = new Fort(936);
        dead_enemies = new ArrayList<int[]>();
        score = 0;
        enemies = new ArrayList<Enemy>();
        fillEnemyList(200, 0.001);
        keys = new boolean[1000];
        level = 0;
        timer_delay = 1000;
        queueDeath = false;
        respawning = false;
        enemy_timer = new Timer(timer_delay, this);
        
    }

    public void startTimers() { // starts all the timers
        timer.start();
        enemy_timer.start();
        ufo_timer.start();
    }

    public void stopTimers() { // stops all the timers
        timer.stop();
        enemy_timer.stop();
        ufo_timer.stop();
    }
    
    public void muteAllSound() { // mutes all the sound
        for (int i = 0; i < 4; i ++) {
            move_sound[i].mute();
        }
        tank_death.mute();
        ufo_death.mute();
        ufo_move.mute();
        enemy_death.mute();
        tank.getSound().mute();
    }

    public void unmuteAllSound() { // unmutes all the sound
        for (int i = 0; i < 4; i ++) {
            move_sound[i].unmute();
        }
        tank_death.unmute();
        ufo_death.unmute();
        ufo_move.unmute();
        enemy_death.unmute();
        tank.getSound().unmute();
    }

    // Getter and setter methods

    public void setState(boolean s) {
        this.state = s;
    }
    public boolean getState() {
        return state;
    }
    public Tank getTank() {
        return tank;
    }
    public void setTank(Tank tank) {
        this.tank = tank;
    }
    public void setRespawning(boolean respawning) {
        this.respawning = respawning;
    }
    public void setQueueDeath(boolean queueDeath) {
        this.queueDeath = queueDeath;
    }
    public String getHighScore() {
        return high_score;
    }
    public int getScore() {
        return score;
    }
    public boolean getMuted() {
        return muted;
    }

    // unused KeyListener methods

    @Override
    public void keyTyped(KeyEvent e) {}
}
