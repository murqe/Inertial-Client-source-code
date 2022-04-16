package wtf.rich.client.features.impl.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventPreMotionUpdate;
import wtf.rich.api.event.event.EventReceivePacket;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.combat.KillAura;
import wtf.rich.client.ui.settings.Setting;
import wtf.rich.client.ui.settings.impl.BooleanSetting;
import wtf.rich.client.ui.settings.impl.NumberSetting;

public class TargetStrafe extends Feature {
     private final NumberSetting range = new NumberSetting("Strafe Distance", 2.4F, 0.1F, 6.0F, 0.1F, () -> {
          return true;
     });
     private final NumberSetting spd = new NumberSetting("Strafe Speed", 0.23F, 0.1F, 2.0F, 0.01F, () -> {
          return true;
     });
     private final NumberSetting boostValue;
     private final NumberSetting boostTicks;
     private final NumberSetting speedIfUsing;
     private final BooleanSetting reversed;
     private final BooleanSetting boost = new BooleanSetting("Hurt Boost", false, () -> {
          return true;
     });
     private final BooleanSetting autoJump;
     private final BooleanSetting usingItemCheck;
     private final BooleanSetting autoShift;
     private final BooleanSetting lagbackCheck = new BooleanSetting("Lagback Check", "Отключает модуль если вас флагнуло на сервере", false, () -> {
          return true;
     });
     private float wrap = 0.0F;
     private boolean switchDir = true;

     public TargetStrafe() {
          super("TargetStrafe", "Стрейфит вокруг сущностей", 0, Category.MOVEMENT);
          BooleanSetting var10008 = this.boost;
          var10008.getClass();
          var10008.getClass();
          this.boostValue = new NumberSetting("Boost Value", 0.5F, 0.1F, 4.0F, 0.01F, var10008::getBoolValue);
          var10008 = this.boost;
          var10008.getClass();
          var10008.getClass();
          this.boostTicks = new NumberSetting("Boost Ticks", 8.0F, 0.0F, 9.0F, 1.0F, var10008::getBoolValue);
          this.reversed = new BooleanSetting("Keep Distance", false, () -> {
               return true;
          });
          this.autoJump = new BooleanSetting("AutoJump", true, () -> {
               return true;
          });
          this.autoShift = new BooleanSetting("AutoShift", false, () -> {
               return true;
          });
          this.usingItemCheck = new BooleanSetting("Using Item Check", false, () -> {
               return true;
          });
          var10008 = this.usingItemCheck;
          var10008.getClass();
          var10008.getClass();
          this.speedIfUsing = new NumberSetting("Speed if using", 0.1F, 0.1F, 2.0F, 0.01F, var10008::getBoolValue);
          this.addSettings(new Setting[]{this.range, this.spd, this.boost, this.boostTicks, this.boostValue, this.reversed, this.autoJump, this.autoShift, this.usingItemCheck, this.speedIfUsing, this.lagbackCheck});
     }

     public void onEnable() {
          this.wrap = 0.0F;
          this.switchDir = true;
          super.onEnable();
     }

     @EventTarget
     public void onLagbackTargetStrafe(EventReceivePacket e) {
          if (e.getPacket() instanceof SPacketPlayerPosLook && this.lagbackCheck.getBoolValue()) {
               this.toggle();
          }

     }

     public boolean needToSwitch(double x, double z) {
          if (!mc.player.isCollidedHorizontally && !mc.gameSettings.keyBindLeft.isPressed() && !mc.gameSettings.keyBindRight.isPressed()) {
               for(int i = (int)(mc.player.posY + 4.0D); i >= 0; --i) {
                    BlockPos playerPos = new BlockPos(x, (double)i, z);
                    if (mc.world.getBlockState(playerPos).getBlock().equals(Blocks.LAVA) || mc.world.getBlockState(playerPos).getBlock().equals(Blocks.FIRE)) {
                         return true;
                    }

                    if (!mc.world.isAirBlock(playerPos)) {
                         return false;
                    }
               }

               return true;
          } else {
               return true;
          }
     }

     private float toDegree(double x, double z) {
          return (float)(Math.atan2(z - mc.player.posZ, x - mc.player.posX) * 180.0D / 3.141592653589793D) - 90.0F;
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (KillAura.target != null && Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled() && mc.player.getDistanceToEntity(KillAura.target) <= KillAura.range.getNumberValue() && this.autoShift.getBoolValue()) {
               mc.gameSettings.keyBindSneak.setPressed(this.isToggled() && KillAura.target != null && (double)mc.player.fallDistance > (double)KillAura.critFallDistance.getNumberValue() + 0.1D);
          }

     }

     @EventTarget
     public void onPreMotion(EventPreMotionUpdate event) {
          if (KillAura.target != null) {
               this.setSuffix("" + this.range.getNumberValue());
               if (Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled() && mc.player.getDistanceToEntity(KillAura.target) <= KillAura.range.getNumberValue()) {
                    if (KillAura.target.getHealth() > 0.0F && this.autoJump.getBoolValue() && Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled() && Main.instance.featureDirector.getFeatureByClass(TargetStrafe.class).isToggled() && mc.player.onGround) {
                         mc.player.jump();
                    }

                    if (KillAura.target.getHealth() > 0.0F) {
                         EntityLivingBase target = KillAura.target;
                         if (target == null || mc.player.ticksExisted < 20) {
                              return;
                         }

                         float speed = (float)mc.player.hurtTime > this.boostTicks.getNumberValue() && this.boost.getBoolValue() && !mc.player.onGround ? this.boostValue.getNumberValue() : ((mc.player.isUsingItem() || mc.gameSettings.keyBindUseItem.isKeyDown()) && this.usingItemCheck.getBoolValue() ? this.speedIfUsing.getNumberValue() : this.spd.getNumberValue());
                         this.wrap = (float)Math.atan2(mc.player.posZ - target.posZ, mc.player.posX - target.posX);
                         this.wrap += this.switchDir ? speed / mc.player.getDistanceToEntity(target) : -(speed / mc.player.getDistanceToEntity(target));
                         double x = target.posX + (double)this.range.getNumberValue() * Math.cos((double)this.wrap);
                         double z = target.posZ + (double)this.range.getNumberValue() * Math.sin((double)this.wrap);
                         if (this.needToSwitch(x, z)) {
                              this.switchDir = !this.switchDir;
                              this.wrap += 2.0F * (this.switchDir ? speed / mc.player.getDistanceToEntity(target) : -(speed / mc.player.getDistanceToEntity(target)));
                              x = target.posX + (double)this.range.getNumberValue() * Math.cos((double)this.wrap);
                              z = target.posZ + (double)this.range.getNumberValue() * Math.sin((double)this.wrap);
                         }

                         if ((float)mc.player.hurtTime > this.boostTicks.getNumberValue() && this.boost.getBoolValue() && !mc.player.onGround) {
                              EntityPlayerSP var10000 = mc.player;
                              var10000.jumpMovementFactor *= 60.0F;
                         }

                         float searchValue = Main.instance.featureDirector.getFeatureByClass(TargetStrafe.class).isToggled() && this.reversed.getBoolValue() && mc.player.getDistanceToEntity(KillAura.target) < this.range.getNumberValue() ? -90.0F : 0.0F;
                         float reversedValue = !mc.gameSettings.keyBindLeft.isKeyDown() && !mc.gameSettings.keyBindRight.isKeyDown() && !mc.player.isCollidedHorizontally ? searchValue : 0.0F;
                         mc.player.motionX = (double)speed * -Math.sin((double)((float)Math.toRadians((double)this.toDegree(x + (double)reversedValue, z + (double)reversedValue))));
                         mc.player.motionZ = (double)speed * Math.cos((double)((float)Math.toRadians((double)this.toDegree(x + (double)reversedValue, z + (double)reversedValue))));
                    }
               }
          }

     }
}
