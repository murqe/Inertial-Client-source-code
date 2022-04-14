package me.rich.module.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.Hero.settings.Setting;
import java.util.Iterator;
import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event2D;
import me.rich.event.events.EventUpdate;
import me.rich.font.Fonts;
import me.rich.module.Category;
import me.rich.module.Feature;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;

public class AutoTotem extends Feature {
      public Setting counttotem;
      public static Setting radiusCrystal;
      public static Setting checkCrystal;

      public void setup() {
            Main.settingsManager.rSetting(new Setting("TotemHealth", this, 20.0D, 0.0D, 20.0D, false));
            Main.settingsManager.rSetting(new Setting("TotemDelay", this, 0.0D, 0.0D, 5000.0D, false));
            checkCrystal = new Setting("Check Crystal", this, false);
            radiusCrystal = new Setting("Distance to Crystal", this, 6.0D, 1.0D, 8.0D, false);
            Main.settingsManager.rSetting(radiusCrystal);
            Main.settingsManager.rSetting(checkCrystal);
      }

      public AutoTotem() {
            super("AutoTotem", 0, Category.COMBAT);
      }

      @EventTarget
      public void on2D(Event2D event) {
            if (this.fountTotemCount() > 0 && this.counttotem.getValBoolean()) {
                  Fonts.neverlose500_16.drawStringWithShadow(ChatFormatting.RED + "" + this.fountTotemCount() + ChatFormatting.WHITE + "x", (double)(event.getWidth() / 2.0F + 7.0F), (double)(event.getHeight() / 2.0F), -1);
            }

      }

      public int fountTotemCount() {
            int count = 0;

            for(int i = 0; i < Minecraft.player.inventory.getSizeInventory(); ++i) {
                  ItemStack stack = Minecraft.player.inventory.getStackInSlot(i);
                  if (stack.getItem() == Items.field_190929_cY) {
                        ++count;
                  }
            }

            return count;
      }

      private boolean checkCrystal() {
            Iterator var1 = mc.world.loadedEntityList.iterator();

            Entity entity;
            do {
                  if (!var1.hasNext()) {
                        return false;
                  }

                  entity = (Entity)var1.next();
            } while(!(entity instanceof EntityEnderCrystal) || Minecraft.player.getDistanceToEntity(entity) > radiusCrystal.getValFloat());

            return true;
      }

      @EventTarget
      public void onUpdate(EventUpdate event) {
            float delay = (float)Main.settingsManager.getSettingByName("TotemDelay").getValDouble();
            if (timerHelper.check(delay)) {
                  label32: {
                        if (!this.checkCrystal()) {
                              if (Minecraft.player.getHealth() > (float)Main.settingsManager.getSettingByName("TotemHealth").getValDouble()) {
                                    break label32;
                              }

                              Minecraft var10000 = mc;
                              if (Minecraft.player.getHeldItemOffhand().getItem() == Items.field_190929_cY || this.totem() == -1 || !(mc.currentScreen instanceof GuiInventory) && mc.currentScreen != null) {
                                    break label32;
                              }
                        }

                        PlayerControllerMP var3 = mc.playerController;
                        int var10002 = this.totem();
                        Minecraft var10005 = mc;
                        var3.windowClick(0, var10002, 1, ClickType.PICKUP, Minecraft.player);
                        var10005 = mc;
                        mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, Minecraft.player);
                        timerHelper.reset();
                  }

                  if (this.checkCrystal() && checkCrystal.getValBoolean() && Minecraft.player.getHeldItemOffhand().getItem() != Items.field_190929_cY && InventoryHelper.getTotemAtHotbar() != -1) {
                        mc.playerController.windowClick(0, InventoryHelper.getTotemAtHotbar(), 1, ClickType.PICKUP, Minecraft.player);
                        mc.playerController.windowClick(0, 45, 1, ClickType.PICKUP, Minecraft.player);
                  }
            }

      }

      public int totem() {
            for(int i = 0; i < 45; ++i) {
                  Minecraft var10000 = mc;
                  ItemStack itemStack = Minecraft.player.inventoryContainer.getSlot(i).getStack();
                  if (itemStack.getItem() == Items.field_190929_cY) {
                        return i;
                  }
            }

            return -1;
      }
}
