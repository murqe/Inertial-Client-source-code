package me.rich.module;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import me.rich.module.combat.AntiBot;
import me.rich.module.combat.AutoArmor;
import me.rich.module.combat.AutoGApple;
import me.rich.module.combat.AutoPotion;
import me.rich.module.combat.AutoShiftTap;
import me.rich.module.combat.AutoTotem;
import me.rich.module.combat.Criticals;
import me.rich.module.combat.HitBox;
import me.rich.module.combat.KillAura;
import me.rich.module.combat.NoFriendDamage;
import me.rich.module.combat.TargetStrafe;
import me.rich.module.combat.TotemDetector;
import me.rich.module.combat.TriggerBot;
import me.rich.module.combat.Velocity;
import me.rich.module.hud.ArreyList;
import me.rich.module.hud.HUD;
import me.rich.module.hud.KeyBinds;
import me.rich.module.hud.Logo;
import me.rich.module.hud.SessionInfo;
import me.rich.module.misc.AutoAccept;
import me.rich.module.misc.Disabler;
import me.rich.module.misc.DiscordRPC;
import me.rich.module.misc.Indicators;
import me.rich.module.misc.MiddleClickFriend;
import me.rich.module.misc.NoSRotations;
import me.rich.module.misc.PortalGodMode;
import me.rich.module.misc.TeleportBack;
import me.rich.module.misc.VClip;
import me.rich.module.movement.AutoSprint;
import me.rich.module.movement.FastLadder;
import me.rich.module.movement.Flight;
import me.rich.module.movement.GuiWalk;
import me.rich.module.movement.LiquidWalk;
import me.rich.module.movement.LongJump;
import me.rich.module.movement.NoJumpDelay;
import me.rich.module.movement.NoSlowDown;
import me.rich.module.movement.Speed;
import me.rich.module.movement.Timer;
import me.rich.module.movement.WaterSpeed;
import me.rich.module.movement.WebLeave;
import me.rich.module.player.AirStuck;
import me.rich.module.player.AntiAFK;
import me.rich.module.player.AntiVoid;
import me.rich.module.player.AutoClicker;
import me.rich.module.player.AutoEat;
import me.rich.module.player.BedrockClip;
import me.rich.module.player.BoatFly;
import me.rich.module.player.FakeLag;
import me.rich.module.player.FastPlace;
import me.rich.module.player.FastUse;
import me.rich.module.player.FreeCam;
import me.rich.module.player.MiddleClickPearl;
import me.rich.module.player.NoClip;
import me.rich.module.player.NoFall;
import me.rich.module.player.NoInteract;
import me.rich.module.player.NoLag;
import me.rich.module.player.NoPush;
import me.rich.module.player.ParkourHelper;
import me.rich.module.player.ParticleTrails;
import me.rich.module.player.PearlNotifier;
import me.rich.module.player.PingSpoof;
import me.rich.module.player.SafeWalk;
import me.rich.module.player.SpeedMine;
import me.rich.module.player.Spider;
import me.rich.module.render.Animations;
import me.rich.module.render.ArmorHUD;
import me.rich.module.render.BlockHitAnimation;
import me.rich.module.render.ChestESP;
import me.rich.module.render.ChinaHat;
import me.rich.module.render.ClickGUI;
import me.rich.module.render.Crosshair;
import me.rich.module.render.ESP;
import me.rich.module.render.FullBright;
import me.rich.module.render.GlowESP;
import me.rich.module.render.JumpCircle;
import me.rich.module.render.NameTags;
import me.rich.module.render.NoBadEffects;
import me.rich.module.render.NoRender;
import me.rich.module.render.Radar;
import me.rich.module.render.SkyColor;
import me.rich.module.render.Tracers;
import me.rich.module.render.Trails;
import me.rich.module.render.Trajectories;
import me.rich.module.render.ViewModel;
import me.rich.module.render.WorldTime;

public class FeatureDirector {
      public static List modules = new CopyOnWriteArrayList();

      public FeatureDirector() {
            modules.add(new AutoPotion());
            modules.add(new AutoTotem());
            modules.add(new TotemDetector());
            modules.add(new Velocity());
            modules.add(new KillAura());
            modules.add(new NoFriendDamage());
            modules.add(new AutoShiftTap());
            modules.add(new AutoGApple());
            modules.add(new AutoArmor());
            modules.add(new Criticals());
            modules.add(new TargetStrafe());
            modules.add(new AntiBot());
            modules.add(new TriggerBot());
            modules.add(new HitBox());
            modules.add(new LiquidWalk());
            modules.add(new AutoSprint());
            modules.add(new WebLeave());
            modules.add(new GuiWalk());
            modules.add(new NoJumpDelay());
            modules.add(new FastLadder());
            modules.add(new Flight());
            modules.add(new Speed());
            modules.add(new LongJump());
            modules.add(new Timer());
            modules.add(new NoSlowDown());
            modules.add(new WaterSpeed());
            modules.add(new MiddleClickPearl());
            modules.add(new ParticleTrails());
            modules.add(new FreeCam());
            modules.add(new AntiAFK());
            modules.add(new AutoEat());
            modules.add(new NoPush());
            modules.add(new NoClip());
            modules.add(new AutoClicker());
            modules.add(new BedrockClip());
            modules.add(new ParkourHelper());
            modules.add(new SpeedMine());
            modules.add(new FastUse());
            modules.add(new NoFall());
            modules.add(new NoLag());
            modules.add(new PingSpoof());
            modules.add(new AirStuck());
            modules.add(new AntiVoid());
            modules.add(new NoInteract());
            modules.add(new BoatFly());
            modules.add(new PearlNotifier());
            modules.add(new FastPlace());
            modules.add(new FakeLag());
            modules.add(new SafeWalk());
            modules.add(new Spider());
            modules.add(new Animations());
            modules.add(new ArmorHUD());
            modules.add(new WorldTime());
            modules.add(new ViewModel());
            modules.add(new FullBright());
            modules.add(new ChestESP());
            modules.add(new NoRender());
            modules.add(new ClickGUI());
            modules.add(new Crosshair());
            modules.add(new Tracers());
            modules.add(new ESP());
            modules.add(new ChinaHat());
            modules.add(new Trails());
            modules.add(new NoBadEffects());
            modules.add(new Trajectories());
            modules.add(new GlowESP());
            modules.add(new Radar());
            modules.add(new NameTags());
            modules.add(new SkyColor());
            modules.add(new JumpCircle());
            modules.add(new MiddleClickFriend());
            modules.add(new VClip());
            modules.add(new Disabler());
            modules.add(new DiscordRPC());
            modules.add(new PortalGodMode());
            modules.add(new BlockHitAnimation());
            modules.add(new TeleportBack());
            modules.add(new NoSRotations());
            modules.add(new AutoAccept());
            modules.add(new Indicators());
            modules.add(new HUD());
            modules.add(new ArreyList());
            modules.add(new Logo());
            modules.add(new KeyBinds());
            modules.add(new SessionInfo());
      }

      public static List getModules() {
            return modules;
      }

      public Feature getModuleByName(String name) {
            return (Feature)modules.stream().filter((module) -> {
                  return module.getName().equalsIgnoreCase(name);
            }).findFirst().orElse((Object)null);
      }

      public List getCheats(Category cheatType) {
            return (List)getModules().stream().filter((cheat) -> {
                  return cheat.getCategory() == cheatType;
            }).collect(Collectors.toList());
      }

      public Feature getModule(Class moduleClass) {
            Iterator var2 = modules.iterator();

            Feature module;
            do {
                  if (!var2.hasNext()) {
                        return null;
                  }

                  module = (Feature)var2.next();
            } while(module.getClass() != moduleClass);

            return module;
      }

      public Feature[] getModulesInCategory(Category category) {
            return (Feature[])((Feature[])modules.stream().filter((module) -> {
                  return module.getCategory() == category;
            }).toArray((x$0) -> {
                  return new Feature[x$0];
            }));
      }
}
