package wtf.rich.api.changelogs;

import java.util.ArrayList;

public class ChangeManager {
     public static ArrayList changeLogs = new ArrayList();

     public ArrayList getChangeLogs() {
          return changeLogs;
     }

     public ChangeManager() {
          changeLogs.add(new ChangeLog("Version 1.1", ChangeType.NONE));
          changeLogs.add(new ChangeLog("New ClickGui", ChangeType.ADD));
          changeLogs.add(new ChangeLog("New ArrayList", ChangeType.ADD));
          changeLogs.add(new ChangeLog("Trails module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("JumpCircle module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("ShaderESP module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("CustomModel module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("MainMenu settings", ChangeType.ADD));
          changeLogs.add(new ChangeLog("SwingAnimations module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("TargetESP Settings", ChangeType.ADD));
          changeLogs.add(new ChangeLog("NoFall Matrix", ChangeType.ADD));
          changeLogs.add(new ChangeLog("AutoPotion module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("Strafe module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("Speed Matrix Long", ChangeType.ADD));
          changeLogs.add(new ChangeLog("Jesus ReallyWorld", ChangeType.ADD));
          changeLogs.add(new ChangeLog("AutoArmor module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("HitSounds module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("AntiCrystal module", ChangeType.ADD));
          changeLogs.add(new ChangeLog("EntityESP module", ChangeType.RECODE));
          changeLogs.add(new ChangeLog("EntityESP module", ChangeType.RECODE));
          changeLogs.add(new ChangeLog("ChinaHat module", ChangeType.RECODE));
          changeLogs.add(new ChangeLog("KillAura ShieldBreaker", ChangeType.FIXED));
          changeLogs.add(new ChangeLog("Scaffold", ChangeType.FIXED));
          changeLogs.add(new ChangeLog("Speed Matrix", ChangeType.IMPROVED));
          changeLogs.add(new ChangeLog("Jesus Matrix", ChangeType.IMPROVED));
          changeLogs.add(new ChangeLog("Speed", ChangeType.IMPROVED));
          changeLogs.add(new ChangeLog("Chams", ChangeType.IMPROVED));
     }
}
