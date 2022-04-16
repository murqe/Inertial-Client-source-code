package wtf.rich.client.features.impl.player;

import net.minecraft.client.gui.GuiChat;
import org.lwjgl.input.Keyboard;
import wtf.rich.api.event.EventTarget;
import wtf.rich.api.event.event.EventUpdate;
import wtf.rich.client.features.Category;
import wtf.rich.client.features.Feature;

public class GuiMove extends Feature {
     public GuiMove() {
          super("GuiWalk", "Позволяет ходить в открытом контейнере (инвентарь,сундук и т.д)", 0, Category.PLAYER);
     }

     @EventTarget
     public void onUpdate(EventUpdate event) {
          if (!(mc.currentScreen instanceof GuiChat)) {
               mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
               mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
               mc.gameSettings.keyBindBack.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
               mc.gameSettings.keyBindLeft.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
               mc.gameSettings.keyBindRight.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
               mc.gameSettings.keyBindSprint.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindSprint.getKeyCode());
          }

     }

     public void onDisable() {
          mc.gameSettings.keyBindJump.pressed = false;
          mc.gameSettings.keyBindForward.pressed = false;
          mc.gameSettings.keyBindBack.pressed = false;
          mc.gameSettings.keyBindLeft.pressed = false;
          mc.gameSettings.keyBindRight.pressed = false;
          mc.gameSettings.keyBindSprint.pressed = false;
          super.onDisable();
     }

     public void onEnable() {
          super.onEnable();
     }
}
