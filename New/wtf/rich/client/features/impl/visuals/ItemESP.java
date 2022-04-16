package wtf.rich.client.features.impl.visuals;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.ColorSetting;

public class ItemESP extends Feature {
     private BooleanSetting tag = new BooleanSetting("Tag", true, () -> {
          return true;
     });
     private BooleanSetting border = new BooleanSetting("Full Box", true, () -> {
          return true;
     });
     private ColorSetting borderColor = new ColorSetting("Border Color", (new Color(16777215)).getRGB(), () -> {
          return this.border.getBoolValue();
     });

     public ItemESP() {
          super("ItemESP", "Отображение айтемов", 0, Category.VISUALS);
          this.addSettings(new Setting[]{this.tag, this.border, this.borderColor});
     }

     @EventTarget
     public void onRender(Event3D event) {
          Iterator var2 = mc.world.loadedEntityList.iterator();

          while(var2.hasNext()) {
               Entity e = (Entity)var2.next();
               if (e instanceof EntityItem) {
                    double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)event.getPartialTicks() - RenderManager.renderPosX - 0.1D;
                    double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)event.getPartialTicks() - RenderManager.renderPosY;
                    double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)event.getPartialTicks() - RenderManager.renderPosZ - 0.15D;
                    if (this.tag.getBoolValue()) {
                         GL11.glPushMatrix();
                         GL11.glDisable(2929);
                         GL11.glDisable(3553);
                         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                         GlStateManager.disableLighting();
                         GlStateManager.enableBlend();
                         float size = Math.min(Math.max(1.2F * Minecraft.getMinecraft().player.getDistanceToEntity(e) * 0.15F, 1.25F), 6.0F) * 0.014F;
                         GL11.glTranslatef((float)x, (float)y + e.height + 0.5F, (float)z);
                         GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                         GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                         GlStateManager.rotate(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                         GL11.glScalef(-size, -size, size);
                         Gui.drawRect((double)(-Minecraft.fontRendererObj.getStringWidth(((EntityItem)e).getEntityItem().getDisplayName()) / 2 - 2), -2.0D, (double)(Minecraft.fontRendererObj.getStringWidth(((EntityItem)e).getEntityItem().getDisplayName()) / 2 + 2), 9.0D, Integer.MIN_VALUE);
                         Minecraft.fontRendererObj.drawStringWithShadow(((EntityItem)e).getEntityItem().getDisplayName(), (float)(-Minecraft.fontRendererObj.getStringWidth(((EntityItem)e).getEntityItem().getDisplayName()) / 2), 0.0F, -1);
                         GlStateManager.resetColor();
                         GL11.glEnable(3553);
                         GL11.glEnable(2929);
                         GL11.glPopMatrix();
                    }

                    if (this.border.getBoolValue()) {
                         DrawHelper.drawEntityBox(e, new Color(this.borderColor.getColorValue()), true, 0.2F);
                    }
               }
          }

     }
}
