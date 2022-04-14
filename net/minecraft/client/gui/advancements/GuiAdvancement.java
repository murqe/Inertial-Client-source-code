package net.minecraft.client.gui.advancements;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class GuiAdvancement extends Gui {
      private static final ResourceLocation field_191827_a = new ResourceLocation("textures/gui/advancements/widgets.png");
      private static final Pattern field_192996_f = Pattern.compile("(.+) \\S+");
      private final GuiAdvancementTab field_191828_f;
      private final Advancement field_191829_g;
      private final DisplayInfo field_191830_h;
      private final String field_191831_i;
      private final int field_191832_j;
      private final List field_192997_l;
      private final Minecraft field_191833_k;
      private GuiAdvancement field_191834_l;
      private final List field_191835_m = Lists.newArrayList();
      private AdvancementProgress field_191836_n;
      private final int field_191837_o;
      private final int field_191826_p;

      public GuiAdvancement(GuiAdvancementTab p_i47385_1_, Minecraft p_i47385_2_, Advancement p_i47385_3_, DisplayInfo p_i47385_4_) {
            this.field_191828_f = p_i47385_1_;
            this.field_191829_g = p_i47385_3_;
            this.field_191830_h = p_i47385_4_;
            this.field_191833_k = p_i47385_2_;
            this.field_191831_i = Minecraft.fontRendererObj.trimStringToWidth(p_i47385_4_.func_192297_a().getFormattedText(), 163);
            this.field_191837_o = MathHelper.floor(p_i47385_4_.func_192299_e() * 28.0F);
            this.field_191826_p = MathHelper.floor(p_i47385_4_.func_192296_f() * 27.0F);
            int i = p_i47385_3_.func_193124_g();
            int j = String.valueOf(i).length();
            int k = i > 1 ? Minecraft.fontRendererObj.getStringWidth("  ") + Minecraft.fontRendererObj.getStringWidth("0") * j * 2 + Minecraft.fontRendererObj.getStringWidth("/") : 0;
            int l = 29 + Minecraft.fontRendererObj.getStringWidth(this.field_191831_i) + k;
            String s = p_i47385_4_.func_193222_b().getFormattedText();
            this.field_192997_l = this.func_192995_a(s, l);

            String s1;
            for(Iterator var10 = this.field_192997_l.iterator(); var10.hasNext(); l = Math.max(l, Minecraft.fontRendererObj.getStringWidth(s1))) {
                  s1 = (String)var10.next();
            }

            this.field_191832_j = l + 3 + 5;
      }

      private List func_192995_a(String p_192995_1_, int p_192995_2_) {
            if (p_192995_1_.isEmpty()) {
                  return Collections.emptyList();
            } else {
                  Minecraft var10000 = this.field_191833_k;
                  List list = Minecraft.fontRendererObj.listFormattedStringToWidth(p_192995_1_, p_192995_2_);
                  if (list.size() < 2) {
                        return list;
                  } else {
                        String s = (String)list.get(0);
                        String s1 = (String)list.get(1);
                        var10000 = this.field_191833_k;
                        int i = Minecraft.fontRendererObj.getStringWidth(s + ' ' + s1.split(" ")[0]);
                        if (i - p_192995_2_ <= 10) {
                              var10000 = this.field_191833_k;
                              return Minecraft.fontRendererObj.listFormattedStringToWidth(p_192995_1_, i);
                        } else {
                              Matcher matcher = field_192996_f.matcher(s);
                              if (matcher.matches()) {
                                    var10000 = this.field_191833_k;
                                    int j = Minecraft.fontRendererObj.getStringWidth(matcher.group(1));
                                    if (p_192995_2_ - j <= 10) {
                                          var10000 = this.field_191833_k;
                                          return Minecraft.fontRendererObj.listFormattedStringToWidth(p_192995_1_, j);
                                    }
                              }

                              return list;
                        }
                  }
            }
      }

      @Nullable
      private GuiAdvancement func_191818_a(Advancement p_191818_1_) {
            do {
                  p_191818_1_ = p_191818_1_.func_192070_b();
            } while(p_191818_1_ != null && p_191818_1_.func_192068_c() == null);

            if (p_191818_1_ != null && p_191818_1_.func_192068_c() != null) {
                  return this.field_191828_f.func_191794_b(p_191818_1_);
            } else {
                  return null;
            }
      }

      public void func_191819_a(int p_191819_1_, int p_191819_2_, boolean p_191819_3_) {
            if (this.field_191834_l != null) {
                  int i = p_191819_1_ + this.field_191834_l.field_191837_o + 13;
                  int j = p_191819_1_ + this.field_191834_l.field_191837_o + 26 + 4;
                  int k = p_191819_2_ + this.field_191834_l.field_191826_p + 13;
                  int l = p_191819_1_ + this.field_191837_o + 13;
                  int i1 = p_191819_2_ + this.field_191826_p + 13;
                  int j1 = p_191819_3_ ? -16777216 : -1;
                  if (p_191819_3_) {
                        this.drawHorizontalLine(j, i, k - 1, j1);
                        this.drawHorizontalLine(j + 1, i, k, j1);
                        this.drawHorizontalLine(j, i, k + 1, j1);
                        this.drawHorizontalLine(l, j - 1, i1 - 1, j1);
                        this.drawHorizontalLine(l, j - 1, i1, j1);
                        this.drawHorizontalLine(l, j - 1, i1 + 1, j1);
                        this.drawVerticalLine(j - 1, i1, k, j1);
                        this.drawVerticalLine(j + 1, i1, k, j1);
                  } else {
                        this.drawHorizontalLine(j, i, k, j1);
                        this.drawHorizontalLine(l, j, i1, j1);
                        this.drawVerticalLine(j, i1, k, j1);
                  }
            }

            Iterator var10 = this.field_191835_m.iterator();

            while(var10.hasNext()) {
                  GuiAdvancement guiadvancement = (GuiAdvancement)var10.next();
                  guiadvancement.func_191819_a(p_191819_1_, p_191819_2_, p_191819_3_);
            }

      }

      public void func_191817_b(int p_191817_1_, int p_191817_2_) {
            if (!this.field_191830_h.func_193224_j() || this.field_191836_n != null && this.field_191836_n.func_192105_a()) {
                  float f = this.field_191836_n == null ? 0.0F : this.field_191836_n.func_192103_c();
                  AdvancementState advancementstate;
                  if (f >= 1.0F) {
                        advancementstate = AdvancementState.OBTAINED;
                  } else {
                        advancementstate = AdvancementState.UNOBTAINED;
                  }

                  this.field_191833_k.getTextureManager().bindTexture(field_191827_a);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  GlStateManager.enableBlend();
                  this.drawTexturedModalRect(p_191817_1_ + this.field_191837_o + 3, p_191817_2_ + this.field_191826_p, this.field_191830_h.func_192291_d().func_192309_b(), 128 + advancementstate.func_192667_a() * 26, 26, 26);
                  RenderHelper.enableGUIStandardItemLighting();
                  this.field_191833_k.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)null, this.field_191830_h.func_192298_b(), p_191817_1_ + this.field_191837_o + 8, p_191817_2_ + this.field_191826_p + 5);
            }

            Iterator var5 = this.field_191835_m.iterator();

            while(var5.hasNext()) {
                  GuiAdvancement guiadvancement = (GuiAdvancement)var5.next();
                  guiadvancement.func_191817_b(p_191817_1_, p_191817_2_);
            }

      }

      public void func_191824_a(AdvancementProgress p_191824_1_) {
            this.field_191836_n = p_191824_1_;
      }

      public void func_191822_a(GuiAdvancement p_191822_1_) {
            this.field_191835_m.add(p_191822_1_);
      }

      public void func_191821_a(int p_191821_1_, int p_191821_2_, float p_191821_3_, int p_191821_4_, int p_191821_5_) {
            boolean flag = p_191821_4_ + p_191821_1_ + this.field_191837_o + this.field_191832_j + 26 >= this.field_191828_f.func_193934_g().width;
            String s = this.field_191836_n == null ? null : this.field_191836_n.func_193126_d();
            int var10000;
            Minecraft var20;
            if (s == null) {
                  var10000 = 0;
            } else {
                  var20 = this.field_191833_k;
                  var10000 = Minecraft.fontRendererObj.getStringWidth(s);
            }

            int i = var10000;
            var10000 = 113 - p_191821_2_ - this.field_191826_p - 26;
            int var10002 = this.field_192997_l.size();
            Minecraft var10003 = this.field_191833_k;
            boolean flag1 = var10000 <= 6 + var10002 * Minecraft.fontRendererObj.FONT_HEIGHT;
            float f = this.field_191836_n == null ? 0.0F : this.field_191836_n.func_192103_c();
            int j = MathHelper.floor(f * (float)this.field_191832_j);
            AdvancementState advancementstate;
            AdvancementState advancementstate1;
            AdvancementState advancementstate2;
            if (f >= 1.0F) {
                  j = this.field_191832_j / 2;
                  advancementstate = AdvancementState.OBTAINED;
                  advancementstate1 = AdvancementState.OBTAINED;
                  advancementstate2 = AdvancementState.OBTAINED;
            } else if (j < 2) {
                  j = this.field_191832_j / 2;
                  advancementstate = AdvancementState.UNOBTAINED;
                  advancementstate1 = AdvancementState.UNOBTAINED;
                  advancementstate2 = AdvancementState.UNOBTAINED;
            } else if (j > this.field_191832_j - 2) {
                  j = this.field_191832_j / 2;
                  advancementstate = AdvancementState.OBTAINED;
                  advancementstate1 = AdvancementState.OBTAINED;
                  advancementstate2 = AdvancementState.UNOBTAINED;
            } else {
                  advancementstate = AdvancementState.OBTAINED;
                  advancementstate1 = AdvancementState.UNOBTAINED;
                  advancementstate2 = AdvancementState.UNOBTAINED;
            }

            int k = this.field_191832_j - j;
            this.field_191833_k.getTextureManager().bindTexture(field_191827_a);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableBlend();
            int l = p_191821_2_ + this.field_191826_p;
            int i1;
            if (flag) {
                  i1 = p_191821_1_ + this.field_191837_o - this.field_191832_j + 26 + 6;
            } else {
                  i1 = p_191821_1_ + this.field_191837_o;
            }

            int var10001 = this.field_192997_l.size();
            Minecraft var22 = this.field_191833_k;
            int j1 = 32 + var10001 * Minecraft.fontRendererObj.FONT_HEIGHT;
            if (!this.field_192997_l.isEmpty()) {
                  if (flag1) {
                        this.func_192994_a(i1, l + 26 - j1, this.field_191832_j, j1, 10, 200, 26, 0, 52);
                  } else {
                        this.func_192994_a(i1, l, this.field_191832_j, j1, 10, 200, 26, 0, 52);
                  }
            }

            this.drawTexturedModalRect(i1, l, 0, advancementstate.func_192667_a() * 26, j, 26);
            this.drawTexturedModalRect(i1 + j, l, 200 - k, advancementstate1.func_192667_a() * 26, k, 26);
            this.drawTexturedModalRect(p_191821_1_ + this.field_191837_o + 3, p_191821_2_ + this.field_191826_p, this.field_191830_h.func_192291_d().func_192309_b(), 128 + advancementstate2.func_192667_a() * 26, 26, 26);
            if (flag) {
                  var20 = this.field_191833_k;
                  Minecraft.fontRendererObj.drawString(this.field_191831_i, (float)(i1 + 5), (float)(p_191821_2_ + this.field_191826_p + 9), -1, true);
                  if (s != null) {
                        var20 = this.field_191833_k;
                        Minecraft.fontRendererObj.drawString(s, (float)(p_191821_1_ + this.field_191837_o - i), (float)(p_191821_2_ + this.field_191826_p + 9), -1, true);
                  }
            } else {
                  var20 = this.field_191833_k;
                  Minecraft.fontRendererObj.drawString(this.field_191831_i, (float)(p_191821_1_ + this.field_191837_o + 32), (float)(p_191821_2_ + this.field_191826_p + 9), -1, true);
                  if (s != null) {
                        var20 = this.field_191833_k;
                        Minecraft.fontRendererObj.drawString(s, (float)(p_191821_1_ + this.field_191837_o + this.field_191832_j - i - 5), (float)(p_191821_2_ + this.field_191826_p + 9), -1, true);
                  }
            }

            int k1;
            String var21;
            Minecraft var10005;
            float var23;
            FontRenderer var24;
            int var25;
            if (flag1) {
                  for(k1 = 0; k1 < this.field_192997_l.size(); ++k1) {
                        var20 = this.field_191833_k;
                        var24 = Minecraft.fontRendererObj;
                        var21 = (String)this.field_192997_l.get(k1);
                        var23 = (float)(i1 + 5);
                        var25 = l + 26 - j1 + 7;
                        var10005 = this.field_191833_k;
                        var24.drawString(var21, var23, (float)(var25 + k1 * Minecraft.fontRendererObj.FONT_HEIGHT), -5592406, false);
                  }
            } else {
                  for(k1 = 0; k1 < this.field_192997_l.size(); ++k1) {
                        var20 = this.field_191833_k;
                        var24 = Minecraft.fontRendererObj;
                        var21 = (String)this.field_192997_l.get(k1);
                        var23 = (float)(i1 + 5);
                        var25 = p_191821_2_ + this.field_191826_p + 9 + 17;
                        var10005 = this.field_191833_k;
                        var24.drawString(var21, var23, (float)(var25 + k1 * Minecraft.fontRendererObj.FONT_HEIGHT), -5592406, false);
                  }
            }

            RenderHelper.enableGUIStandardItemLighting();
            this.field_191833_k.getRenderItem().renderItemAndEffectIntoGUI((EntityLivingBase)null, this.field_191830_h.func_192298_b(), p_191821_1_ + this.field_191837_o + 8, p_191821_2_ + this.field_191826_p + 5);
      }

      protected void func_192994_a(int p_192994_1_, int p_192994_2_, int p_192994_3_, int p_192994_4_, int p_192994_5_, int p_192994_6_, int p_192994_7_, int p_192994_8_, int p_192994_9_) {
            this.drawTexturedModalRect(p_192994_1_, p_192994_2_, p_192994_8_, p_192994_9_, p_192994_5_, p_192994_5_);
            this.func_192993_a(p_192994_1_ + p_192994_5_, p_192994_2_, p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_);
            this.drawTexturedModalRect(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_, p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_, p_192994_5_, p_192994_5_);
            this.drawTexturedModalRect(p_192994_1_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_8_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_5_, p_192994_5_);
            this.func_192993_a(p_192994_1_ + p_192994_5_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_);
            this.drawTexturedModalRect(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_ + p_192994_4_ - p_192994_5_, p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_ + p_192994_7_ - p_192994_5_, p_192994_5_, p_192994_5_);
            this.func_192993_a(p_192994_1_, p_192994_2_ + p_192994_5_, p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_, p_192994_9_ + p_192994_5_, p_192994_6_, p_192994_7_ - p_192994_5_ - p_192994_5_);
            this.func_192993_a(p_192994_1_ + p_192994_5_, p_192994_2_ + p_192994_5_, p_192994_3_ - p_192994_5_ - p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_ + p_192994_5_, p_192994_9_ + p_192994_5_, p_192994_6_ - p_192994_5_ - p_192994_5_, p_192994_7_ - p_192994_5_ - p_192994_5_);
            this.func_192993_a(p_192994_1_ + p_192994_3_ - p_192994_5_, p_192994_2_ + p_192994_5_, p_192994_5_, p_192994_4_ - p_192994_5_ - p_192994_5_, p_192994_8_ + p_192994_6_ - p_192994_5_, p_192994_9_ + p_192994_5_, p_192994_6_, p_192994_7_ - p_192994_5_ - p_192994_5_);
      }

      protected void func_192993_a(int p_192993_1_, int p_192993_2_, int p_192993_3_, int p_192993_4_, int p_192993_5_, int p_192993_6_, int p_192993_7_, int p_192993_8_) {
            for(int i = 0; i < p_192993_3_; i += p_192993_7_) {
                  int j = p_192993_1_ + i;
                  int k = Math.min(p_192993_7_, p_192993_3_ - i);

                  for(int l = 0; l < p_192993_4_; l += p_192993_8_) {
                        int i1 = p_192993_2_ + l;
                        int j1 = Math.min(p_192993_8_, p_192993_4_ - l);
                        this.drawTexturedModalRect(j, i1, p_192993_5_, p_192993_6_, k, j1);
                  }
            }

      }

      public boolean func_191816_c(int p_191816_1_, int p_191816_2_, int p_191816_3_, int p_191816_4_) {
            if (this.field_191830_h.func_193224_j() && (this.field_191836_n == null || !this.field_191836_n.func_192105_a())) {
                  return false;
            } else {
                  int i = p_191816_1_ + this.field_191837_o;
                  int j = i + 26;
                  int k = p_191816_2_ + this.field_191826_p;
                  int l = k + 26;
                  return p_191816_3_ >= i && p_191816_3_ <= j && p_191816_4_ >= k && p_191816_4_ <= l;
            }
      }

      public void func_191825_b() {
            if (this.field_191834_l == null && this.field_191829_g.func_192070_b() != null) {
                  this.field_191834_l = this.func_191818_a(this.field_191829_g);
                  if (this.field_191834_l != null) {
                        this.field_191834_l.func_191822_a(this);
                  }
            }

      }

      public int func_191820_c() {
            return this.field_191826_p;
      }

      public int func_191823_d() {
            return this.field_191837_o;
      }
}
