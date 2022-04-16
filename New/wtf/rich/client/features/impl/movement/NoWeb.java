package wtf.rich.client.features.impl.movement;

import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.MoveEvent;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.ListSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class NoWeb extends Feature {
     public ListSetting noWebMode = new ListSetting("NoWeb Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Matrix", "Matrix New", "NCP"});
     public NumberSetting webSpeed = new NumberSetting("Web Speed", 0.8F, 0.1F, 2.0F, 0.1F, () -> {
          return this.noWebMode.currentMode.equals("Matrix New");
     });
     public NumberSetting webJumpMotion = new NumberSetting("Jump Motion", 2.0F, 0.0F, 10.0F, 1.0F, () -> {
          return this.noWebMode.currentMode.equals("Matrix New");
     });

     public NoWeb() {
          super("NoWeb", "Позволяет быстро ходить в паутине", 0, Category.PLAYER);
          this.addSettings(new Setting[]{this.noWebMode, this.webJumpMotion, this.webSpeed});
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          String mode = this.noWebMode.getOptions();
          this.setSuffix(mode);
          if (mode.equalsIgnoreCase("Matrix New")) {
               BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 0.6D, mc.player.posZ);
               Block block = mc.world.getBlockState(blockPos).getBlock();
               EntityPlayerSP var10000;
               if (mc.player.isInWeb) {
                    var10000 = mc.player;
                    var10000.motionY += 2.0D;
               } else if (Block.getIdFromBlock(block) == 30) {
                    if (this.webJumpMotion.getNumberValue() > 0.0F) {
                         var10000 = mc.player;
                         var10000.motionY += (double)this.webJumpMotion.getNumberValue();
                    } else {
                         mc.player.motionY = 0.0D;
                    }

                    MovementHelper.setSpeed((double)this.webSpeed.getNumberValue());
                    mc.gameSettings.keyBindJump.pressed = false;
               }
          }

     }

     @EventTarget
     public void onMove(MoveEvent event) {
          String mode = this.noWebMode.getOptions();
          this.setSuffix(mode);
          if (this.isToggled()) {
               if (mode.equalsIgnoreCase("Matrix")) {
                    if (mc.player.onGround && mc.player.isInWeb) {
                         mc.player.isInWeb = true;
                    } else {
                         if (mc.gameSettings.keyBindJump.isKeyDown()) {
                              return;
                         }

                         mc.player.isInWeb = false;
                    }

                    if (mc.player.isInWeb && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                         MovementHelper.setEventSpeed(event, 0.483D);
                    }
               } else if (mode.equalsIgnoreCase("NCP")) {
                    if (mc.player.onGround && mc.player.isInWeb) {
                         mc.player.isInWeb = true;
                    } else {
                         if (mc.gameSettings.keyBindJump.isKeyDown()) {
                              return;
                         }

                         mc.player.isInWeb = false;
                    }

                    if (mc.player.isInWeb && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                         MovementHelper.setEventSpeed(event, 0.403D);
                    }
               }
          }

     }
}
