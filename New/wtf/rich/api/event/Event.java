package wtf.rich.api.event;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import wtf.rich.Main;

public abstract class Event {
     private boolean cancelled;

     public Event call() {
          this.cancelled = false;
          call(this);
          return this;
     }

     public boolean isCancelled() {
          return this.cancelled;
     }

     public void setCancelled(boolean cancelled) {
          this.cancelled = cancelled;
     }

     private static void call(Event event) {
          EventManager var10000 = Main.instance.eventManager;
          ArrayHelper dataList = EventManager.get(event.getClass());
          if (dataList != null) {
               Iterator var2 = dataList.iterator();

               while(var2.hasNext()) {
                    Data data = (Data)var2.next();

                    try {
                         data.target.invoke(data.source, event);
                    } catch (IllegalAccessException var5) {
                         var5.printStackTrace();
                    } catch (InvocationTargetException var6) {
                         var6.printStackTrace();
                    }
               }
          }

     }

     public static enum State {
          PRE("PRE", 0),
          POST("POST", 1);

          private State(String string, int number) {
          }
     }
}
