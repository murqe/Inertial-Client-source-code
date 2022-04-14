package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventPacket;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.movement.MovementHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.BlockPos;

public class Velocity extends Feature {
      public Velocity() {
            super("Velocity", 0, Category.COMBAT);
            ArrayList vel = new ArrayList();
            vel.add("Cancel");
            vel.add("Matrix");
            vel.add("Matrix2");
            Main.settingsManager.rSetting(new Setting("Velocity Mode", this, "Cancel", vel));
      }

      @EventTarget
      public void minet(EventUpdate event) {
            String mode = Main.settingsManager.getSettingByName("Velocity Mode").getValString();
            this.setModuleName("Velocity §7" + mode + "");
            if (mode.equalsIgnoreCase("Matrix") && mc.world.getBlockState(new BlockPos(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ)).getBlock() == Block.getBlockById(0) && Minecraft.player.hurtTime > 0) {
                  float ticks = 0.2F;
                  Minecraft.player.motionY = (double)(-ticks);
                  ++ticks;
            }

            if (mode.equalsIgnoreCase("Matrix2") && Minecraft.player.hurtTime > 0) {
                  MovementHelper.strafePlayer(MovementHelper.getSpeed());
                  Minecraft.player.speedInAir = 0.02F;
            }

      }

      @EventTarget
      public void on(EventPacket event) {
            if (Main.settingsManager.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Cancel") && event.getPacket() instanceof SPacketEntityVelocity) {
                  SPacketEntityVelocity sp = (SPacketEntityVelocity)event.getPacket();
                  if (Minecraft.player.getEntityId() == sp.getEntityID()) {
                        int[] motionDiff = this.getMotionDiff(sp.getMotionX(), sp.getMotionY(), sp.getMotionZ());
                        sp.setMotionX(motionDiff[0]);
                        sp.setMotionY(motionDiff[1]);
                        sp.setMotionZ(motionDiff[2]);
                  }
            }

      }

      private int[] getMotionDiff(int motionX, int motionY, int motionZ) {
            double velX = ((double)motionX / 8000.0D - Minecraft.player.motionX) * 0.0D;
            double velY = ((double)motionY / 8000.0D - Minecraft.player.motionY) * 0.0D;
            double velZ = ((double)motionZ / 8000.0D - Minecraft.player.motionZ) * 0.0D;
            int x = (int)(velX * 8000.0D + Minecraft.player.motionX * 8000.0D);
            int y = (int)(velY * 8000.0D + Minecraft.player.motionY * 8000.0D);
            int z = (int)(velZ * 8000.0D + Minecraft.player.motionZ * 8000.0D);
            return new int[]{x, y, z};
      }
}
