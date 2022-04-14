package net.minecraft.advancements.critereon;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class TameAnimalTrigger implements ICriterionTrigger {
      private static final ResourceLocation field_193179_a = new ResourceLocation("tame_animal");
      private final Map field_193180_b = Maps.newHashMap();

      public ResourceLocation func_192163_a() {
            return field_193179_a;
      }

      public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener p_192165_2_) {
            TameAnimalTrigger.Listeners tameanimaltrigger$listeners = (TameAnimalTrigger.Listeners)this.field_193180_b.get(p_192165_1_);
            if (tameanimaltrigger$listeners == null) {
                  tameanimaltrigger$listeners = new TameAnimalTrigger.Listeners(p_192165_1_);
                  this.field_193180_b.put(p_192165_1_, tameanimaltrigger$listeners);
            }

            tameanimaltrigger$listeners.func_193496_a(p_192165_2_);
      }

      public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener p_192164_2_) {
            TameAnimalTrigger.Listeners tameanimaltrigger$listeners = (TameAnimalTrigger.Listeners)this.field_193180_b.get(p_192164_1_);
            if (tameanimaltrigger$listeners != null) {
                  tameanimaltrigger$listeners.func_193494_b(p_192164_2_);
                  if (tameanimaltrigger$listeners.func_193495_a()) {
                        this.field_193180_b.remove(p_192164_1_);
                  }
            }

      }

      public void func_192167_a(PlayerAdvancements p_192167_1_) {
            this.field_193180_b.remove(p_192167_1_);
      }

      public TameAnimalTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
            EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("entity"));
            return new TameAnimalTrigger.Instance(entitypredicate);
      }

      public void func_193178_a(EntityPlayerMP p_193178_1_, EntityAnimal p_193178_2_) {
            TameAnimalTrigger.Listeners tameanimaltrigger$listeners = (TameAnimalTrigger.Listeners)this.field_193180_b.get(p_193178_1_.func_192039_O());
            if (tameanimaltrigger$listeners != null) {
                  tameanimaltrigger$listeners.func_193497_a(p_193178_1_, p_193178_2_);
            }

      }

      static class Listeners {
            private final PlayerAdvancements field_193498_a;
            private final Set field_193499_b = Sets.newHashSet();

            public Listeners(PlayerAdvancements p_i47514_1_) {
                  this.field_193498_a = p_i47514_1_;
            }

            public boolean func_193495_a() {
                  return this.field_193499_b.isEmpty();
            }

            public void func_193496_a(ICriterionTrigger.Listener p_193496_1_) {
                  this.field_193499_b.add(p_193496_1_);
            }

            public void func_193494_b(ICriterionTrigger.Listener p_193494_1_) {
                  this.field_193499_b.remove(p_193494_1_);
            }

            public void func_193497_a(EntityPlayerMP p_193497_1_, EntityAnimal p_193497_2_) {
                  List list = null;
                  Iterator var4 = this.field_193499_b.iterator();

                  ICriterionTrigger.Listener listener1;
                  while(var4.hasNext()) {
                        listener1 = (ICriterionTrigger.Listener)var4.next();
                        if (((TameAnimalTrigger.Instance)listener1.func_192158_a()).func_193216_a(p_193497_1_, p_193497_2_)) {
                              if (list == null) {
                                    list = Lists.newArrayList();
                              }

                              list.add(listener1);
                        }
                  }

                  if (list != null) {
                        var4 = list.iterator();

                        while(var4.hasNext()) {
                              listener1 = (ICriterionTrigger.Listener)var4.next();
                              listener1.func_192159_a(this.field_193498_a);
                        }
                  }

            }
      }

      public static class Instance extends AbstractCriterionInstance {
            private final EntityPredicate field_193217_a;

            public Instance(EntityPredicate p_i47513_1_) {
                  super(TameAnimalTrigger.field_193179_a);
                  this.field_193217_a = p_i47513_1_;
            }

            public boolean func_193216_a(EntityPlayerMP p_193216_1_, EntityAnimal p_193216_2_) {
                  return this.field_193217_a.func_192482_a(p_193216_1_, p_193216_2_);
            }
      }
}
