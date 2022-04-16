package net.minecraft.util.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.IntIdentityHashBiMap;

public class RegistryNamespaced extends RegistrySimple implements IObjectIntIterable {
     protected final IntIdentityHashBiMap underlyingIntegerMap = new IntIdentityHashBiMap(256);
     protected final Map inverseObjectRegistry;

     public RegistryNamespaced() {
          this.inverseObjectRegistry = ((BiMap)this.registryObjects).inverse();
     }

     public void register(int id, Object key, Object value) {
          this.underlyingIntegerMap.put(value, id);
          this.putObject(key, value);
     }

     protected Map createUnderlyingMap() {
          return HashBiMap.create();
     }

     @Nullable
     public Object getObject(@Nullable Object name) {
          return super.getObject(name);
     }

     @Nullable
     public Object getNameForObject(Object value) {
          return this.inverseObjectRegistry.get(value);
     }

     public boolean containsKey(Object key) {
          return super.containsKey(key);
     }

     public int getIDForObject(@Nullable Object value) {
          return this.underlyingIntegerMap.getId(value);
     }

     @Nullable
     public Object getObjectById(int id) {
          return this.underlyingIntegerMap.get(id);
     }

     public Iterator iterator() {
          return this.underlyingIntegerMap.iterator();
     }
}
