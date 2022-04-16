package me.rich.module.combat;

import de.Hero.settings.Setting;
import java.util.ArrayList;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventAttack;
import me.rich.event.events.EventReceivePacket;
import me.rich.event.events.EventStep;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.world.TimerHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class Criticals extends Feature {
      EntityLivingBase target;
      int groundTicks;
      int stage;
      int count;
      TimerHelper lastStep;
      TimerHelper timer;

      public Criticals() {
            super("Criticals", 0, Category.COMBAT);
            this.target = KillAura.target;
            this.lastStep = new TimerHelper();
            this.timer = new TimerHelper();
            ArrayList mode = new ArrayList();
            mode.add("Packet");
            mode.add("PacketNew");
            Main.settingsManager.rSetting(new Setting("Criticals Mode", this, "Packet", mode));
      }

      @EventTarget
      public void onAttackPacket(EventAttack event) {
            String mode = Main.settingsManager.getSettingByName("Criticals Mode").getValString();
            if (KillAura.target != null && Main.moduleManager.getModule(KillAura.class).isToggled()) {
                  double x = Minecraft.player.posX;
                  double y = Minecraft.player.posY;
                  double z = Minecraft.player.posZ;
                  if (mode.equalsIgnoreCase("Packet")) {
                        mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y + 0.5D, z, true));
                        mc.getConnection().sendPacket(new CPacketPlayer.Position(x, y, z, false));
                  }

                  if (mode.equalsIgnoreCase("PacketNew")) {
                        Minecraft.player.setPosition(x, y + 0.04D, z);
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY + 0.01D, Minecraft.player.posZ, true));
                        Minecraft.player.connection.sendPacket(new CPacketPlayer.Position(Minecraft.player.posX, Minecraft.player.posY, Minecraft.player.posZ, false));
                  }
            }

      }

      @EventTarget
      public void onPacket(EventReceivePacket event) {
            Packet packet = event.getPacket();
            if (packet instanceof SPacketPlayerPosLook) {
                  this.stage = 0;
            }

            if (packet instanceof CPacketConfirmTransaction) {
                  CPacketConfirmTransaction confirmTransaction = (CPacketConfirmTransaction)packet;
                  short uid = confirmTransaction.getUid();
                  if (uid == 0) {
                        ++this.count;
                  }
            }

      }

      @EventTarget
      public void onStep(EventStep event) {
            if (!event.isPre()) {
                  this.lastStep.reset();
                  if (!Minecraft.player.isCollidedHorizontally) {
                        this.stage = 0;
                  }
            }

      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            String mode = Main.settingsManager.getSettingByName("Criticals Mode").getValString();
            this.setSuffix(mode);
      }
}
