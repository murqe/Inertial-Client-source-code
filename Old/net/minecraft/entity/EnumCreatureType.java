package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;

public enum EnumCreatureType {
      MONSTER(IMob.class, 70, Material.AIR, false, false),
      CREATURE(EntityAnimal.class, 10, Material.AIR, true, true),
      AMBIENT(EntityAmbientCreature.class, 15, Material.AIR, true, false),
      WATER_CREATURE(EntityWaterMob.class, 5, Material.WATER, true, false);

      private final Class creatureClass;
      private final int maxNumberOfCreature;
      private final Material creatureMaterial;
      private final boolean isPeacefulCreature;
      private final boolean isAnimal;

      private EnumCreatureType(Class creatureClassIn, int maxNumberOfCreatureIn, Material creatureMaterialIn, boolean isPeacefulCreatureIn, boolean isAnimalIn) {
            this.creatureClass = creatureClassIn;
            this.maxNumberOfCreature = maxNumberOfCreatureIn;
            this.creatureMaterial = creatureMaterialIn;
            this.isPeacefulCreature = isPeacefulCreatureIn;
            this.isAnimal = isAnimalIn;
      }

      public Class getCreatureClass() {
            return this.creatureClass;
      }

      public int getMaxNumberOfCreature() {
            return this.maxNumberOfCreature;
      }

      public boolean getPeacefulCreature() {
            return this.isPeacefulCreature;
      }

      public boolean getAnimal() {
            return this.isAnimal;
      }
}
