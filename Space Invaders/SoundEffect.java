// SoundEffect.java
// Jacob Gaisinsky
// Class that plays sound effects

import java.io.*;
import javax.sound.sampled.*;


class SoundEffect {
    private Clip c; // clip that plays the sound effect
    private BooleanControl muted; // volume control for the clip

    public SoundEffect(String filename) { // constructor
        setClip(filename);
    }
    public void setClip(String filename) { // sets the clip to the sound effect file
        try {
            File f = new File(filename); // gets the file
            c = AudioSystem.getClip(); // gets the clip
            c.open(AudioSystem.getAudioInputStream(f)); // sets the clip as the sound effect file
            muted = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);

        } 
        catch (Exception e) { 
            System.out.println("error"); 
        }
    }
    public void play() { // plays the sound effect
        c.setFramePosition(0);
        c.start();
    }
    public void stop() { // stops the sound effect
        c.stop();
    }
    public void mute() { // mutes the sound effect
        muted.setValue(true);
    }
    public void unmute() { // unmutes the sound effect
        muted.setValue(false);
    }
}