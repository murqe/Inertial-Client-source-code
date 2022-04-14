package net.minecraft.client.gui.toasts;

import com.google.common.collect.Queues;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.math.MathHelper;

public class GuiToast extends Gui {
      private final Minecraft field_191790_f;
      private final GuiToast.ToastInstance[] field_191791_g = new GuiToast.ToastInstance[5];
      private final Deque field_191792_h = Queues.newArrayDeque();

      public GuiToast(Minecraft p_i47388_1_) {
            this.field_191790_f = p_i47388_1_;
      }

      public void func_191783_a(ScaledResolution p_191783_1_) {
            if (!this.field_191790_f.gameSettings.hideGUI) {
                  RenderHelper.disableStandardItemLighting();

                  for(int i = 0; i < this.field_191791_g.length; ++i) {
                        GuiToast.ToastInstance toastinstance = this.field_191791_g[i];
                        if (toastinstance != null && toastinstance.func_193684_a(p_191783_1_.getScaledWidth(), i)) {
                              this.field_191791_g[i] = null;
                        }

                        if (this.field_191791_g[i] == null && !this.field_191792_h.isEmpty()) {
                              this.field_191791_g[i] = new GuiToast.ToastInstance((IToast)this.field_191792_h.removeFirst());
                        }
                  }
            }

      }

      @Nullable
      public IToast func_192990_a(Class p_192990_1_, Object p_192990_2_) {
            GuiToast.ToastInstance[] var3 = this.field_191791_g;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                  GuiToast.ToastInstance toastinstance = var3[var5];
                  if (toastinstance != null && p_192990_1_.isAssignableFrom(toastinstance.func_193685_a().getClass()) && toastinstance.func_193685_a().func_193652_b().equals(p_192990_2_)) {
                        return toastinstance.func_193685_a();
                  }
            }

            Iterator var7 = this.field_191792_h.iterator();

            IToast itoast;
            do {
                  if (!var7.hasNext()) {
                        return (IToast)null;
                  }

                  itoast = (IToast)var7.next();
            } while(!p_192990_1_.isAssignableFrom(itoast.getClass()) || !itoast.func_193652_b().equals(p_192990_2_));

            return itoast;
      }

      public void func_191788_b() {
            Arrays.fill(this.field_191791_g, (Object)null);
            this.field_191792_h.clear();
      }

      public void func_192988_a(IToast p_192988_1_) {
            this.field_191792_h.add(p_192988_1_);
      }

      public Minecraft func_192989_b() {
            return this.field_191790_f;
      }

      class ToastInstance {
            private final IToast field_193688_b;
            private long field_193689_c;
            private long field_193690_d;
            private IToast.Visibility field_193691_e;

            private ToastInstance(IToast p_i47483_2_) {
                  this.field_193689_c = -1L;
                  this.field_193690_d = -1L;
                  this.field_193691_e = IToast.Visibility.SHOW;
                  this.field_193688_b = p_i47483_2_;
            }

            public IToast func_193685_a() {
                  return this.field_193688_b;
            }

            private float func_193686_a(long p_193686_1_) {
                  float f = MathHelper.clamp((float)(p_193686_1_ - this.field_193689_c) / 600.0F, 0.0F, 1.0F);
                  f *= f;
                  return this.field_193691_e == IToast.Visibility.HIDE ? 1.0F - f : f;
            }

            public boolean func_193684_a(int p_193684_1_, int p_193684_2_) {
                  long i = Minecraft.getSystemTime();
                  if (this.field_193689_c == -1L) {
                        this.field_193689_c = i;
                        this.field_193691_e.func_194169_a(GuiToast.this.field_191790_f.getSoundHandler());
                  }

                  if (this.field_193691_e == IToast.Visibility.SHOW && i - this.field_193689_c <= 600L) {
                        this.field_193690_d = i;
                  }

                  GlStateManager.pushMatrix();
                  GlStateManager.translate((float)p_193684_1_ - 160.0F * this.func_193686_a(i), (float)(p_193684_2_ * 32), (float)(500 + p_193684_2_));
                  IToast.Visibility itoast$visibility = this.field_193688_b.func_193653_a(GuiToast.this, i - this.field_193690_d);
                  GlStateManager.popMatrix();
                  if (itoast$visibility != this.field_193691_e) {
                        this.field_193689_c = i - (long)((int)((1.0F - this.func_193686_a(i)) * 600.0F));
                        this.field_193691_e = itoast$visibility;
                        this.field_193691_e.func_194169_a(GuiToast.this.field_191790_f.getSoundHandler());
                  }

                  return this.field_193691_e == IToast.Visibility.HIDE && i - this.field_193689_c > 600L;
            }

            // $FF: synthetic method
            ToastInstance(IToast x1, Object x2) {
                  this(x1);
            }
      }
}
