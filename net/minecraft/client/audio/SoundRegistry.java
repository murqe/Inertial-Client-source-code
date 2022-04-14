package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.registry.RegistrySimple;

public class SoundRegistry extends RegistrySimple {
      private Map soundRegistry;

      protected Map createUnderlyingMap() {
            this.soundRegistry = Maps.newHashMap();
            return this.soundRegistry;
      }

      public void add(SoundEventAccessor accessor) {
            this.putObject(accessor.getLocation(), accessor);
      }

      public void clearMap() {
            this.soundRegistry.clear();
      }
}
