package net.minecraft.world.storage.loot.properties;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public interface EntityProperty {
      boolean testProperty(Random var1, Entity var2);

      public abstract static class Serializer {
            private final ResourceLocation name;
            private final Class propertyClass;

            protected Serializer(ResourceLocation nameIn, Class propertyClassIn) {
                  this.name = nameIn;
                  this.propertyClass = propertyClassIn;
            }

            public ResourceLocation getName() {
                  return this.name;
            }

            public Class getPropertyClass() {
                  return this.propertyClass;
            }

            public abstract JsonElement serialize(EntityProperty var1, JsonSerializationContext var2);

            public abstract EntityProperty deserialize(JsonElement var1, JsonDeserializationContext var2);
      }
}
