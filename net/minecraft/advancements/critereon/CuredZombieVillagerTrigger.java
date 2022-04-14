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
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class CuredZombieVillagerTrigger implements ICriterionTrigger {
      private static final ResourceLocation field_192186_a = new ResourceLocation("cured_zombie_villager");
      private final Map field_192187_b = Maps.newHashMap();

      public ResourceLocation func_192163_a() {
            return field_192186_a;
      }

      public void func_192165_a(PlayerAdvancements p_192165_1_, ICriterionTrigger.Listener p_192165_2_) {
            CuredZombieVillagerTrigger.Listeners curedzombievillagertrigger$listeners = (CuredZombieVillagerTrigger.Listeners)this.field_192187_b.get(p_192165_1_);
            if (curedzombievillagertrigger$listeners == null) {
                  curedzombievillagertrigger$listeners = new CuredZombieVillagerTrigger.Listeners(p_192165_1_);
                  this.field_192187_b.put(p_192165_1_, curedzombievillagertrigger$listeners);
            }

            curedzombievillagertrigger$listeners.func_192360_a(p_192165_2_);
      }

      public void func_192164_b(PlayerAdvancements p_192164_1_, ICriterionTrigger.Listener p_192164_2_) {
            CuredZombieVillagerTrigger.Listeners curedzombievillagertrigger$listeners = (CuredZombieVillagerTrigger.Listeners)this.field_192187_b.get(p_192164_1_);
            if (curedzombievillagertrigger$listeners != null) {
                  curedzombievillagertrigger$listeners.func_192358_b(p_192164_2_);
                  if (curedzombievillagertrigger$listeners.func_192359_a()) {
                        this.field_192187_b.remove(p_192164_1_);
                  }
            }

      }

      public void func_192167_a(PlayerAdvancements p_192167_1_) {
            this.field_192187_b.remove(p_192167_1_);
      }

      public CuredZombieVillagerTrigger.Instance func_192166_a(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
            EntityPredicate entitypredicate = EntityPredicate.func_192481_a(p_192166_1_.get("zombie"));
            EntityPredicate entitypredicate1 = EntityPredicate.func_192481_a(p_192166_1_.get("villager"));
            return new CuredZombieVillagerTrigger.Instance(entitypredicate, entitypredicate1);
      }

      public void func_192183_a(EntityPlayerMP p_192183_1_, EntityZombie p_192183_2_, EntityVillager p_192183_3_) {
            CuredZombieVillagerTrigger.Listeners curedzombievillagertrigger$listeners = (CuredZombieVillagerTrigger.Listeners)this.field_192187_b.get(p_192183_1_.func_192039_O());
            if (curedzombievillagertrigger$listeners != null) {
                  curedzombievillagertrigger$listeners.func_192361_a(p_192183_1_, p_192183_2_, p_192183_3_);
            }

      }

      static class Listeners {
            private final PlayerAdvancements field_192362_a;
            private final Set field_192363_b = Sets.newHashSet();

            public Listeners(PlayerAdvancements p_i47460_1_) {
                  this.field_192362_a = p_i47460_1_;
            }

            public boolean func_192359_a() {
                  return this.field_192363_b.isEmpty();
            }

            public void func_192360_a(ICriterionTrigger.Listener p_192360_1_) {
                  this.field_192363_b.add(p_192360_1_);
            }

            public void func_192358_b(ICriterionTrigger.Listener p_192358_1_) {
                  this.field_192363_b.remove(p_192358_1_);
            }

            public void func_192361_a(EntityPlayerMP p_192361_1_, EntityZombie p_192361_2_, EntityVillager p_192361_3_) {
                  List list = null;
                  Iterator var5 = this.field_192363_b.iterator();

                  ICriterionTrigger.Listener listener1;
                  while(var5.hasNext()) {
                        listener1 = (ICriterionTrigger.Listener)var5.next();
                        if (((CuredZombieVillagerTrigger.Instance)listener1.func_192158_a()).func_192254_a(p_192361_1_, p_192361_2_, p_192361_3_)) {
                              if (list == null) {
                                    list = Lists.newArrayList();
                              }

                              list.add(listener1);
                        }
                  }

                  if (list != null) {
                        var5 = list.iterator();

                        while(var5.hasNext()) {
                              listener1 = (ICriterionTrigger.Listener)var5.next();
                              listener1.func_192159_a(this.field_192362_a);
                        }
                  }

            }
      }

      public static class Instance extends AbstractCriterionInstance {
            private final EntityPredicate field_192255_a;
            private final EntityPredicate field_192256_b;

            public Instance(EntityPredicate p_i47459_1_, EntityPredicate p_i47459_2_) {
                  super(CuredZombieVillagerTrigger.field_192186_a);
                  this.field_192255_a = p_i47459_1_;
                  this.field_192256_b = p_i47459_2_;
            }

            public boolean func_192254_a(EntityPlayerMP p_192254_1_, EntityZombie p_192254_2_, EntityVillager p_192254_3_) {
                  return !this.field_192255_a.func_192482_a(p_192254_1_, p_192254_2_) ? false : this.field_192256_b.func_192482_a(p_192254_1_, p_192254_3_);
            }
      }
}
