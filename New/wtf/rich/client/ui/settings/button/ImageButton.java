package wtf.rich.client.ui.settings.button;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.ui.GuiCapeSelector;
import wtf.rich.client.ui.GuiConfig;
import wtf.rich.client.ui.MouseHelper;

public class ImageButton {
     protected int height;
     protected String description;
     protected int width;
     protected Minecraft mc;
     protected ResourceLocation image;
     protected int target;
     protected int x;
     protected int ani = 0;
     protected int y;

     public ImageButton(ResourceLocation resourceLocation, int x, int y, int width, int height, String description, int target) {
          this.image = resourceLocation;
          this.x = x;
          this.y = y;
          this.width = width;
          this.height = height;
          this.description = description;
          this.target = target;
          this.mc = Minecraft.getMinecraft();
     }

     protected void hoverAnimation(int mouseX, int mouseY) {
          if (this.isHovered(mouseX, mouseY)) {
               if (this.ani < 3) {
                    ++this.ani;
               }
          } else if (this.ani > 0) {
               --this.ani;
          }

     }

     public void onClick(int mouseX, int mouseY) {
          if (this.isHovered(mouseX, mouseY)) {
               if (this.target == 19) {
                    Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
               }

               if (this.target == 22) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiConfig());
               }

               if (this.target == 23) {
                    Minecraft.getMinecraft().displayGuiScreen(new GuiCapeSelector());
               }

               GuiChest chest;
               if (this.target == 30) {
                    chest = (GuiChest)this.mc.currentScreen;
                    if (chest != null) {
                         (new Thread(() -> {
                              try {
                                   for(int i = 0; i < chest.getInventoryRows() * 9; ++i) {
                                        ContainerChest container = (ContainerChest)this.mc.player.openContainer;
                                        if (container != null) {
                                             Thread.sleep(50L);
                                             this.mc.playerController.windowClick(container.windowId, i, 0, ClickType.QUICK_MOVE, this.mc.player);
                                        }
                                   }
                              } catch (Exception var4) {
                              }

                         })).start();
                    }
               }

               if (this.target == 31) {
                    chest = (GuiChest)this.mc.currentScreen;
                    if (chest != null) {
                         (new Thread(() -> {
                              try {
                                   for(int i = chest.getInventoryRows() * 9; i < chest.getInventoryRows() * 9 + 44; ++i) {
                                        Slot slot = (Slot)chest.inventorySlots.inventorySlots.get(i);
                                        if (slot.getStack() != null) {
                                             Thread.sleep(50L);
                                             chest.handleMouseClick(slot, slot.slotNumber, 0, ClickType.QUICK_MOVE);
                                        }
                                   }
                              } catch (Exception var3) {
                              }

                         })).start();
                    }
               }

               if (this.target == 32) {
                    for(int i = 0; i < 46; ++i) {
                         if (this.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                              this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, i, 1, ClickType.THROW, this.mc.player);
                         }
                    }
               }

               byte var4;
               String var6;
               if (this.target == 55) {
                    var6 = GuiCapeSelector.Selector.getCapeName();
                    var4 = -1;
                    switch(var6.hashCode()) {
                    case 3046099:
                         if (var6.equals("cape")) {
                              var4 = 1;
                         }
                         break;
                    case 3441014:
                         if (var6.equals("pink")) {
                              var4 = 0;
                         }
                    }

                    switch(var4) {
                    case 0:
                         GuiCapeSelector.Selector.capeName = "pink";
                         break;
                    case 1:
                         GuiCapeSelector.Selector.capeName = "cape";
                    }
               }

               if (this.target == 56) {
                    var6 = GuiCapeSelector.Selector.getCapeName();
                    var4 = -1;
                    switch(var6.hashCode()) {
                    case 3046099:
                         if (var6.equals("cape")) {
                              var4 = 1;
                         }
                         break;
                    case 3441014:
                         if (var6.equals("pink")) {
                              var4 = 0;
                         }
                    }

                    switch(var4) {
                    case 0:
                         GuiCapeSelector.Selector.capeName = "pink";
                         break;
                    case 1:
                         GuiCapeSelector.Selector.capeName = "cape";
                    }
               }
          }

     }

     public void draw(int mouseX, int mouseY, Color color) {
          GlStateManager.pushMatrix();
          GlStateManager.disableBlend();
          this.hoverAnimation(mouseX, mouseY);
          if (this.ani > 0) {
               DrawHelper.drawImage(this.image, (float)(this.x - this.ani), (float)(this.y - this.ani), (float)(this.width + this.ani * 2), (float)(this.height + this.ani * 2), new Color(156, 156, 156, 255));
          } else {
               DrawHelper.drawImage(this.image, (float)this.x, (float)this.y, (float)this.width, (float)this.height, color);
          }

          GlStateManager.popMatrix();
     }

     protected boolean isHovered(int mouseX, int mouseY) {
          return MouseHelper.isHovered((double)this.x, (double)this.y, (double)(this.x + this.width), (double)(this.y + this.height), mouseX, mouseY);
     }
}
