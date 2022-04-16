package net.minecraft.client.audio;

import java.util.List;
import javax.annotation.Nullable;

public class SoundList {
     private final List sounds;
     private final boolean replaceExisting;
     private final String subtitle;

     public SoundList(List soundsIn, boolean replceIn, String subtitleIn) {
          this.sounds = soundsIn;
          this.replaceExisting = replceIn;
          this.subtitle = subtitleIn;
     }

     public List getSounds() {
          return this.sounds;
     }

     public boolean canReplaceExisting() {
          return this.replaceExisting;
     }

     @Nullable
     public String getSubtitle() {
          return this.subtitle;
     }
}
