package wtf.rich.client.features;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import wtf.rich.client.features.impl.combat.AntiBot;
import wtf.rich.client.features.impl.combat.AntiCrystal;
import wtf.rich.client.features.impl.combat.AutoArmor;
import wtf.rich.client.features.impl.combat.AutoGapple;
import wtf.rich.client.features.impl.combat.AutoPotion;
import wtf.rich.client.features.impl.combat.AutoTotem;
import wtf.rich.client.features.impl.combat.FastBow;
import wtf.rich.client.features.impl.combat.HitBox;
import wtf.rich.client.features.impl.combat.HitSounds;
import wtf.rich.client.features.impl.combat.KillAura;
import wtf.rich.client.features.impl.combat.NoFriendDamage;
import wtf.rich.client.features.impl.combat.Reach;
import wtf.rich.client.features.impl.combat.ShieldDesync;
import wtf.rich.client.features.impl.combat.SuperKnockBack;
import wtf.rich.client.features.impl.combat.TriggerBot;
import wtf.rich.client.features.impl.combat.Velocity;
import wtf.rich.client.features.impl.display.ClickGUI;
import wtf.rich.client.features.impl.display.ClientFont;
import wtf.rich.client.features.impl.display.FeatureList;
import wtf.rich.client.features.impl.display.HUD;
import wtf.rich.client.features.impl.display.Hotbar;
import wtf.rich.client.features.impl.display.MainMenu;
import wtf.rich.client.features.impl.display.Notifications;
import wtf.rich.client.features.impl.display.Watermark;
import wtf.rich.client.features.impl.misc.DiscordRPC;
import wtf.rich.client.features.impl.misc.FastWorldLoad;
import wtf.rich.client.features.impl.misc.MCF;
import wtf.rich.client.features.impl.misc.ModuleSoundAlert;
import wtf.rich.client.features.impl.misc.Panic;
import wtf.rich.client.features.impl.movement.AirJump;
import wtf.rich.client.features.impl.movement.AutoSprint;
import wtf.rich.client.features.impl.movement.FastClimb;
import wtf.rich.client.features.impl.movement.Flight;
import wtf.rich.client.features.impl.movement.Jesus;
import wtf.rich.client.features.impl.movement.LongJump;
import wtf.rich.client.features.impl.movement.NoFall;
import wtf.rich.client.features.impl.movement.NoSlowDown;
import wtf.rich.client.features.impl.movement.NoWeb;
import wtf.rich.client.features.impl.movement.SafeWalk;
import wtf.rich.client.features.impl.movement.Speed;
import wtf.rich.client.features.impl.movement.Step;
import wtf.rich.client.features.impl.movement.Strafe;
import wtf.rich.client.features.impl.movement.TargetStrafe;
import wtf.rich.client.features.impl.movement.Timer;
import wtf.rich.client.features.impl.movement.WaterLeave;
import wtf.rich.client.features.impl.movement.WaterSpeed;
import wtf.rich.client.features.impl.player.AutoAuth;
import wtf.rich.client.features.impl.player.AutoFarm;
import wtf.rich.client.features.impl.player.DeathCoordinates;
import wtf.rich.client.features.impl.player.FreeCam;
import wtf.rich.client.features.impl.player.GuiMove;
import wtf.rich.client.features.impl.player.ItemScroller;
import wtf.rich.client.features.impl.player.KeepSprint;
import wtf.rich.client.features.impl.player.MiddleClickPearl;
import wtf.rich.client.features.impl.player.NoClip;
import wtf.rich.client.features.impl.player.NoDelay;
import wtf.rich.client.features.impl.player.NoInteract;
import wtf.rich.client.features.impl.player.NoPush;
import wtf.rich.client.features.impl.player.NoServerRotation;
import wtf.rich.client.features.impl.player.Scaffold;
import wtf.rich.client.features.impl.player.SpeedMine;
import wtf.rich.client.features.impl.player.Spider;
import wtf.rich.client.features.impl.player.XCarry;
import wtf.rich.client.features.impl.visuals.ArmorHUD;
import wtf.rich.client.features.impl.visuals.Breadcrumbs;
import wtf.rich.client.features.impl.visuals.Chams;
import wtf.rich.client.features.impl.visuals.ChestEsp;
import wtf.rich.client.features.impl.visuals.ChinaHat;
import wtf.rich.client.features.impl.visuals.Crosshair;
import wtf.rich.client.features.impl.visuals.CustomModel;
import wtf.rich.client.features.impl.visuals.ESP;
import wtf.rich.client.features.impl.visuals.EnchantmentColor;
import wtf.rich.client.features.impl.visuals.EntityESP;
import wtf.rich.client.features.impl.visuals.FogColor;
import wtf.rich.client.features.impl.visuals.FullBright;
import wtf.rich.client.features.impl.visuals.ItemESP;
import wtf.rich.client.features.impl.visuals.JumpCircle;
import wtf.rich.client.features.impl.visuals.NameProtect;
import wtf.rich.client.features.impl.visuals.NameTags;
import wtf.rich.client.features.impl.visuals.NightMode;
import wtf.rich.client.features.impl.visuals.NoRender;
import wtf.rich.client.features.impl.visuals.PearlESP;
import wtf.rich.client.features.impl.visuals.ScoreBoard;
import wtf.rich.client.features.impl.visuals.SwingAnimations;
import wtf.rich.client.features.impl.visuals.TargetESP;
import wtf.rich.client.features.impl.visuals.Tracers;
import wtf.rich.client.features.impl.visuals.ViewModel;
import wtf.rich.client.features.impl.visuals.Weather;

public class FeatureDirector {
     public static ArrayList features = new ArrayList();

     public FeatureDirector() {
          features.add(new NoFriendDamage());
          features.add(new SuperKnockBack());
          features.add(new TriggerBot());
          features.add(new AutoArmor());
          features.add(new AutoGapple());
          features.add(new AutoTotem());
          features.add(new KillAura());
          features.add(new AntiBot());
          features.add(new AutoPotion());
          features.add(new HitSounds());
          features.add(new ShieldDesync());
          features.add(new Velocity());
          features.add(new HitBox());
          features.add(new AntiCrystal());
          features.add(new FastBow());
          features.add(new TargetStrafe());
          features.add(new Reach());
          features.add(new NoSlowDown());
          features.add(new WaterSpeed());
          features.add(new AutoSprint());
          features.add(new SafeWalk());
          features.add(new Strafe());
          features.add(new AirJump());
          features.add(new GuiMove());
          features.add(new NoFall());
          features.add(new FastClimb());
          features.add(new NoWeb());
          features.add(new LongJump());
          features.add(new Flight());
          features.add(new Timer());
          features.add(new Speed());
          features.add(new Jesus());
          features.add(new Step());
          features.add(new SwingAnimations());
          features.add(new Breadcrumbs());
          features.add(new NameProtect());
          features.add(new NightMode());
          features.add(new ChestEsp());
          features.add(new PearlESP());
          features.add(new EnchantmentColor());
          features.add(new CustomModel());
          features.add(new Crosshair());
          features.add(new WaterLeave());
          features.add(new FullBright());
          features.add(new JumpCircle());
          features.add(new ScoreBoard());
          features.add(new ArmorHUD());
          features.add(new ItemESP());
          features.add(new FogColor());
          features.add(new Weather());
          features.add(new ViewModel());
          features.add(new TargetESP());
          features.add(new ChinaHat());
          features.add(new NameTags());
          features.add(new NoRender());
          features.add(new Tracers());
          features.add(new ESP());
          features.add(new EntityESP());
          features.add(new Chams());
          features.add(new MiddleClickPearl());
          features.add(new KeepSprint());
          features.add(new NoInteract());
          features.add(new NoDelay());
          features.add(new XCarry());
          features.add(new Spider());
          features.add(new ItemScroller());
          features.add(new SpeedMine());
          features.add(new DeathCoordinates());
          features.add(new FreeCam());
          features.add(new AutoAuth());
          features.add(new NoServerRotation());
          features.add(new AutoFarm());
          features.add(new Scaffold());
          features.add(new NoPush());
          features.add(new NoClip());
          features.add(new ModuleSoundAlert());
          features.add(new FastWorldLoad());
          features.add(new Panic());
          features.add(new DiscordRPC());
          features.add(new MCF());
          features.add(new Watermark());
          features.add(new ClickGUI());
          features.add(new FeatureList());
          features.add(new MainMenu());
          features.add(new ClientFont());
          features.add(new HUD());
          features.add(new Notifications());
          features.add(new Hotbar());
          features.sort(Comparator.comparingInt((m) -> {
               return Minecraft.getMinecraft().neverlose500_15.getStringWidth(((Feature)m).getLabel());
          }).reversed());
     }

     public List getFeatureList() {
          return features;
     }

     public List getFeaturesForCategory(Category category) {
          List featureList = new ArrayList();
          Iterator var3 = this.getFeatureList().iterator();

          while(var3.hasNext()) {
               Feature feature = (Feature)var3.next();
               if (feature.getCategory() == category) {
                    featureList.add(feature);
               }
          }

          return featureList;
     }

     public Feature getFeatureByClass(Class classFeature) {
          Iterator var2 = this.getFeatureList().iterator();

          Feature feature;
          do {
               if (!var2.hasNext()) {
                    return null;
               }

               feature = (Feature)var2.next();
          } while(feature == null || feature.getClass() != classFeature);

          return feature;
     }

     public Feature getFeatureByLabel(String name) {
          Iterator var2 = this.getFeatureList().iterator();

          Feature feature;
          do {
               if (!var2.hasNext()) {
                    return null;
               }

               feature = (Feature)var2.next();
          } while(!feature.getLabel().equals(name));

          return feature;
     }
}
