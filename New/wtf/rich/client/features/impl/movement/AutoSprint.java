package wtf.rich.client.features.impl.movement;

import wtf.rich.Main;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.api.utils.movement.MovementHelper;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;
import wtf.rich.client.features.impl.combat.KillAura;
import wtf.rich.client.features.impl.player.Scaffold;

public class AutoSprint extends Feature {
     public AutoSprint() {
          super("AutoSprint", "Зажимает CTRL за вас, что бы быстро бежать", 0, Category.PLAYER);
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if ((!Main.instance.featureDirector.getFeatureByClass(Scaffold.class).isToggled() || !Scaffold.sprintoff.getBoolValue()) && (!Main.instance.featureDirector.getFeatureByClass(KillAura.class).isToggled() || !KillAura.stopSprint.getBoolValue() || KillAura.target == null)) {
               mc.player.setSprinting(MovementHelper.isMoving());
          }

     }
}
