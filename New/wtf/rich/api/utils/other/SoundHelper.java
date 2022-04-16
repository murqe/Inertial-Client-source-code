package wtf.rich.api.utils.other;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import wtf.rich.Main;

public class SoundHelper {
     public static void playSound(String url) {
          (new Thread(() -> {
               try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/assets/minecraft/richclient/songs/" + url));
                    clip.open(inputStream);
                    clip.start();
               } catch (Exception var3) {
               }

          })).start();
     }
}
