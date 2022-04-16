package net.minecraft.world.storage.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public interface LootCondition {
     boolean testCondition(Random var1, LootContext var2);

     public abstract static class Serializer {
          private final ResourceLocation lootTableLocation;
          private final Class conditionClass;

          protected Serializer(ResourceLocation location, Class clazz) {
               this.lootTableLocation = location;
               this.conditionClass = clazz;
          }

          public ResourceLocation getLootTableLocation() {
               return this.lootTableLocation;
          }

          public Class getConditionClass() {
               return this.conditionClass;
          }

          public abstract void serialize(JsonObject var1, LootCondition var2, JsonSerializationContext var3);

          public abstract LootCondition deserialize(JsonObject var1, JsonDeserializationContext var2);
     }
}
