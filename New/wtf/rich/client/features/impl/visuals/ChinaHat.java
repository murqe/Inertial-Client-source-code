package wtf.rich.client.features.impl.visuals;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.Event3D;
import wtf.rich.api.utils.render.ClientHelper;
import wtf.rich.api.utils.render.DrawHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class ChinaHat extends Feature {
     public ChinaHat() {
          super("ChinaHat", "Показывает китайскую шляпу", 0, Category.VISUALS);
     }

     @EventTarget
     public void asf(Event3D event) {
          ItemStack stack = mc.player.getEquipmentInSlot(4);
          double height = stack.getItem() instanceof ItemArmor ? (mc.player.isSneaking() ? -0.1D : 0.12D) : (mc.player.isSneaking() ? -0.22D : 0.0D);
          if (mc.gameSettings.thirdPersonView == 1 || mc.gameSettings.thirdPersonView == 2) {
               GlStateManager.pushMatrix();
               GlStateManager.enableBlend();
               GL11.glBlendFunc(770, 771);
               GlStateManager.disableDepth();
               GlStateManager.disableTexture2D();
               DrawHelper.enableSmoothLine(2.5F);
               GL11.glShadeModel(7425);
               GL11.glDisable(2884);
               GL11.glEnable(2929);
               GL11.glTranslatef(0.0F, (float)((double)mc.player.height + height), 0.0F);
               GL11.glRotatef(-mc.player.rotationYaw, 0.0F, 1.0F, 0.0F);
               GL11.glBegin(2);

               float i;
               for(i = 0.0F; (double)i < 360.5D; ++i) {
                    DrawHelper.glColor(ClientHelper.getClientColor(5.0F, i, 5), 250);
                    GL11.glVertex3d(Math.cos((double)i * 3.141592653589793D / 180.0D) * 0.66D, 0.0D, Math.sin((double)i * 3.141592653589793D / 180.0D) * 0.66D);
               }

               GL11.glEnd();
               GL11.glBegin(6);
               DrawHelper.glColor(ClientHelper.getClientColor(5.0F, 1.0F, 5), 160);
               GL11.glVertex3d(0.0D, 0.3D, 0.0D);

               for(i = 0.0F; (double)i < 360.5D; ++i) {
                    DrawHelper.glColor(ClientHelper.getClientColor(i / 16.0F, i, 5), 195);
                    GL11.glVertex3d(Math.cos((double)i * 3.141592653589793D / 180.0D) * 0.66D, 0.0D, Math.sin((double)i * 3.141592653589793D / 180.0D) * 0.66D);
               }

               GL11.glVertex3d(0.0D, 0.3D, 0.0D);
               GL11.glEnd();
               GlStateManager.enableAlpha();
               DrawHelper.disableSmoothLine();
               GL11.glShadeModel(7424);
               GL11.glEnable(2884);
               GlStateManager.enableTexture2D();
               GlStateManager.enableDepth();
               GlStateManager.disableBlend();
               GlStateManager.resetColor();
               GlStateManager.popMatrix();
          }

     }

     public void onEnable() {
          super.onEnable();
     }

     public void onDisable() {
          super.onDisable();
     }
}
