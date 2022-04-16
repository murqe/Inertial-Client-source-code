package me.rich.module.player;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.helpers.combat.RotationHelper;
import me.rich.helpers.combat.RotationSpoofer;
import me.rich.helpers.other.RandomHelper;
import me.rich.helpers.world.InventoryHelper;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Feature {
      public static final List invalidBlocks;
      private final List validBlocks;
      private final BlockPos[] blockPositions;
      private float[] angles;
      public static Scaffold.BlockData data;
      private int slot;
      public static boolean isSneaking;
      private static double yDif;
      private TimerHelper time;
      private TimerHelper timer;
      private double speed;
      private int item;
      private boolean canPlace;
      private double pitch;
      Object localObject;
      private int itspoof;
      public static EntityPlayerSP entity;
      private Setting jump;
      private Setting tower;
      private Setting packetSneak;
      private Setting swing;
      private Setting delay1;
      private Setting delay2;
      private Setting chance3;
      public static Setting cast;

      public Scaffold() {
            super("Scaffold", 0, Category.PLAYER);
            this.validBlocks = Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.FLOWING_WATER, Blocks.LAVA, Blocks.FLOWING_LAVA);
            this.blockPositions = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 0, 0)};
            this.angles = new float[2];
            this.time = new TimerHelper();
            this.timer = new TimerHelper();
            ArrayList rots = new ArrayList();
            rots.add("Matrix");
            rots.add("None");
            Main.settingsManager.rSetting(new Setting("BlockRotation Mode", this, "Matrix", rots));
            ArrayList motion = new ArrayList();
            motion.add("Zitter");
            motion.add("MoonWalk");
            Main.settingsManager.rSetting(new Setting("Jitter Mode", this, "Zitter", motion));
            this.chance3 = new Setting("PlaceChance", this, 100.0D, 0.0D, 100.0D, false);
            Main.settingsManager.rSetting(this.chance3);
            this.delay1 = new Setting("PlaceDelay", this, 55.0D, 0.0D, 300.0D, false);
            Main.settingsManager.rSetting(this.delay1);
            this.delay2 = new Setting("PlaceDelayRandom", this, 0.0D, 0.0D, 1000.0D, false);
            Main.settingsManager.rSetting(this.delay2);
            Main.settingsManager.rSetting(new Setting("BlockRayCast", this, false));
            cast = new Setting("BlockCastReduce", this, 0.9D, 0.4D, 1.5D, false);
            Main.settingsManager.rSetting(cast);
            Main.settingsManager.rSetting(new Setting("ScaffoldSpeed", this, 0.6D, 0.05D, 1.2D, false));
            Main.settingsManager.rSetting(new Setting("VaildCheck", this, true));
            this.tower = new Setting("Tower", this, true);
            Main.settingsManager.rSetting(this.tower);
            this.jump = new Setting("Jump", this, false);
            Main.settingsManager.rSetting(this.jump);
            this.packetSneak = new Setting("PacketSneak", this, true);
            Main.settingsManager.rSetting(this.packetSneak);
            this.swing = new Setting("SwingHand", this, false);
            Main.settingsManager.rSetting(this.swing);
            Main.settingsManager.rSetting(new Setting("AutoMotionStop", this, true));
            Main.settingsManager.rSetting(new Setting("SprintOFF", this, true));
            Main.settingsManager.rSetting(new Setting("BlockSafe", this, true));
            Main.settingsManager.rSetting(new Setting("MotionJitter", this, false));
            Main.settingsManager.rSetting(new Setting("YawRandom", this, 1.5D, 0.1D, 5.0D, false));
            Main.settingsManager.rSetting(new Setting("PitchRandom", this, 1.5D, 0.1D, 5.0D, false));
      }

      public void onEnable() {
            NotificationPublisher.queue("Module", "Scaffold was Enabled!", NotificationType.INFO);
            if (!InventoryHelper.doesHotbarHaveBlock()) {
                  NotificationPublisher.queue("Scaffold", "You have no blocks in your hotbar!", NotificationType.INFO);
            }

            this.angles[0] = 999.0F;
            this.angles[1] = 999.0F;
            if (Main.settingsManager.getSettingByName("AutoMotionStop").getValBoolean()) {
                  EntityPlayerSP var10000 = Minecraft.player;
                  var10000.motionX *= -1.15D;
                  var10000 = Minecraft.player;
                  var10000.motionZ *= -1.15D;
            }

            this.slot = Minecraft.player.inventory.currentItem;
            data = null;
            super.onEnable();
      }

      @EventTarget
      public void onUpdate(EventPreMotionUpdate eventUpdate) {
            this.item = this.findBlock(Minecraft.player.inventoryContainer);
            EntityPlayerSP var10000;
            if (InventoryHelper.doesHotbarHaveBlock()) {
                  if (this.jump.getValBoolean() && (!this.jump.getValBoolean() || !mc.gameSettings.keyBindJump.isKeyDown()) && Minecraft.player.onGround) {
                        mc.gameSettings.keyBindJump.pressed = false;
                        Minecraft.player.jump();
                  }

                  if (Main.settingsManager.getSettingByName("AutoMotionStop").getValBoolean() && Minecraft.player.isSprinting() && mc.gameSettings.keyBindForward.isKeyDown() && Main.settingsManager.getSettingByName("SprintOFF").getValBoolean() && this.jump.getValBoolean()) {
                        var10000 = Minecraft.player;
                        var10000.motionX *= -1.15D;
                        var10000 = Minecraft.player;
                        var10000.motionZ *= -1.15D;
                  }

                  var10000 = Minecraft.player;
                  var10000.motionX /= 1.0499999523162842D;
                  var10000 = Minecraft.player;
                  var10000.motionZ /= 1.0499999523162842D;
            }

            String mo = Main.settingsManager.getSettingByName("BlockRotation Mode").getValString();
            this.setModuleName(mo);
            yDif = 1.0D;

            double dir;
            for(dir = Minecraft.player.posY - 1.0D; dir > 0.0D; --dir) {
                  BlockPos blockPos = isSneaking ? (new BlockPos(Minecraft.player)).add(0.0D, -1.0D, 0.0D).down() : (new BlockPos(Minecraft.player)).add(0.0D, -1.0D, 0.0D);
                  Scaffold.BlockData newData = this.getBlockData2(blockPos);
                  if (newData != null) {
                        yDif = Minecraft.player.posY - dir;
                        if (yDif <= 7.0D) {
                              data = newData;
                        }
                  }
            }

            if (!this.jump.getValBoolean()) {
                  if (!Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                  }

                  var10000 = Minecraft.player;
                  var10000.motionX *= Main.settingsManager.getSettingByName("ScaffoldSpeed").getValDouble() / 1.37D;
                  if (!Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                  }

                  var10000 = Minecraft.player;
                  var10000.motionZ *= Main.settingsManager.getSettingByName("ScaffoldSpeed").getValDouble();
            }

            if (Main.settingsManager.getSettingByName("MotionJitter").getValBoolean()) {
                  mo = Main.settingsManager.getSettingByName("Jitter Mode").getValString();
                  if (mo.equalsIgnoreCase("Zitter")) {
                        dir = RandomHelper.getRandomDouble(0.008D, -0.008D);
                        var10000 = Minecraft.player;
                        var10000.motionX += dir;
                        var10000 = Minecraft.player;
                        var10000.motionZ -= dir;
                  }

                  if (mo.equalsIgnoreCase("SlowForward") && mc.gameSettings.keyBindForward.isKeyDown()) {
                        dir = Math.toRadians((double)Minecraft.player.rotationYaw);
                        var10000 = Minecraft.player;
                        var10000.motionX += -Math.sin(dir) * -0.05D;
                        var10000 = Minecraft.player;
                        var10000.motionZ += Math.cos(dir) * -0.05D;
                  }

                  if (mo.equalsIgnoreCase("MoonWalk")) {
                        if (!Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        }

                        var10000 = Minecraft.player;
                        var10000.motionX *= Main.settingsManager.getSettingByName("ScaffoldSpeed").getValDouble() / 1.37D;
                        if (!Minecraft.player.isPotionActive(MobEffects.SPEED) && Minecraft.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
                        }

                        var10000 = Minecraft.player;
                        var10000.motionZ *= Main.settingsManager.getSettingByName("ScaffoldSpeed").getValDouble() / 1.37D;
                        if (Minecraft.player.ticksExisted % 7 == 0) {
                              dir = Math.toRadians((double)(Minecraft.player.rotationYaw - 90.0F));
                              var10000 = Minecraft.player;
                              var10000.motionX += -Math.sin(dir) * 0.03D;
                              var10000 = Minecraft.player;
                              var10000.motionZ += Math.cos(dir) * 0.03D;
                        }

                        if (Minecraft.player.ticksExisted % 11 == 0) {
                              dir = Math.toRadians((double)(Minecraft.player.rotationYaw + 90.0F));
                              var10000 = Minecraft.player;
                              var10000.motionX += -Math.sin(dir) * 0.003D;
                              var10000 = Minecraft.player;
                              var10000.motionZ += Math.cos(dir) * 0.003D;
                              double dir1 = Math.toRadians((double)(Minecraft.player.rotationYaw + 90.0F));
                              EntityPlayerSP var10001 = Minecraft.player;
                              Minecraft.player.setPosition(var10001.posX += -Math.sin(dir1) * 0.072D, Minecraft.player.posY, Minecraft.player.posZ);
                              EntityPlayerSP var10003 = Minecraft.player;
                              Minecraft.player.setPosition(Minecraft.player.posX, Minecraft.player.posY, var10003.posZ += Math.cos(dir1) * 0.072D);
                        }
                  }
            }

            if (Main.settingsManager.getSettingByName("SprintOFF").getValBoolean() && this.isToggled()) {
                  Minecraft.player.setSprinting(false);
            }

            if (data != null) {
                  if (this.slot != -1) {
                        BlockPos pos = data.pos;
                        EnumFacing facing = data.face;
                        Block block = mc.world.getBlockState(pos.offset(data.face)).getBlock();
                        Vec3d hitVec = this.getVec3(data);
                        String mod = Main.settingsManager.getSettingByName("BlockRotation Mode").getValString();
                        if (mod.equalsIgnoreCase("Matrix")) {
                              float yawRand = (float)Main.settingsManager.getSettingByName("YawRandom").getValDouble();
                              float pitchRand = (float)Main.settingsManager.getSettingByName("PitchRandom").getValDouble();
                              float[] rots = RotationHelper.getNeededFacing(hitVec);
                              float yawGCD = rots[0] + (float)RandomHelper.getRandomDouble((double)(-yawRand), (double)yawRand);
                              float pitchGCD = rots[1] + (float)RandomHelper.getRandomDouble((double)(-pitchRand), (double)pitchRand);
                              eventUpdate.setYaw(yawGCD);
                              eventUpdate.setPitch(pitchGCD);
                              Minecraft.player.renderYawOffset = yawGCD;
                              Minecraft.player.rotationYawHead = yawGCD;
                              Minecraft.player.rotationPitchHead = pitchGCD;
                        }

                        if (this.validBlocks.contains(block)) {
                              if (!this.isBlockUnder(yDif)) {
                                    if (mc.gameSettings.keyBindJump.isKeyDown()) {
                                          if (this.tower.getValBoolean()) {
                                                if (Minecraft.player.onGround) {
                                                      Minecraft.player.jump();
                                                }
                                          }
                                    }
                              }
                        }
                  }
            }
      }

      @EventTarget
      public void onSafeWalk(EventSafeWalk event) {
            if (Main.settingsManager.getSettingByName("BlockSafe").getValBoolean() && !isSneaking) {
                  event.setCancelled(Minecraft.player.onGround);
            }

            this.localObject = new BlockPos(Minecraft.player.posX, Minecraft.player.posY - 0.5D, Minecraft.player.posZ);
            if (InventoryHelper.doesHotbarHaveBlock()) {
                  int slot = -1;
                  boolean blockCount = false;

                  for(int i = 0; i < 9; ++i) {
                        ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
                        if (itemStack != null) {
                              int stackSize = itemStack.stackSize;
                              if (this.isValidItem(itemStack.getItem())) {
                                    slot = i;
                              }
                        }
                  }

                  if (data != null && slot != -1) {
                        BlockPos pos = data.pos;
                        EnumFacing facing = data.face;
                        mc.world.getBlockState(pos.offset(data.face)).getBlock();
                        Vec3d hitVec = this.getVec3(data);
                        if (this.itspoof != -1) {
                              this.ItemSpoof();
                        }

                        if (this.time.check(this.delay1.getValFloat() + (float)RandomHelper.getRandomDouble(0.0D, (double)this.delay2.getValFloat()))) {
                              boolean look = RotationSpoofer.isLookingAt1(hitVec);
                              if (!look && Main.settingsManager.getSettingByName("BlockRayCast").getValBoolean()) {
                                    return;
                              }

                              if (!this.jump.getValBoolean() && this.packetSneak.getValBoolean() && this.allowPlacing() && this.isToggled()) {
                                    Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.START_SNEAKING));
                              }

                              if (Main.settingsManager.getSettingByName("VaildCheck").getValBoolean() && (!this.validateDataRange(data) || !this.validateReplaceable(data))) {
                                    return;
                              }

                              if (RandomHelper.getRandomDouble(0.0D, 100.0D) <= this.chance3.getValDouble()) {
                                    mc.playerController.processRightClickBlock(Minecraft.player, mc.world, pos, data.face, hitVec, EnumHand.MAIN_HAND);
                                    if (this.swing.getValBoolean()) {
                                          Minecraft.player.swingArm(EnumHand.MAIN_HAND);
                                    } else {
                                          mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                                    }

                                    this.time.reset();
                              }
                        }

                        if (!this.jump.getValBoolean() && this.packetSneak.getValBoolean() && this.allowPlacing() && this.isToggled()) {
                              Minecraft.player.connection.sendPacket(new CPacketEntityAction(Minecraft.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                  }
            }

      }

      private boolean validateDataRange(Scaffold.BlockData data) {
            Vec3d hitVec = this.getVec3(data);
            Minecraft var10001 = mc;
            double x = hitVec.xCoord - Minecraft.player.posX;
            var10001 = mc;
            Minecraft var10002 = mc;
            double y = hitVec.yCoord - (Minecraft.player.posY + (double)Minecraft.player.getEyeHeight());
            double var10000 = x * x + y * y;
            var10002 = mc;
            double z;
            return Math.sqrt(var10000 + (z = hitVec.zCoord - Minecraft.player.posZ) * z) <= 4.0D;
      }

      private boolean validateReplaceable(Scaffold.BlockData data) {
            BlockPos pos = data.pos.offset(data.face);
            WorldClient world = mc.world;
            return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
      }

      private boolean allowPlacing() {
            BlockPos localBlockPos1 = new BlockPos(Minecraft.player.posX - 0.024D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ - 0.024D);
            BlockPos localBlockPos2 = new BlockPos(Minecraft.player.posX - 0.024D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ + 0.024D);
            BlockPos localBlockPos3 = new BlockPos(Minecraft.player.posX + 0.024D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ + 0.024D);
            BlockPos localBlockPos4 = new BlockPos(Minecraft.player.posX + 0.024D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ - 0.024D);
            return mc.world.getBlockState(localBlockPos1).getBlock() == Blocks.AIR && mc.world.getBlockState(localBlockPos2).getBlock() == Blocks.AIR && mc.world.getBlockState(localBlockPos3).getBlock() == Blocks.AIR && mc.world.getBlockState(localBlockPos4).getBlock() == Blocks.AIR;
      }

      private boolean allowRotation() {
            double d = 0.1D;
            BlockPos localBlockPos1 = new BlockPos(Minecraft.player.posX - 0.1D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ - 0.1D);
            BlockPos localBlockPos2 = new BlockPos(Minecraft.player.posX - 0.1D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ + 0.1D);
            BlockPos localBlockPos3 = new BlockPos(Minecraft.player.posX + 0.1D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ + 0.1D);
            BlockPos localBlockPos4 = new BlockPos(Minecraft.player.posX + 0.1D, Minecraft.player.posY - 0.5D, Minecraft.player.posZ - 0.1D);
            return mc.world.getBlockState(localBlockPos1).getBlock() == Blocks.AIR && mc.world.getBlockState(localBlockPos2).getBlock() == Blocks.AIR && mc.world.getBlockState(localBlockPos3).getBlock() == Blocks.AIR && mc.world.getBlockState(localBlockPos4).getBlock() == Blocks.AIR;
      }

      static float getSensitivityMultiplier2() {
            float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            return f * f * f * 8.0F * 0.15F;
      }

      private void ItemSpoof() {
            if (this.itspoof != -1 && this.itspoof != Minecraft.player.inventory.currentItem) {
            }

            ItemStack is = new ItemStack(Item.getItemById(261));

            try {
                  for(int i = 36; i < 45; ++i) {
                        int slot1 = i - 36;
                        if (!Container.canAddItemToSlot(Minecraft.player.inventoryContainer.getSlot(i), is, true) && Minecraft.player.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock && Minecraft.player.inventoryContainer.getSlot(i).getStack() != null && this.isValidItem(Minecraft.player.inventoryContainer.getSlot(i).getStack().getItem()) && Minecraft.player.inventoryContainer.getSlot(i).getStack().stackSize != 0) {
                              if (Minecraft.player.inventory.currentItem != slot1) {
                                    if (this.itspoof != slot1) {
                                          CPacketHeldItemChange p = new CPacketHeldItemChange(slot1);
                                          this.itspoof = slot1;
                                          mc.getConnection().sendPacket(p);
                                          Minecraft.player.inventory.currentItem = slot1;
                                          mc.playerController.updateController();
                                    } else {
                                          Minecraft.player.inventory.currentItem = slot1;
                                          mc.playerController.updateController();
                                    }
                              } else {
                                    Minecraft.player.inventory.currentItem = slot1;
                                    mc.playerController.updateController();
                              }
                              break;
                        }
                  }
            } catch (Exception var5) {
            }

      }

      float to1801(float ang) {
            float value = ang % 360.0F;
            if (value >= 180.0F) {
                  value -= 360.0F;
            }

            if (value < -180.0F) {
                  value += 360.0F;
            }

            return value;
      }

      private int getBlockCount() {
            int blockCount = 0;

            for(int i = 0; i < 45; ++i) {
                  if (Minecraft.player.inventoryContainer.getSlot(i).getHasStack()) {
                        ItemStack is = Minecraft.player.inventoryContainer.getSlot(i).getStack();
                        Item item = is.getItem();
                        if (this.isValidItem(item)) {
                              blockCount += is.stackSize;
                        }
                  }
            }

            return blockCount;
      }

      private boolean isBlockUnder(double yOffset) {
            return !this.validBlocks.contains(mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY - yOffset, Minecraft.player.posZ)).getBlock());
      }

      private boolean isValidItem(Item item) {
            if (item instanceof ItemBlock) {
                  ItemBlock iBlock = (ItemBlock)item;
                  Block block = iBlock.getBlock();
                  return !invalidBlocks.contains(block);
            } else {
                  return false;
            }
      }

      public Scaffold.BlockData getBlockData(BlockPos pos, int i) {
            return mc.world.getBlockState(pos.add(0, 0, i)).getBlock() != Blocks.AIR ? new Scaffold.BlockData(pos.add(0, 0, i), EnumFacing.NORTH) : (mc.world.getBlockState(pos.add(0, 0, -i)).getBlock() != Blocks.AIR ? new Scaffold.BlockData(pos.add(0, 0, -i), EnumFacing.SOUTH) : (mc.world.getBlockState(pos.add(i, 0, 0)).getBlock() != Blocks.AIR ? new Scaffold.BlockData(pos.add(i, 0, 0), EnumFacing.WEST) : (mc.world.getBlockState(pos.add(-i, 0, 0)).getBlock() != Blocks.AIR ? new Scaffold.BlockData(pos.add(-i, 0, 0), EnumFacing.EAST) : (mc.world.getBlockState(pos.add(0, -i, 0)).getBlock() != Blocks.AIR ? new Scaffold.BlockData(pos.add(0, -i, 0), EnumFacing.UP) : null))));
      }

      public final int findBlock(Container paramContainer) {
            for(int i = 0; i < 9; ++i) {
                  ItemStack localItemStack = paramContainer.getSlot(i | 36).getStack();
                  if (localItemStack != null && localItemStack.getItem() instanceof ItemBlock) {
                        return i;
                  }
            }

            return -1;
      }

      private int findBlockValue(Container paramContainer) {
            int i = 0;

            for(int j = 0; j < 9; ++j) {
                  ItemStack localItemStack = paramContainer.getSlot(j | 36).getStack();
                  if (localItemStack != null && localItemStack.getItem() instanceof ItemBlock) {
                        i |= localItemStack.stackSize;
                  }
            }

            return i;
      }

      public Scaffold.BlockData getBlockData2(BlockPos pos) {
            Scaffold.BlockData blockData = null;

            for(int i = 0; blockData == null && i < 2; ++i) {
                  if (!this.isBlockPosAir(pos.add(0, 0, 1))) {
                        blockData = new Scaffold.BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(0, 0, -1))) {
                        blockData = new Scaffold.BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(1, 0, 0))) {
                        blockData = new Scaffold.BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(-1, 0, 0))) {
                        blockData = new Scaffold.BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
                        break;
                  }

                  if (this.tower.getValBoolean() && mc.gameSettings.keyBindJump.isKeyDown() && !this.isBlockPosAir(pos.add(0, -1, 0))) {
                        blockData = new Scaffold.BlockData(pos.add(0, -1, 0), EnumFacing.UP);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(0, 1, 0)) && isSneaking) {
                        blockData = new Scaffold.BlockData(pos.add(0, 1, 0), EnumFacing.DOWN);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(0, 1, 1)) && isSneaking) {
                        blockData = new Scaffold.BlockData(pos.add(0, 1, 1), EnumFacing.DOWN);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(0, 1, -1)) && isSneaking) {
                        blockData = new Scaffold.BlockData(pos.add(0, 1, -1), EnumFacing.DOWN);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(1, 1, 0)) && isSneaking) {
                        blockData = new Scaffold.BlockData(pos.add(1, 1, 0), EnumFacing.DOWN);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(-1, 1, 0)) && isSneaking) {
                        blockData = new Scaffold.BlockData(pos.add(-1, 1, 0), EnumFacing.DOWN);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(1, 0, 1))) {
                        blockData = new Scaffold.BlockData(pos.add(1, 0, 1), EnumFacing.NORTH);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(-1, 0, -1))) {
                        blockData = new Scaffold.BlockData(pos.add(-1, 0, -1), EnumFacing.SOUTH);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(1, 0, 1))) {
                        blockData = new Scaffold.BlockData(pos.add(1, 0, 1), EnumFacing.WEST);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(-1, 0, -1))) {
                        blockData = new Scaffold.BlockData(pos.add(-1, 0, -1), EnumFacing.EAST);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(-1, 0, 1))) {
                        blockData = new Scaffold.BlockData(pos.add(-1, 0, 1), EnumFacing.NORTH);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(1, 0, -1))) {
                        blockData = new Scaffold.BlockData(pos.add(1, 0, -1), EnumFacing.SOUTH);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(1, 0, -1))) {
                        blockData = new Scaffold.BlockData(pos.add(1, 0, -1), EnumFacing.WEST);
                        break;
                  }

                  if (!this.isBlockPosAir(pos.add(-1, 0, 1))) {
                        blockData = new Scaffold.BlockData(pos.add(-1, 0, 1), EnumFacing.EAST);
                        break;
                  }

                  pos = pos.down();
            }

            return blockData;
      }

      private Vec3d getVec3(Scaffold.BlockData data) {
            BlockPos pos = data.pos;
            EnumFacing face = data.face;
            double x = (double)pos.getX() + 0.5D;
            double y = (double)pos.getY() + 0.5D;
            double z = (double)pos.getZ() + 0.5D;
            if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
                  y += 0.5D;
            } else {
                  x += 0.3D;
                  z += 0.3D;
            }

            if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
                  z += 0.15D;
            }

            if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
                  x += 0.15D;
            }

            return new Vec3d(x, y, z);
      }

      public Vec3d getPositionByFace(BlockPos position, EnumFacing facing) {
            Vec3d offset = new Vec3d((double)facing.getDirectionVec().getX() / 2.0D, (double)facing.getDirectionVec().getY() / 2.0D, (double)facing.getDirectionVec().getZ() / 2.0D);
            Vec3d point = new Vec3d((double)position.getX() + 0.5D, (double)position.getY() + 0.5D, (double)position.getZ() + 0.5D);
            return point.add(offset);
      }

      public boolean isBlockPosAir(BlockPos blockPos) {
            return this.getBlockByPos(blockPos) == Blocks.AIR || this.getBlockByPos(blockPos) instanceof BlockLiquid;
      }

      public Block getBlockByPos(BlockPos blockPos) {
            return mc.world.getBlockState(blockPos).getBlock();
      }

      private double randomNumber(double max, double min) {
            return Math.random() * (max - min) + min;
      }

      static {
            invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.FLOWING_LAVA, Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_SLAB, Blocks.WOODEN_SLAB, Blocks.STONE_SLAB2, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.YELLOW_FLOWER, Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, Blocks.IRON_BARS, Blocks.CACTUS, Blocks.LADDER, Blocks.WEB, Blocks.PUMPKIN);
            double var0 = 0.5D;
      }

      private static class BlockData {
            public final BlockPos pos;
            public final EnumFacing face;

            private BlockData(BlockPos pos, EnumFacing face) {
                  this.pos = pos;
                  this.face = face;
            }

            // $FF: synthetic method
            BlockData(BlockPos x0, EnumFacing x1, Object x2) {
                  this(x0, x1);
            }
      }
}
