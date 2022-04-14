package me.rich.event;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class EventManager {
      private static final Map REGISTRY_MAP = new HashMap();

      public static void register(Object o) {
            Method[] var1 = o.getClass().getDeclaredMethods();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                  Method method = var1[var3];
                  if (!isMethodBad(method)) {
                        register(method, o);
                  }
            }

      }

      public static void register(Object o, Class clazz) {
            Method[] var2 = o.getClass().getDeclaredMethods();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                  Method method = var2[var4];
                  if (!isMethodBad(method, clazz)) {
                        register(method, o);
                  }
            }

      }

      private static void register(Method method, Object o) {
            Class clazz = method.getParameterTypes()[0];
            final Data methodData = new Data(o, method, ((EventTarget)method.getAnnotation(EventTarget.class)).value());
            if (!methodData.target.isAccessible()) {
                  methodData.target.setAccessible(true);
            }

            if (REGISTRY_MAP.containsKey(clazz)) {
                  if (!((ArrayHelper)REGISTRY_MAP.get(clazz)).contains(methodData)) {
                        ((ArrayHelper)REGISTRY_MAP.get(clazz)).add(methodData);
                        sortListValue(clazz);
                  }
            } else {
                  REGISTRY_MAP.put(clazz, new ArrayHelper() {
                        {
                              this.add(methodData);
                        }
                  });
            }

      }

      public static void unregister(Object o) {
            Iterator var1 = REGISTRY_MAP.values().iterator();

            while(var1.hasNext()) {
                  ArrayHelper flexibalArray = (ArrayHelper)var1.next();
                  Iterator var3 = flexibalArray.iterator();

                  while(var3.hasNext()) {
                        Data methodData = (Data)var3.next();
                        if (methodData.source.equals(o)) {
                              flexibalArray.remove(methodData);
                        }
                  }
            }

            cleanMap(true);
      }

      public static void unregister(Object o, Class clazz) {
            if (REGISTRY_MAP.containsKey(clazz)) {
                  Iterator var2 = ((ArrayHelper)REGISTRY_MAP.get(clazz)).iterator();

                  while(var2.hasNext()) {
                        Data methodData = (Data)var2.next();
                        if (methodData.source.equals(o)) {
                              ((ArrayHelper)REGISTRY_MAP.get(clazz)).remove(methodData);
                        }
                  }

                  cleanMap(true);
            }

      }

      public static void cleanMap(boolean b) {
            Iterator iterator = REGISTRY_MAP.entrySet().iterator();

            while(true) {
                  do {
                        if (!iterator.hasNext()) {
                              return;
                        }
                  } while(b && !((ArrayHelper)((Entry)iterator.next()).getValue()).isEmpty());

                  iterator.remove();
            }
      }

      public static void removeEnty(Class clazz) {
            Iterator iterator = REGISTRY_MAP.entrySet().iterator();

            while(iterator.hasNext()) {
                  if (((Class)((Entry)iterator.next()).getKey()).equals(clazz)) {
                        iterator.remove();
                        break;
                  }
            }

      }

      private static void sortListValue(Class clazz) {
            ArrayHelper flexibleArray = new ArrayHelper();
            byte[] var2 = Priority.VALUE_ARRAY;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                  byte b = var2[var4];
                  Iterator var6 = ((ArrayHelper)REGISTRY_MAP.get(clazz)).iterator();

                  while(var6.hasNext()) {
                        Data methodData = (Data)var6.next();
                        if (methodData.priority == b) {
                              flexibleArray.add(methodData);
                        }
                  }
            }

            REGISTRY_MAP.put(clazz, flexibleArray);
      }

      private static boolean isMethodBad(Method method) {
            return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
      }

      private static boolean isMethodBad(Method method, Class clazz) {
            return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
      }

      public static ArrayHelper get(Class clazz) {
            return (ArrayHelper)REGISTRY_MAP.get(clazz);
      }

      public static void shutdown() {
            REGISTRY_MAP.clear();
      }
}
