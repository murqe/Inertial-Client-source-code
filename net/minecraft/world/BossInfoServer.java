package net.minecraft.world;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;

public class BossInfoServer extends BossInfo {
      private final Set players = Sets.newHashSet();
      private final Set readOnlyPlayers;
      private boolean visible;

      public BossInfoServer(ITextComponent nameIn, BossInfo.Color colorIn, BossInfo.Overlay overlayIn) {
            super(MathHelper.getRandomUUID(), nameIn, colorIn, overlayIn);
            this.readOnlyPlayers = Collections.unmodifiableSet(this.players);
            this.visible = true;
      }

      public void setPercent(float percentIn) {
            if (percentIn != this.percent) {
                  super.setPercent(percentIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PCT);
            }

      }

      public void setColor(BossInfo.Color colorIn) {
            if (colorIn != this.color) {
                  super.setColor(colorIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
            }

      }

      public void setOverlay(BossInfo.Overlay overlayIn) {
            if (overlayIn != this.overlay) {
                  super.setOverlay(overlayIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_STYLE);
            }

      }

      public BossInfo setDarkenSky(boolean darkenSkyIn) {
            if (darkenSkyIn != this.darkenSky) {
                  super.setDarkenSky(darkenSkyIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
            }

            return this;
      }

      public BossInfo setPlayEndBossMusic(boolean playEndBossMusicIn) {
            if (playEndBossMusicIn != this.playEndBossMusic) {
                  super.setPlayEndBossMusic(playEndBossMusicIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
            }

            return this;
      }

      public BossInfo setCreateFog(boolean createFogIn) {
            if (createFogIn != this.createFog) {
                  super.setCreateFog(createFogIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_PROPERTIES);
            }

            return this;
      }

      public void setName(ITextComponent nameIn) {
            if (!Objects.equal(nameIn, this.name)) {
                  super.setName(nameIn);
                  this.sendUpdate(SPacketUpdateBossInfo.Operation.UPDATE_NAME);
            }

      }

      private void sendUpdate(SPacketUpdateBossInfo.Operation operationIn) {
            if (this.visible) {
                  SPacketUpdateBossInfo spacketupdatebossinfo = new SPacketUpdateBossInfo(operationIn, this);
                  Iterator var3 = this.players.iterator();

                  while(var3.hasNext()) {
                        EntityPlayerMP entityplayermp = (EntityPlayerMP)var3.next();
                        entityplayermp.connection.sendPacket(spacketupdatebossinfo);
                  }
            }

      }

      public void addPlayer(EntityPlayerMP player) {
            if (this.players.add(player) && this.visible) {
                  player.connection.sendPacket(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.ADD, this));
            }

      }

      public void removePlayer(EntityPlayerMP player) {
            if (this.players.remove(player) && this.visible) {
                  player.connection.sendPacket(new SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation.REMOVE, this));
            }

      }

      public void setVisible(boolean visibleIn) {
            if (visibleIn != this.visible) {
                  this.visible = visibleIn;
                  Iterator var2 = this.players.iterator();

                  while(var2.hasNext()) {
                        EntityPlayerMP entityplayermp = (EntityPlayerMP)var2.next();
                        entityplayermp.connection.sendPacket(new SPacketUpdateBossInfo(visibleIn ? SPacketUpdateBossInfo.Operation.ADD : SPacketUpdateBossInfo.Operation.REMOVE, this));
                  }
            }

      }

      public Collection getPlayers() {
            return this.readOnlyPlayers;
      }
}
