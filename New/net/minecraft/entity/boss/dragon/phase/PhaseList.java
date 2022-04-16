package net.minecraft.entity.boss.dragon.phase;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.entity.boss.EntityDragon;

public class PhaseList {
     private static PhaseList[] phases = new PhaseList[0];
     public static final PhaseList HOLDING_PATTERN = create(PhaseHoldingPattern.class, "HoldingPattern");
     public static final PhaseList STRAFE_PLAYER = create(PhaseStrafePlayer.class, "StrafePlayer");
     public static final PhaseList LANDING_APPROACH = create(PhaseLandingApproach.class, "LandingApproach");
     public static final PhaseList LANDING = create(PhaseLanding.class, "Landing");
     public static final PhaseList TAKEOFF = create(PhaseTakeoff.class, "Takeoff");
     public static final PhaseList SITTING_FLAMING = create(PhaseSittingFlaming.class, "SittingFlaming");
     public static final PhaseList SITTING_SCANNING = create(PhaseSittingScanning.class, "SittingScanning");
     public static final PhaseList SITTING_ATTACKING = create(PhaseSittingAttacking.class, "SittingAttacking");
     public static final PhaseList CHARGING_PLAYER = create(PhaseChargingPlayer.class, "ChargingPlayer");
     public static final PhaseList DYING = create(PhaseDying.class, "Dying");
     public static final PhaseList HOVER = create(PhaseHover.class, "Hover");
     private final Class clazz;
     private final int id;
     private final String name;

     private PhaseList(int idIn, Class clazzIn, String nameIn) {
          this.id = idIn;
          this.clazz = clazzIn;
          this.name = nameIn;
     }

     public IPhase createPhase(EntityDragon dragon) {
          try {
               Constructor constructor = this.getConstructor();
               return (IPhase)constructor.newInstance(dragon);
          } catch (Exception var3) {
               throw new Error(var3);
          }
     }

     protected Constructor getConstructor() throws NoSuchMethodException {
          return this.clazz.getConstructor(EntityDragon.class);
     }

     public int getId() {
          return this.id;
     }

     public String toString() {
          return this.name + " (#" + this.id + ")";
     }

     public static PhaseList getById(int p_188738_0_) {
          return p_188738_0_ >= 0 && p_188738_0_ < phases.length ? phases[p_188738_0_] : HOLDING_PATTERN;
     }

     public static int getTotalPhases() {
          return phases.length;
     }

     private static PhaseList create(Class phaseIn, String nameIn) {
          PhaseList phaselist = new PhaseList(phases.length, phaseIn, nameIn);
          phases = (PhaseList[])((PhaseList[])Arrays.copyOf(phases, phases.length + 1));
          phases[phaselist.getId()] = phaselist;
          return phaselist;
     }
}
