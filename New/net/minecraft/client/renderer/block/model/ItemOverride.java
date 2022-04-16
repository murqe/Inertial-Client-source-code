package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemOverride {
     private final ResourceLocation location;
     private final Map mapResourceValues;

     public ItemOverride(ResourceLocation locationIn, Map propertyValues) {
          this.location = locationIn;
          this.mapResourceValues = propertyValues;
     }

     public ResourceLocation getLocation() {
          return this.location;
     }

     boolean matchesItemStack(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase livingEntity) {
          Item item = stack.getItem();
          Iterator var5 = this.mapResourceValues.entrySet().iterator();

          Entry entry;
          IItemPropertyGetter iitempropertygetter;
          do {
               if (!var5.hasNext()) {
                    return true;
               }

               entry = (Entry)var5.next();
               iitempropertygetter = item.getPropertyGetter((ResourceLocation)entry.getKey());
          } while(iitempropertygetter != null && iitempropertygetter.apply(stack, worldIn, livingEntity) >= (Float)entry.getValue());

          return false;
     }

     static class Deserializer implements JsonDeserializer {
          public ItemOverride deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
               JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
               ResourceLocation resourcelocation = new ResourceLocation(JsonUtils.getString(jsonobject, "model"));
               Map map = this.makeMapResourceValues(jsonobject);
               return new ItemOverride(resourcelocation, map);
          }

          protected Map makeMapResourceValues(JsonObject p_188025_1_) {
               Map map = Maps.newLinkedHashMap();
               JsonObject jsonobject = JsonUtils.getJsonObject(p_188025_1_, "predicate");
               Iterator var4 = jsonobject.entrySet().iterator();

               while(var4.hasNext()) {
                    Entry entry = (Entry)var4.next();
                    map.put(new ResourceLocation((String)entry.getKey()), JsonUtils.getFloat((JsonElement)entry.getValue(), (String)entry.getKey()));
               }

               return map;
          }
     }
}
