package me.rich.changelogs;

import java.util.ArrayList;

public class ChangeLogMngr {
      public static ArrayList changeLogs = new ArrayList();

      public void setChangeLogs() {
            changeLogs.add(new ChangeLog("AntiBot.", ChangelogType.IMPROVED));
            changeLogs.add(new ChangeLog("HUD.", ChangelogType.IMPROVED));
            changeLogs.add(new ChangeLog("KillAura rotations.", ChangelogType.IMPROVED));
            changeLogs.add(new ChangeLog("Velocity [Mode: Cancel, Matrix].", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("NoSlowDown [Mode: Vanila, Matrix].", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("Flight [Mode: MotionGlide].", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("FogColor.", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("ArmorHUD.", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("Radar.", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("FeatureList.", ChangelogType.ADD));
            changeLogs.add(new ChangeLog("BindList.", ChangelogType.ADD));
      }

      public ArrayList getChangeLogs() {
            return changeLogs;
      }
}
