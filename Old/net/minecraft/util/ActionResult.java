package net.minecraft.util;

public class ActionResult {
      private final EnumActionResult type;
      private final Object result;

      public ActionResult(EnumActionResult typeIn, Object resultIn) {
            this.type = typeIn;
            this.result = resultIn;
      }

      public EnumActionResult getType() {
            return this.type;
      }

      public Object getResult() {
            return this.result;
      }
}
