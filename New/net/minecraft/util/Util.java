package net.minecraft.util;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Logger;

public class Util {
     public static Util.EnumOS getOSType() {
          String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
          if (s.contains("win")) {
               return Util.EnumOS.WINDOWS;
          } else if (s.contains("mac")) {
               return Util.EnumOS.OSX;
          } else if (s.contains("solaris")) {
               return Util.EnumOS.SOLARIS;
          } else if (s.contains("sunos")) {
               return Util.EnumOS.SOLARIS;
          } else if (s.contains("linux")) {
               return Util.EnumOS.LINUX;
          } else {
               return s.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN;
          }
     }

     @Nullable
     public static Object runTask(FutureTask task, Logger logger) {
          try {
               task.run();
               return task.get();
          } catch (ExecutionException var3) {
               logger.fatal("Error executing task", var3);
          } catch (InterruptedException var4) {
               logger.fatal("Error executing task", var4);
          }

          return null;
     }

     public static Object getLastElement(List list) {
          return list.get(list.size() - 1);
     }

     public static enum EnumOS {
          LINUX,
          SOLARIS,
          WINDOWS,
          OSX,
          UNKNOWN;
     }
}
