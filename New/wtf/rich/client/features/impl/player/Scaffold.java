package wtf.rich.client.features.impl.player;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventRender2D;
import wtf.rich.api.event.event.EventSafeWalk;
import wtf.rich.api.event.event.EventStrafe;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.combat.RotationHelper;
import wtf.rich.api.utils.math.MathematicHelper;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.api.utils.world.InventoryHelper;
import wtf.rich.api.utils.world.TimerHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class Scaffold extends Feature {
     public static List invalidBlocks;
     public static Scaffold.BlockData data;
     public static boolean isSneaking;
     public static BooleanSetting down;
     public static BooleanSetting sprintoff;
     public static BooleanSetting rotationRandom;
     public static NumberSetting rotationSpeed;
     private final TimerHelper time = new TimerHelper();
     private final BooleanSetting jump;
     private final BooleanSetting swing;
     private final NumberSetting delay = new NumberSetting("Min Delay", 0.0F, 0.0F, 300.0F, 1.0F, () -> {
          return true;
     });
     private final NumberSetting delayRandom = new NumberSetting("Random Delay", 0.0F, 0.0F, 1000.0F, 1.0F, () -> {
          return true;
     });
     private final NumberSetting chance = new NumberSetting("Chance", 100.0F, 0.0F, 100.0F, 1.0F, () -> {
          return true;
     });
     private final NumberSetting speed = new NumberSetting("Speed", 0.6F, 0.05F, 1.2F, 0.01F, () -> {
          return true;
     });
     private final BooleanSetting rotStrafe;
     private final BooleanSetting safewalk;
     private final ListSetting blockRotation = new ListSetting("BlockRotation Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Matrix", "None"});
     private final ListSetting towerMode = new ListSetting("Tower Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Matrix", "NCP", "Default"});
     public NumberSetting rotPitchRandom = new NumberSetting("Rotation Pitch Random", 2.0F, 0.0F, 8.0F, 0.01F, () -> {
          return rotationRandom.getBoolValue();
     });
     public NumberSetting rotYawRandom = new NumberSetting("Rotation Yaw Random", 2.0F, 0.0F, 8.0F, 0.01F, () -> {
          return rotationRandom.getBoolValue();
     });
     public BooleanSetting airCheck = new BooleanSetting("Check Air", true, () -> {
          return true;
     });
     public BooleanSetting sneak = new BooleanSetting("Sneak", true, () -> {
          return true;
     });
     public NumberSetting sneakChance = new NumberSetting("Sneak Chance", 100.0F, 0.0F, 100.0F, 1.0F, () -> {
          return this.sneak.getBoolValue();
     });
     public NumberSetting sneakSpeed = new NumberSetting("Sneak Speed", 0.05F, 0.01F, 1.0F, 0.01F, () -> {
          return this.sneak.getBoolValue();
     });
     public ListSetting sneakMode = new ListSetting("Sneak Mode", "Packet", () -> {
          return this.sneak.getBoolValue();
     }, new String[]{"Packet", "Client"});
     public NumberSetting rotationOffset = new NumberSetting("Rotation Offset", 0.25F, 0.0F, 1.0F, 0.01F, () -> {
          return true;
     });
     public NumberSetting placeOffset = new NumberSetting("Place Offset", 0.2F, 0.01F, 0.3F, 0.01F, () -> {
          return true;
     });
     private int slot;

     public Scaffold() {
          super("Scaffold", "Автоматически ставит под вас блоки", 0, Category.PLAYER);
          sprintoff = new BooleanSetting("Stop Sprinting", true, () -> {
               return true;
          });
          this.safewalk = new BooleanSetting("SafeWalk", true, () -> {
               return true;
          });
          this.jump = new BooleanSetting("Jump", false, () -> {
               return true;
          });
          down = new BooleanSetting("DownWard", false, () -> {
               return true;
          });
          this.swing = new BooleanSetting("SwingHand", false, () -> {
               return true;
          });
          this.rotStrafe = new BooleanSetting("Rotation Strafe", false, () -> {
               return true;
          });
          this.addSettings(new Setting[]{this.blockRotation, this.towerMode, this.chance, this.delay, this.delayRandom, this.rotationOffset, this.placeOffset, rotationSpeed, rotationRandom, this.rotYawRandom, this.rotPitchRandom, this.speed, this.sneak, this.sneakMode, this.sneakChance, this.sneakSpeed, sprintoff, this.airCheck, this.safewalk, this.jump, down, this.swing, this.rotStrafe});
     }

     public static int searchBlock() {
          for(int i = 0; i < 45; ++i) {
               ItemStack itemStack = mc.player.inventoryContainer.getSlot(i).getStack();
               if (itemStack.getItem() instanceof ItemBlock) {
                    return i;
               }
          }

          return -1;
     }

     private boolean canPlace() {
          BlockPos bp1 = new BlockPos(mc.player.posX - (double)this.placeOffset.getNumberValue(), mc.player.posY - (double)this.placeOffset.getNumberValue(), mc.player.posZ - (double)this.placeOffset.getNumberValue());
          BlockPos bp2 = new BlockPos(mc.player.posX - (double)this.placeOffset.getNumberValue(), mc.player.posY - (double)this.placeOffset.getNumberValue(), mc.player.posZ + (double)this.placeOffset.getNumberValue());
          BlockPos bp3 = new BlockPos(mc.player.posX + (double)this.placeOffset.getNumberValue(), mc.player.posY - (double)this.placeOffset.getNumberValue(), mc.player.posZ + (double)this.placeOffset.getNumberValue());
          BlockPos bp4 = new BlockPos(mc.player.posX + (double)this.placeOffset.getNumberValue(), mc.player.posY - (double)this.placeOffset.getNumberValue(), mc.player.posZ - (double)this.placeOffset.getNumberValue());
          return mc.player.world.getBlockState(bp1).getBlock() == Blocks.AIR && mc.player.world.getBlockState(bp2).getBlock() == Blocks.AIR && mc.player.world.getBlockState(bp3).getBlock() == Blocks.AIR && mc.player.world.getBlockState(bp4).getBlock() == Blocks.AIR;
     }

     private boolean canSneak() {
          BlockPos bp1 = new BlockPos(mc.player.posX - (double)this.sneakSpeed.getNumberValue(), mc.player.posY - (double)this.sneakSpeed.getNumberValue(), mc.player.posZ - (double)this.sneakSpeed.getNumberValue());
          BlockPos bp2 = new BlockPos(mc.player.posX - (double)this.sneakSpeed.getNumberValue(), mc.player.posY - (double)this.sneakSpeed.getNumberValue(), mc.player.posZ + (double)this.sneakSpeed.getNumberValue());
          BlockPos bp3 = new BlockPos(mc.player.posX + (double)this.sneakSpeed.getNumberValue(), mc.player.posY - (double)this.sneakSpeed.getNumberValue(), mc.player.posZ + (double)this.sneakSpeed.getNumberValue());
          BlockPos bp4 = new BlockPos(mc.player.posX + (double)this.sneakSpeed.getNumberValue(), mc.player.posY - (double)this.sneakSpeed.getNumberValue(), mc.player.posZ - (double)this.sneakSpeed.getNumberValue());
          return mc.player.world.getBlockState(bp1).getBlock() == Blocks.AIR && mc.player.world.getBlockState(bp2).getBlock() == Blocks.AIR && mc.player.world.getBlockState(bp3).getBlock() == Blocks.AIR && mc.player.world.getBlockState(bp4).getBlock() == Blocks.AIR;
     }

     public void onEnable() {
          this.slot = mc.player.inventory.currentItem;
          data = null;
          super.onEnable();
     }

     public void onDisable() {
          mc.player.inventory.currentItem = this.slot;
          mc.timer.timerSpeed = 1.0F;
          mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
          mc.gameSettings.keyBindSneak.pressed = false;
          super.onDisable();
     }

     @EventTarget
     public void onStrafeMotion(EventStrafe eventStrafe) {
          if (this.rotStrafe.getBoolValue()) {
               eventStrafe.setCancelled(true);
               MovementHelper.calculateSilentMove(eventStrafe, RotationHelper.Rotation.packetYaw);
          }

     }

     @EventTarget
     public void onSafe(EventSafeWalk eventSafeWalk) {
          if (this.safewalk.getBoolValue() && !isSneaking) {
               eventSafeWalk.setCancelled(mc.player.onGround);
          }

     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate eventUpdate) {
          String tower = this.towerMode.getCurrentMode();
          this.setSuffix(this.blockRotation.getCurrentMode());
          EntityPlayerSP var10000;
          if (tower.equalsIgnoreCase("Matrix")) {
               if (!MovementHelper.isMoving()) {
                    if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
                         mc.player.jump();
                    }

                    if (mc.player.motionY > 0.0D && !mc.player.onGround) {
                         var10000 = mc.player;
                         var10000.motionY -= 0.00994D;
                    } else {
                         var10000 = mc.player;
                         var10000.motionY -= 0.00995D;
                    }
               }
          } else if (tower.equalsIgnoreCase("NCP") && !MovementHelper.isMoving()) {
               if (mc.player.onGround && mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.jump();
               }

               float pos = -2.0F;
               if (mc.player.motionY < 0.1D && !(mc.world.getBlockState((new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).add(0.0D, (double)pos, 0.0D)).getBlock() instanceof BlockAir)) {
                    var10000 = mc.player;
                    var10000.motionY -= 190.0D;
               }
          }

          if (mc.gameSettings.keyBindSneak.pressed && down.getBoolValue()) {
               mc.gameSettings.keyBindSneak.pressed = false;
               isSneaking = true;
          } else {
               isSneaking = false;
          }

          var10000 = mc.player;
          var10000.motionX *= (double)this.speed.getNumberValue();
          var10000 = mc.player;
          var10000.motionZ *= (double)this.speed.getNumberValue();
          if (!InventoryHelper.doesHotbarHaveBlock() && !(mc.player.getHeldItemOffhand().getItem() instanceof ItemBlock) && searchBlock() != -1) {
               mc.playerController.windowClick(0, searchBlock(), 1, ClickType.QUICK_MOVE, mc.player);
          }

          BlockPos blockPos = isSneaking ? (new BlockPos(mc.player)).add(1, -1, 0).down() : (new BlockPos(mc.player)).add(0, -1, 0);

          for(double posY = mc.player.posY - 1.0D; posY > 0.0D; --posY) {
               Scaffold.BlockData newData = this.getBlockData(blockPos);
               if (newData != null) {
                    double yDif = mc.player.posY - posY;
                    if (yDif <= 7.0D) {
                         data = newData;
                    }
               }
          }

          if (sprintoff.getBoolValue()) {
               mc.player.setSprinting(false);
          }

          if (data != null && this.slot != -1 && !mc.player.isInLiquid()) {
               Vec3d hitVec = this.getVectorToRotate(data);
               if (this.blockRotation.getOptions().equalsIgnoreCase("Matrix")) {
                    float[] rots = RotationHelper.getRotationVector(hitVec, rotationRandom.getBoolValue(), this.rotYawRandom.getNumberValue(), this.rotPitchRandom.getNumberValue(), rotationSpeed.getNumberValue());
                    eventUpdate.setYaw(rots[0]);
                    eventUpdate.setPitch(rots[1]);
                    if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR && !this.airCheck.getBoolValue()) {
                         mc.player.renderYawOffset = rots[0];
                         mc.player.rotationYawHead = rots[0];
                         mc.player.rotationPitchHead = rots[1];
                    } else {
                         mc.player.renderYawOffset = rots[0];
                         mc.player.rotationYawHead = rots[0];
                         mc.player.rotationPitchHead = rots[1];
                    }
               }
          }

     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (InventoryHelper.doesHotbarHaveBlock() && data != null) {
               int slot = -1;
               int lastItem = mc.player.inventory.currentItem;
               BlockPos pos = data.pos;
               Vec3d hitVec = this.getVectorToPlace(data);

               for(int i = 0; i < 9; ++i) {
                    ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                    if (this.isValidItem(itemStack.getItem())) {
                         slot = i;
                    }
               }

               if (slot != -1) {
                    if (this.jump.getBoolValue() && !mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
                         mc.player.jump();
                    }

                    if (!this.jump.getBoolValue() && InventoryHelper.doesHotbarHaveBlock() && MovementHelper.isMoving() && !mc.gameSettings.keyBindJump.isKeyDown() && this.sneak.getBoolValue() && MathematicHelper.randomizeFloat(0.0F, 100.0F) <= this.sneakChance.getNumberValue() && InventoryHelper.doesHotbarHaveBlock()) {
                         if (this.canSneak()) {
                              if (this.sneakMode.currentMode.equals("Packet")) {
                                   mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_RIDING_JUMP));
                                   mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                              } else if (this.sneakMode.currentMode.equals("Client")) {
                                   mc.gameSettings.keyBindSneak.pressed = true;
                              }
                         } else if (this.sneakMode.currentMode.equals("Packet")) {
                              mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                         } else if (this.sneakMode.currentMode.equals("Client")) {
                              mc.gameSettings.keyBindSneak.pressed = false;
                         }
                    }

                    if (this.time.hasReached((double)(this.delay.getNumberValue() + MathematicHelper.randomizeFloat(0.0F, this.delayRandom.getNumberValue()))) && this.canPlace()) {
                         if (MathematicHelper.randomizeFloat(0.0F, 100.0F) <= this.chance.getNumberValue()) {
                              mc.player.inventory.currentItem = slot;
                         }

                         mc.playerController.processRightClickBlock(mc.player, mc.world, pos, data.face, hitVec, EnumHand.MAIN_HAND);
                         if (this.swing.getBoolValue()) {
                              mc.player.swingArm(EnumHand.MAIN_HAND);
                         } else {
                              mc.player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
                         }

                         mc.player.inventory.currentItem = lastItem;
                         this.time.reset();
                    }
               }
          }

     }

     @EventTarget
     public void onRender2D(EventRender2D render) {
          float width = (float)render.getResolution().getScaledWidth();
          float height = (float)render.getResolution().getScaledHeight();
          String blockString = this.getBlockCount() + " Blocks";
          GlStateManager.pushMatrix();
          GlStateManager.translate(23.0F, 15.0F, 0.0F);
          DrawHelper.drawSkeetRectWithoutBorder(width / 2.0F + 88.0F - (float)mc.neverlose500_17.getStringWidth(blockString), height / 2.0F - (float)mc.neverlose500_17.getStringHeight(blockString) + 59.0F, width / 2.0F + (float)mc.neverlose500_18.getStringHeight(blockString) + 2.0F, height / 2.0F - (float)mc.neverlose500_18.getStringHeight(blockString) / 2.0F - 55.0F);
          mc.neverlose500_17.drawStringWithOutline(blockString, (double)(width / 2.0F + 49.0F - (float)mc.neverlose500_17.getStringWidth(blockString)), (double)(height / 2.0F - 6.0F), -1);
          GlStateManager.popMatrix();
     }

     private int getBlockCount() {
          int blockCount = 0;

          for(int i = 0; i < 45; ++i) {
               if (mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                    ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
                    Item item = is.getItem();
                    if (this.isValidItem(item)) {
                         blockCount += is.stackSize;
                    }
               }
          }

          return blockCount;
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

     public Scaffold.BlockData getBlockData(BlockPos pos) {
          Scaffold.BlockData blockData = null;

          for(int i = 0; blockData == null && i < 2; ++i) {
               if (this.isBlockPosAir(pos.add(0, 0, 1))) {
                    blockData = new Scaffold.BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
                    break;
               }

               if (this.isBlockPosAir(pos.add(0, 0, -1))) {
                    blockData = new Scaffold.BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
                    break;
               }

               if (this.isBlockPosAir(pos.add(1, 0, 0))) {
                    blockData = new Scaffold.BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
                    break;
               }

               if (this.isBlockPosAir(pos.add(-1, 0, 0))) {
                    blockData = new Scaffold.BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
                    break;
               }

               if (mc.gameSettings.keyBindJump.isKeyDown() && this.isBlockPosAir(pos.add(0, -1, 0))) {
                    blockData = new Scaffold.BlockData(pos.add(0, -1, 0), EnumFacing.UP);
                    break;
               }

               if (this.isBlockPosAir(pos.add(0, 1, 0)) && isSneaking) {
                    blockData = new Scaffold.BlockData(pos.add(0, 1, 0), EnumFacing.DOWN);
                    break;
               }

               if (this.isBlockPosAir(pos.add(0, 1, 1)) && isSneaking) {
                    blockData = new Scaffold.BlockData(pos.add(0, 1, 1), EnumFacing.DOWN);
                    break;
               }

               if (this.isBlockPosAir(pos.add(0, 1, -1)) && isSneaking) {
                    blockData = new Scaffold.BlockData(pos.add(0, 1, -1), EnumFacing.DOWN);
                    break;
               }

               if (this.isBlockPosAir(pos.add(1, 1, 0)) && isSneaking) {
                    blockData = new Scaffold.BlockData(pos.add(1, 1, 0), EnumFacing.DOWN);
                    break;
               }

               if (this.isBlockPosAir(pos.add(-1, 1, 0)) && isSneaking) {
                    blockData = new Scaffold.BlockData(pos.add(-1, 1, 0), EnumFacing.DOWN);
                    break;
               }

               if (this.isBlockPosAir(pos.add(1, 0, 1))) {
                    blockData = new Scaffold.BlockData(pos.add(1, 0, 1), EnumFacing.NORTH);
                    break;
               }

               if (this.isBlockPosAir(pos.add(-1, 0, -1))) {
                    blockData = new Scaffold.BlockData(pos.add(-1, 0, -1), EnumFacing.SOUTH);
                    break;
               }

               if (this.isBlockPosAir(pos.add(1, 0, 1))) {
                    blockData = new Scaffold.BlockData(pos.add(1, 0, 1), EnumFacing.WEST);
                    break;
               }

               if (this.isBlockPosAir(pos.add(-1, 0, -1))) {
                    blockData = new Scaffold.BlockData(pos.add(-1, 0, -1), EnumFacing.EAST);
                    break;
               }

               if (this.isBlockPosAir(pos.add(-1, 0, 1))) {
                    blockData = new Scaffold.BlockData(pos.add(-1, 0, 1), EnumFacing.NORTH);
                    break;
               }

               if (this.isBlockPosAir(pos.add(1, 0, -1))) {
                    blockData = new Scaffold.BlockData(pos.add(1, 0, -1), EnumFacing.SOUTH);
                    break;
               }

               if (this.isBlockPosAir(pos.add(1, 0, -1))) {
                    blockData = new Scaffold.BlockData(pos.add(1, 0, -1), EnumFacing.WEST);
                    break;
               }

               if (this.isBlockPosAir(pos.add(-1, 0, 1))) {
                    blockData = new Scaffold.BlockData(pos.add(-1, 0, 1), EnumFacing.EAST);
                    break;
               }

               pos = pos.down();
          }

          return blockData;
     }

     private Vec3d getVectorToPlace(Scaffold.BlockData data) {
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
               z += (double)this.placeOffset.getNumberValue();
          }

          if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
               x += (double)this.placeOffset.getNumberValue();
          }

          return new Vec3d(x, y, z);
     }

     private Vec3d getVectorToRotate(Scaffold.BlockData data) {
          BlockPos pos = data.pos;
          EnumFacing face = data.face;
          double x = (double)pos.getX() + 0.5D;
          double y = (double)pos.getY() + 0.5D;
          double z = (double)pos.getZ() + 0.5D;
          if (face != EnumFacing.UP && face != EnumFacing.DOWN) {
               y += 0.4D;
          } else {
               x += 0.4D;
               z += 0.4D;
          }

          if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
               z += (double)this.rotationOffset.getNumberValue();
          }

          if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
               x += (double)this.rotationOffset.getNumberValue();
          }

          return new Vec3d(x, y, z);
     }

     public boolean isBlockPosAir(BlockPos blockPos) {
          return this.getBlockByPos(blockPos) != Blocks.AIR && !(this.getBlockByPos(blockPos) instanceof BlockLiquid);
     }

     public Block getBlockByPos(BlockPos blockPos) {
          return mc.world.getBlockState(blockPos).getBlock();
     }

     static {
          invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, Blocks.WATER, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.FLOWING_LAVA, Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_SLAB, Blocks.WOODEN_SLAB, Blocks.STONE_SLAB2, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.YELLOW_FLOWER, Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, Blocks.IRON_BARS, Blocks.CACTUS, Blocks.LADDER, Blocks.WEB, Blocks.PUMPKIN);
          rotationRandom = new BooleanSetting("Rotation Random", true, () -> {
               return true;
          });
          rotationSpeed = new NumberSetting("Rotation Speed", 360.0F, 1.0F, 360.0F, 1.0F, () -> {
               return true;
          });
     }

     private static class BlockData {
          public BlockPos pos;
          public EnumFacing face;

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
