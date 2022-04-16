package net.minecraft.world.storage.loot.properties;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.ResourceLocation;

public class EntityPropertyManager {
     private static final Map NAME_TO_SERIALIZER_MAP = Maps.newHashMap();
     private static final Map CLASS_TO_SERIALIZER_MAP = Maps.newHashMap();

     public static void registerProperty(EntityProperty.Serializer p_186644_0_) {
          ResourceLocation resourcelocation = p_186644_0_.getName();
          Class oclass = p_186644_0_.getPropertyClass();
          if (NAME_TO_SERIALIZER_MAP.containsKey(resourcelocation)) {
               throw new IllegalArgumentException("Can't re-register entity property name " + resourcelocation);
          } else if (CLASS_TO_SERIALIZER_MAP.containsKey(oclass)) {
               throw new IllegalArgumentException("Can't re-register entity property class " + oclass.getName());
          } else {
               NAME_TO_SERIALIZER_MAP.put(resourcelocation, p_186644_0_);
               CLASS_TO_SERIALIZER_MAP.put(oclass, p_186644_0_);
          }
     }

     public static EntityProperty.Serializer getSerializerForName(ResourceLocation p_186646_0_) {
          EntityProperty.Serializer serializer = (EntityProperty.Serializer)NAME_TO_SERIALIZER_MAP.get(p_186646_0_);
          if (serializer == null) {
               throw new IllegalArgumentException("Unknown loot entity property '" + p_186646_0_ + "'");
          } else {
               return serializer;
          }
     }

     public static EntityProperty.Serializer getSerializerFor(EntityProperty property) {
          EntityProperty.Serializer serializer = (EntityProperty.Serializer)CLASS_TO_SERIALIZER_MAP.get(property.getClass());
          if (serializer == null) {
               throw new IllegalArgumentException("Unknown loot entity property " + property);
          } else {
               return serializer;
          }
     }

     static {
          registerProperty(new EntityOnFire.Serializer());
     }
}
