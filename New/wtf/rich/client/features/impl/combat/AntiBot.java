package wtf.rich.client.features.impl.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ListSetting;

public class AntiBot extends Feature {
     public static List isBotPlayer = new ArrayList();
     public ListSetting antiBotMode = new ListSetting("Anti Bot Mode", "Matrix", () -> {
          return true;
     }, new String[]{"Matrix", "Reflex"});
     public BooleanSetting invisIgnore = new BooleanSetting("Invisible Ignore", "Игнорирует невидимых сущностей", false, () -> {
          return true;
     });

     public AntiBot() {
          super("AntiBot", "Добавляет сущностей заспавненых античитом в блэк-лист", 0, Category.COMBAT);
          this.addSettings(new Setting[]{this.antiBotMode, this.invisIgnore});
     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          String abmode = this.antiBotMode.getOptions();
          Iterator var3 = mc.world.loadedEntityList.iterator();

          while(true) {
               while(true) {
                    while(var3.hasNext()) {
                         Entity entity = (Entity)var3.next();
                         byte var6 = -1;
                         switch(abmode.hashCode()) {
                         case -1997372447:
                              if (abmode.equals("Matrix")) {
                                   var6 = 0;
                              }
                              break;
                         case -1850955572:
                              if (abmode.equals("Reflex")) {
                                   var6 = 1;
                              }
                         }

                         switch(var6) {
                         case 0:
                              if (entity != mc.player && entity.ticksExisted < 5 && entity instanceof EntityOtherPlayerMP && ((EntityOtherPlayerMP)entity).hurtTime > 0 && mc.player.getDistanceToEntity(entity) <= 25.0F && mc.player.connection.getPlayerInfo(entity.getUniqueID()).getResponseTime() != 0) {
                                   isBotPlayer.add(entity);
                              }
                              break;
                         case 1:
                              if (entity.getDisplayName().getUnformattedText().length() == 8 && mc.player.posY < entity.posY && entity.ticksExisted == 1 && !entity.isCollidedVertically && !entity.isEntityInsideOpaqueBlock() && entity.fallDistance == 0.0F && entity.posX != 0.0D && entity.posZ != 0.0D) {
                                   isBotPlayer.add(entity);
                              } else if (this.invisIgnore.getBoolValue() && entity.isInvisible() && entity != mc.player) {
                                   isBotPlayer.add(entity);
                              }
                         }
                    }

                    return;
               }
          }
     }
}
