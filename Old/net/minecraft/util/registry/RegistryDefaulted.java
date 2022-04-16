package net.minecraft.util.registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RegistryDefaulted extends RegistrySimple {
      private final Object defaultObject;

      public RegistryDefaulted(Object defaultObjectIn) {
            this.defaultObject = defaultObjectIn;
      }

      @Nonnull
      public Object getObject(@Nullable Object name) {
            Object v = super.getObject(name);
            return v == null ? this.defaultObject : v;
      }
}
