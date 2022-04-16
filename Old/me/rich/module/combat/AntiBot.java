package me.rich.module.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.EventAttack;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.event.events.EventReceivePacket;
import me.rich.event.events.EventUpdate;
import me.rich.helpers.friend.FriendManager;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketSpawnPlayer;

public class AntiBot extends Feature {
      public static List invalid = new ArrayList();
      public static ArrayList entete = new ArrayList();
      Entity currentEntity;
      Entity[] playerList;
      int index;
      boolean next;
      double[] oldPos;
      private UUID detectedEntity;

      public static List getInvalid() {
            return invalid;
      }

      public AntiBot() {
            super("AntiBot", 0, Category.COMBAT);
      }

      public void setup() {
            ArrayList mode = new ArrayList();
            mode.add("Matrix");
            mode.add("HitBefore");
            Main var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("Invisible Remove", this, false));
            var10000 = Main.instance;
            Main.settingsManager.rSetting(new Setting("AntiBot Mode", this, "Matrix", mode));
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            this.isBot();
      }

      public void isBot() {
            Iterator var2 = mc.world.playerEntities.iterator();
            Main var10000 = Main.instance;
            String mode = Main.settingsManager.getSettingByName("AntiBot Mode").getValString();
            this.setModuleName("AntiBot §7Mode : " + Main.settingsManager.getSettingByName("AntiBot Mode").getValString());
            if (mode.equalsIgnoreCase("Matrix")) {
                  Iterator var3 = mc.world.loadedEntityList.iterator();

                  while(var3.hasNext()) {
                        Entity e = (Entity)var3.next();
                        if (e.ticksExisted < 5 && e instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)e).hurtTime > 0) {
                              Minecraft var5 = mc;
                              if (Minecraft.player.getDistanceToEntity(e) <= 25.0F && mc.getConnection().getPlayerInfo(e.getUniqueID()).getResponseTime() != 0) {
                                    mc.world.removeEntity(e);
                              }
                        }
                  }
            }

      }

      @EventTarget
      public void onMouse(EventAttack event) {
            Main var10000 = Main.instance;
            String mode = Main.settingsManager.getSettingByName("AntiBot Mode").getValString();
            if (this.isToggled() && mode.equalsIgnoreCase("HitBefore")) {
                  EntityPlayer entityPlayer = (EntityPlayer)mc.objectMouseOver.entityHit;
                  String name = entityPlayer.getName();
                  FriendManager.getFriends();
                  if (FriendManager.isFriend(entityPlayer.getName())) {
                        return;
                  }

                  if (entete.contains(entityPlayer)) {
                        Main.msg(ChatFormatting.AQUA + name + ChatFormatting.GRAY + " Already in AntiBot-List!");
                  } else {
                        Main.msg(ChatFormatting.AQUA + name + ChatFormatting.GRAY + " Was added in AntiBot-List!");
                  }
            }

      }

      @EventTarget
      public void onPre(EventPreMotionUpdate event) {
            Iterator var2 = mc.world.loadedEntityList.iterator();

            while(true) {
                  Entity entity;
                  do {
                        do {
                              if (!var2.hasNext()) {
                                    return;
                              }

                              entity = (Entity)var2.next();
                              if (Minecraft.player != null) {
                                    return;
                              }

                              Main var10000 = Main.instance;
                        } while(!Main.settingsManager.getSettingByName("Invisible Remove").getValBoolean());
                  } while(entity instanceof EntityPlayer && entity.isInvisible());

                  mc.world.removeEntity(entity);
            }
      }

      @EventTarget
      public void onPacket(EventReceivePacket event) {
            if (event.getPacket() instanceof SPacketSpawnPlayer) {
                  Minecraft var10000 = mc;
                  if (Minecraft.player.ticksExisted >= 9 && ((SPacketSpawnPlayer)event.getPacket()).getYaw() != 0 && ((SPacketSpawnPlayer)event.getPacket()).getPitch() != 0) {
                        this.detectedEntity = ((SPacketSpawnPlayer)event.getPacket()).getUniqueId();
                        Main.msg(mc.world.getPlayerEntityByUUID(this.detectedEntity).rotationYaw + " pitch: " + mc.world.getPlayerEntityByUUID(this.detectedEntity).rotationPitch);
                  }
            }

      }

      public void onEnable() {
            super.onEnable();
      }

      public void onDisable() {
            super.onDisable();
      }
}
