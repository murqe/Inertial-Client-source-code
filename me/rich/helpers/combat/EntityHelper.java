package me.rich.helpers.combat;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.rich.Main;
import me.rich.module.combat.KillAura;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EntityHelper {
      private static final Map serverIpPingCache = new HashMap();
      static EntityLivingBase target;
      static Minecraft mc;

      public static boolean isPassive(Entity e) {
            if (e instanceof EntityWolf && ((EntityWolf)e).isAngry()) {
                  return false;
            } else {
                  return e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid;
            }
      }

      public static void blockByShield(boolean state) {
            if (Minecraft.player.getHeldItemOffhand().getItem() instanceof ItemShield) {
                  mc.gameSettings.keyBindUseItem.pressed = state;
            }

      }

      public static boolean weaponCheck() {
            if (Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemAxe) {
                  return true;
            } else {
                  boolean var0;
                  if (Minecraft.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                        Main var10000 = Main.instance;
                        if (Main.settingsManager.getSettingByName("Weapon Only").getValBoolean()) {
                              var0 = true;
                              return var0;
                        }
                  }

                  var0 = false;
                  return var0;
            }
      }

      public static long getPingToServer(String ip) {
            return (Long)serverIpPingCache.get(ip);
      }

      public static EntityLivingBase searchEntityByName(String name) {
            EntityLivingBase newEntity = null;
            Minecraft.getMinecraft();
            Iterator var2 = mc.world.loadedEntityList.iterator();

            while(var2.hasNext()) {
                  Object o = var2.next();
                  EntityLivingBase en2 = (EntityLivingBase)o;
                  if (!(o instanceof EntityPlayerSP) && !en2.isDead) {
                        Minecraft.getMinecraft();
                        if (!Minecraft.player.canEntityBeSeen(en2) && newEntity == null && en2.getName().equals(name)) {
                              newEntity = en2;
                        }
                  }
            }

            return newEntity;
      }

      public boolean isNPC(EntityPlayer player) {
            try {
                  NetworkPlayerInfo p = Minecraft.player.connection.getPlayerInfo(player.getUniqueID());
                  return !p.getGameType().isSurvivalOrAdventure() && !p.getGameType().isCreative();
            } catch (Exception var3) {
                  return true;
            }
      }

      public static TileEntityChest getClosestChest() {
            TileEntityChest entity = null;
            double maxDist = 4.0D;
            Iterator var3 = mc.world.loadedTileEntityList.iterator();

            while(var3.hasNext()) {
                  TileEntity tileEntity = (TileEntity)var3.next();
                  if (tileEntity instanceof TileEntityChest && Minecraft.player.getDistanceSq(tileEntity.getPos()) < maxDist) {
                        entity = (TileEntityChest)tileEntity;
                        maxDist = Minecraft.player.getDistanceSq(entity.getPos());
                  }
            }

            return entity;
      }

      public static boolean isOnTab(Entity entity) {
            Iterator var1 = mc.getConnection().getPlayerInfoMap().iterator();

            NetworkPlayerInfo info;
            do {
                  if (!var1.hasNext()) {
                        return false;
                  }

                  info = (NetworkPlayerInfo)var1.next();
            } while(!info.getGameProfile().getName().equals(entity.getName()));

            return true;
      }

      public static float getPing(EntityLivingBase target) {
            if (mc.getConnection() != null && target != null) {
                  NetworkPlayerInfo info = mc.getConnection().getPlayerInfo(target.getUniqueID());
                  return info == null ? 0.0F : clamp((double)info.getResponseTime(), 1.0D, 100000.0D);
            } else {
                  return 0.0F;
            }
      }

      public static void block() {
            if (!Minecraft.player.isBlocking() && Minecraft.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemShield) {
                  mc.playerController.processRightClick(Minecraft.player, mc.world, EnumHand.OFF_HAND);
            }

      }

      public static void unblock() {
            if (Minecraft.player.isBlocking() && Minecraft.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemShield) {
                  sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-0.8D, -0.8D, -0.8D), EnumFacing.DOWN));
            }

      }

      public static boolean hasNoArmorPlayer() {
            return ((EntityPlayer)target).getEquipmentInSlot(3) != null && ((EntityPlayer)target).getEquipmentInSlot(2) != null && ((EntityPlayer)target).getEquipmentInSlot(1) != null && ((EntityPlayer)target).getEquipmentInSlot(0) != null;
      }

      public static boolean hasNoMixArmor(Entity e) {
            ItemStack head = ((EntityPlayer)e).getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            ItemStack chest = ((EntityPlayer)e).getItemStackFromSlot(EntityEquipmentSlot.CHEST);
            ItemStack legs = ((EntityPlayer)e).getItemStackFromSlot(EntityEquipmentSlot.LEGS);
            ItemStack feet = ((EntityPlayer)e).getItemStackFromSlot(EntityEquipmentSlot.FEET);
            return head.getItem() != Items.DIAMOND_HELMET || chest.getItem() != Items.DIAMOND_CHESTPLATE || legs.getItem() != Items.DIAMOND_LEGGINGS || feet.getItem() != Items.DIAMOND_BOOTS;
      }

      public static String getEnchantShortName(Enchantment enchantment) {
            if (enchantment == Enchantments.FIRE_PROTECTION) {
                  return "F Prot";
            } else if (enchantment == Enchantments.FEATHER_FALLING) {
                  return "Fea Fa";
            } else if (enchantment == Enchantments.BLAST_PROTECTION) {
                  return "B Prot";
            } else if (enchantment == Enchantments.PROJECTILE_PROTECTION) {
                  return "P Prot";
            } else if (enchantment == Enchantments.AQUA_AFFINITY) {
                  return "Aqua A";
            } else if (enchantment == Enchantments.THORNS) {
                  return "Thorns";
            } else if (enchantment == Enchantments.DEPTH_STRIDER) {
                  return "Depth S";
            } else if (enchantment == Enchantments.FROST_WALKER) {
                  return "Frost W";
            } else if (enchantment == Enchantments.field_190941_k) {
                  return "Curse B";
            } else if (enchantment == Enchantments.SMITE) {
                  return "Smite";
            } else if (enchantment == Enchantments.BANE_OF_ARTHROPODS) {
                  return "Bane A";
            } else if (enchantment == Enchantments.FIRE_ASPECT) {
                  return "Fire A";
            } else if (enchantment == Enchantments.SILK_TOUCH) {
                  return "Silk T";
            } else if (enchantment == Enchantments.POWER) {
                  return "Power";
            } else if (enchantment == Enchantments.PUNCH) {
                  return "Punch";
            } else if (enchantment == Enchantments.FLAME) {
                  return "Flame";
            } else if (enchantment == Enchantments.LUCK_OF_THE_SEA) {
                  return "Luck S";
            } else {
                  return enchantment == Enchantments.field_190940_C ? "Curse V" : enchantment.getName().substring(0, 4);
            }
      }

      public static int getArmorItemsEquipSlot(ItemStack stack, boolean equipmentSlot) {
            if (stack.getUnlocalizedName().contains("helmet")) {
                  return equipmentSlot ? 4 : 5;
            } else if (stack.getUnlocalizedName().contains("chestplate")) {
                  return equipmentSlot ? 3 : 6;
            } else if (stack.getUnlocalizedName().contains("leggings")) {
                  return equipmentSlot ? 2 : 7;
            } else if (stack.getUnlocalizedName().contains("boots")) {
                  return equipmentSlot ? 1 : 8;
            } else {
                  return -1;
            }
      }

      public static void attack(Entity target) {
            sendPacket(new CPacketUseEntity(target));
            Minecraft.player.swingArm(EnumHand.MAIN_HAND);
            Minecraft.player.resetCooldown();
      }

      public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
            if (packet) {
                  Minecraft.player.connection.sendPacket(new CPacketUseEntity(entity));
            } else {
                  mc.playerController.attackEntity(Minecraft.player, entity);
            }

            if (swingArm) {
                  Minecraft.player.swingArm(EnumHand.MAIN_HAND);
            }

      }

      public static boolean isTeam2(EntityPlayer e, EntityLivingBase e2) {
            String localPlayerTeamCode = String.valueOf(e.getDisplayName().getFormattedText().charAt(1)).toLowerCase();
            return localPlayerTeamCode == String.valueOf(e2.getDisplayName().getFormattedText().charAt(1)).toUpperCase();
      }

      public static Vec3d getEntityWorldPosition(Entity entity) {
            double partial = (double)mc.timer.elapsedPartialTicks;
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partial;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partial;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partial;
            return new Vec3d(x, y, z);
      }

      public static Vec3d getEntityRenderPosition(Entity entity) {
            double partial = (double)mc.timer.renderPartialTicks;
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partial - mc.getRenderManager().viewerPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partial - mc.getRenderManager().viewerPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partial - mc.getRenderManager().viewerPosZ;
            return new Vec3d(x, y, z);
      }

      public static double getRotationDifference(double[] rotation) {
            return Math.sqrt(Math.pow(Math.abs(angleDifference((double)(Minecraft.player.rotationYaw % 360.0F), rotation[0])), 2.0D) + Math.pow(Math.abs(angleDifference((double)Minecraft.player.rotationPitch, rotation[1])), 2.0D));
      }

      private static double angleDifference(double a, double b) {
            return ((a - b) % 360.0D + 540.0D) % 360.0D - 180.0D;
      }

      public static boolean isAnimal(Entity entity) {
            return entity instanceof EntityAnimal;
      }

      public static boolean isMonster(Entity entity) {
            return entity instanceof IMob || entity instanceof EntityDragon || entity instanceof EntityGolem;
      }

      public static boolean isNeutral(Entity entity) {
            return entity instanceof EntityBat || entity instanceof EntitySquid || entity instanceof EntityVillager;
      }

      public static boolean isProjectile(Entity entity) {
            return entity instanceof IProjectile || entity instanceof EntityFishHook;
      }

      public static Vec3d getTileEntityRenderPosition(TileEntity entity) {
            double x = (double)entity.getPos().getX() - mc.getRenderManager().viewerPosX;
            double y = (double)entity.getPos().getY() - mc.getRenderManager().viewerPosY;
            double z = (double)entity.getPos().getZ() - mc.getRenderManager().viewerPosZ;
            return new Vec3d(x, y, z);
      }

      private static String isTeam(EntityPlayer player) {
            Matcher m = Pattern.compile("Â§(.).*Â§r").matcher(player.getDisplayName().getFormattedText());
            return m.find() ? m.group(1) : "f";
      }

      public static boolean isTeam(EntityPlayer e, EntityPlayer e2) {
            if (e2.getTeam() != null && e.getTeam() != null) {
                  Character target = e2.getDisplayName().getFormattedText().charAt(1);
                  Character player = e.getDisplayName().getFormattedText().charAt(1);
                  return target.equals(player);
            } else {
                  return true;
            }
      }

      public static void getCurrentPots() {
            EnumHand hand = EnumHand.MAIN_HAND;
            if (Minecraft.player.getHeldItemOffhand().getItem() == Items.SPLASH_POTION) {
                  hand = EnumHand.OFF_HAND;
            } else if (Minecraft.player.getHeldItemMainhand().getItem() != Items.SPLASH_POTION) {
                  for(int i = 0; i < 9; ++i) {
                        if (Minecraft.player.inventory.getStackInSlot(i).getItem() == Items.SPLASH_POTION) {
                              Minecraft.player.inventory.currentItem = i;
                        }
                  }
            }

      }

      public static boolean isPlayOnServer(String s) {
            return mc.isSingleplayer() ? false : mc.getCurrentServerData().serverIP.contains(s);
      }

      public static boolean isBlockUnder() {
            for(int i = (int)(Minecraft.player.posY - 1.0D); i > 0; --i) {
                  BlockPos pos = new BlockPos(Minecraft.player.posX, (double)i, Minecraft.player.posZ);
                  if (!(mc.world.getBlockState(pos).getBlock() instanceof BlockAir)) {
                        return true;
                  }
            }

            return false;
      }

      public static void getCurrentSoups() {
            EnumHand hand = EnumHand.MAIN_HAND;
            if (Minecraft.player.getHeldItemOffhand().getItem() == Items.MUSHROOM_STEW) {
                  hand = EnumHand.OFF_HAND;
            } else if (Minecraft.player.getHeldItemMainhand().getItem() != Items.MUSHROOM_STEW) {
                  for(int i = 0; i < 9; ++i) {
                        if (Minecraft.player.inventory.getStackInSlot(i).getItem() == Items.MUSHROOM_STEW) {
                              Minecraft.player.inventory.currentItem = i;
                        }
                  }
            }

      }

      protected static void sendPacket(Packet packet) {
            mc.getConnection().sendPacket(packet);
      }

      public static boolean isLowHealth(EntityLivingBase entity, EntityLivingBase entityPriority) {
            return entityPriority == null || entity.getHealth() < entityPriority.getHealth();
      }

      public static boolean isClosest(EntityLivingBase entity, EntityLivingBase entityPriority) {
            if (entityPriority == null) {
                  return true;
            } else {
                  return Minecraft.player.getDistanceToEntity(entity) < Minecraft.player.getDistanceToEntity(entityPriority);
            }
      }

      public static boolean isLiving(Entity e) {
            return e instanceof EntityLivingBase;
      }

      public static boolean isFakeLocalPlayer(Entity entity) {
            if (entity == null) {
                  return false;
            } else if (entity.getEntityId() != -100) {
                  return false;
            } else {
                  return Minecraft.player != entity;
            }
      }

      public static int getAxeAtHotbar() {
            for(int i = 0; i < 9; ++i) {
                  ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
                  if (itemStack.getItem() instanceof ItemAxe) {
                        return i;
                  }
            }

            return 1;
      }

      public static int getSwordAtHotbar() {
            for(int i = 0; i < 9; ++i) {
                  ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
                  if (itemStack.getItem() instanceof ItemSword) {
                        return i;
                  }
            }

            return 0;
      }

      public static int getPotAtHotbar() {
            for(int i = 0; i < 9; ++i) {
                  ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
                  if (itemStack.getItem() == Items.MUSHROOM_STEW) {
                        return i;
                  }
            }

            return 1;
      }

      public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
            return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
      }

      public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
            return getInterpolatedAmount(entity, vec.xCoord, vec.yCoord, vec.zCoord);
      }

      public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
            return getInterpolatedAmount(entity, ticks, ticks, ticks);
      }

      public static boolean isNeutralMob(Entity entity) {
            return entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman;
      }

      public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
            return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)).add(getInterpolatedAmount(entity, (double)ticks));
      }

      public static boolean isAlive(Entity entity) {
            return isLiving(entity) && !entity.isDead && ((EntityLivingBase)entity).getHealth() > 0.0F;
      }

      public static boolean isDead(Entity entity) {
            return !isAlive(entity);
      }

      public static Vec3d getInterpolatedRenderPos(Entity entity, float ticks) {
            return getInterpolatedPos(entity, ticks).subtract(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
      }

      public static float getHealth(Entity entity) {
            if (isLiving(entity)) {
                  EntityLivingBase livingBase = (EntityLivingBase)entity;
                  return livingBase.getHealth() + livingBase.getAbsorptionAmount();
            } else {
                  return 0.0F;
            }
      }

      public static boolean isInWater(Entity entity) {
            if (entity == null) {
                  return false;
            } else {
                  double y = entity.posY + 0.01D;

                  for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
                        for(int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                              BlockPos pos = new BlockPos(x, (int)y, z);
                              if (mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                                    return true;
                              }
                        }
                  }

                  return false;
            }
      }

      public static boolean isDrivenByPlayer(Entity entityIn) {
            if (Minecraft.player == null) {
                  return false;
            } else {
                  return entityIn == null ? false : entityIn.equals(Minecraft.player.getRidingEntity());
            }
      }

      public static boolean isAboveWater(Entity entity) {
            return isAboveWater(entity, false);
      }

      public static boolean isAboveWater(Entity entity, boolean packet) {
            if (entity == null) {
                  return false;
            } else {
                  double y = entity.posY - (packet ? 0.03D : (isPlayer(entity) ? 0.2D : 0.5D));

                  for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); ++x) {
                        for(int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); ++z) {
                              BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                              if (mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                                    return true;
                              }
                        }
                  }

                  return false;
            }
      }

      public static boolean canEntityFeetBeSeen(Entity entityIn) {
            return mc.world.rayTraceBlocks(new Vec3d(Minecraft.player.posX, Minecraft.player.posX + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ), new Vec3d(entityIn.posX, entityIn.posY, entityIn.posZ), false, true, false) == null;
      }

      public static float[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
            double dirx = me.posX - px;
            double diry = me.posY - py;
            double dirz = me.posZ - pz;
            double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
            double pitch = Math.asin(diry / len);
            double yaw = Math.atan2(dirz / len, dirx / len);
            pitch = pitch * 180.0D / 3.141592653589793D;
            yaw = yaw * 180.0D / 3.141592653589793D;
            return new float[]{(float)(yaw += 90.0D), (float)pitch};
      }

      public static boolean isPlayer(Entity entity) {
            return entity instanceof EntityPlayer;
      }

      public static double getRelativeX(float yaw) {
            return (double)MathHelper.sin(-yaw * 0.017453292F);
      }

      public static double getRelativeZ(float yaw) {
            return (double)MathHelper.cos(yaw * 0.017453292F);
      }

      public static double GetDistance(double p_X, double p_Y, double p_Z, double x, double y, double z) {
            double d0 = p_X - x;
            double d1 = p_Y - y;
            double d2 = p_Z - z;
            return (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      }

      public static double GetDistanceOfEntityToBlock(Entity p_Entity, BlockPos p_Pos) {
            return GetDistance(p_Entity.posX, p_Entity.posY, p_Entity.posZ, (double)p_Pos.getX(), (double)p_Pos.getY(), (double)p_Pos.getZ());
      }

      public static float clamp(double val, double min, double max) {
            if (val <= min) {
                  val = min;
            }

            if (val >= max) {
                  val = max;
            }

            return (float)val;
      }

      public static boolean isPassiveMob(Entity e) {
            return e instanceof EntityAnimal || e instanceof EntityAgeable || e instanceof EntityTameable || e instanceof EntityAmbientCreature || e instanceof EntitySquid;
      }

      public static int GetRecursiveItemSlot(Item input) {
            if (Minecraft.player == null) {
                  return 0;
            } else {
                  for(int i = Minecraft.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
                        if (i != 0 && i != 5 && i != 6 && i != 7 && i != 8) {
                              ItemStack s = (ItemStack)Minecraft.player.inventoryContainer.getInventory().get(i);
                              if (!s.func_190926_b() && s.getItem() == input) {
                                    return i;
                              }
                        }
                  }

                  return -1;
            }
      }

      public static int GetItemSlotNotHotbar(Item input) {
            if (Minecraft.player == null) {
                  return 0;
            } else {
                  for(int i = 9; i < 36; ++i) {
                        Item item = Minecraft.player.inventory.getStackInSlot(i).getItem();
                        if (item == input) {
                              return i;
                        }
                  }

                  return -1;
            }
      }

      public static float GetHealthWithAbsorption() {
            return Minecraft.player.getHealth() + Minecraft.player.getAbsorptionAmount();
      }

      public static int GetItemSlot(Item input) {
            if (Minecraft.player == null) {
                  return 0;
            } else {
                  for(int i = 0; i < Minecraft.player.inventoryContainer.getInventory().size(); ++i) {
                        if (i != 0 && i != 5 && i != 6 && i != 7 && i != 8) {
                              ItemStack s = (ItemStack)Minecraft.player.inventoryContainer.getInventory().get(i);
                              if (!s.func_190926_b() && s.getItem() == input) {
                                    return i;
                              }
                        }
                  }

                  return -1;
            }
      }

      public static boolean isPlayerMovingKeybind() {
            return mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown();
      }

      public static boolean isPlayerMovingMomentum() {
            if (Minecraft.player.moveForward > 0.0F) {
                  return true;
            } else if (Minecraft.player.moveStrafing > 0.0F) {
                  return true;
            } else if (Minecraft.player.moveForward < 0.0F) {
                  return true;
            } else {
                  return Minecraft.player.moveStrafing < 0.0F;
            }
      }

      public static boolean isPlayerMovingLegit() {
            return mc.gameSettings.keyBindForward.isKeyDown();
      }

      public static void getPlayerSpeed() {
            new DecimalFormat("#.#");
            double deltaX = Minecraft.player.posX - Minecraft.player.prevPosX;
            double deltaY = Minecraft.player.posY - Minecraft.player.prevPosY;
      }

      public static int getPing() {
            if (mc.world != null && Minecraft.player != null && Minecraft.player.getUniqueID() != null) {
                  NetworkPlayerInfo debugInfo = mc.getConnection().getPlayerInfo(Minecraft.player.getUniqueID());
                  return debugInfo == null ? 0 : debugInfo.getResponseTime();
            } else {
                  return 0;
            }
      }

      public static float[] getFacePosRemote(Vec3d src, Vec3d dest) {
            double diffX = dest.xCoord - src.xCoord;
            double diffY = dest.yCoord - src.yCoord;
            double diffZ = dest.zCoord - src.zCoord;
            double dist = (double)MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
            float yaw = MathHelper.wrapAngleTo180_float((float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D)) - 90.0F;
            float pitch = MathHelper.wrapAngleTo180_float((float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D)));
            float[] arrf = new float[]{RotationHelper.fixRotation(Minecraft.player.rotationYaw, yaw), RotationHelper.fixRotation(Minecraft.player.rotationPitch, pitch)};
            return arrf;
      }

      public static float[] getFacePosEntityRemote(EntityLivingBase facing, Entity en) {
            if (en == null) {
                  return new float[]{facing.rotationYaw, facing.rotationPitch};
            } else {
                  new Vec3d(facing.posX, facing.posY + (double)en.getEyeHeight(), facing.posZ);
                  new Vec3d(en.posX, en.posY + (double)en.getEyeHeight(), en.posZ);
                  return getFacePosRemote(new Vec3d(facing.posX, facing.posY + (double)en.getEyeHeight(), facing.posZ), new Vec3d(en.posX, en.posY + (double)en.getEyeHeight(), en.posZ));
            }
      }

      public static EntityLivingBase getClosestEntityToEntity(float range, Entity ent) {
            EntityLivingBase closestEntity = null;
            float mindistance = range;
            Iterator var4 = mc.world.loadedEntityList.iterator();

            while(var4.hasNext()) {
                  Object o = var4.next();
                  EntityLivingBase en;
                  if (isNotItem(o) && !ent.isEntityEqual((EntityLivingBase)o) && ent.getDistanceToEntity(en = (EntityLivingBase)o) < mindistance) {
                        mindistance = ent.getDistanceToEntity(en);
                        closestEntity = en;
                  }
            }

            return closestEntity;
      }

      public static boolean isNotItem(Object o) {
            return o instanceof EntityLivingBase;
      }

      static {
            target = KillAura.target;
            mc = Minecraft.getMinecraft();
      }
}
