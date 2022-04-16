package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassInheritanceMultiMap extends AbstractSet {
     private static final Set ALL_KNOWN = Sets.newHashSet();
     private final Map map = Maps.newHashMap();
     private final Set knownKeys = Sets.newIdentityHashSet();
     private final Class baseClass;
     private final List values = Lists.newArrayList();

     public ClassInheritanceMultiMap(Class baseClassIn) {
          this.baseClass = baseClassIn;
          this.knownKeys.add(baseClassIn);
          this.map.put(baseClassIn, this.values);
          Iterator var2 = ALL_KNOWN.iterator();

          while(var2.hasNext()) {
               Class oclass = (Class)var2.next();
               this.createLookup(oclass);
          }

     }

     protected void createLookup(Class clazz) {
          ALL_KNOWN.add(clazz);
          Iterator var2 = this.values.iterator();

          while(var2.hasNext()) {
               Object t = var2.next();
               if (clazz.isAssignableFrom(t.getClass())) {
                    this.addForClass(t, clazz);
               }
          }

          this.knownKeys.add(clazz);
     }

     protected Class initializeClassLookup(Class clazz) {
          if (this.baseClass.isAssignableFrom(clazz)) {
               if (!this.knownKeys.contains(clazz)) {
                    this.createLookup(clazz);
               }

               return clazz;
          } else {
               throw new IllegalArgumentException("Don't know how to search for " + clazz);
          }
     }

     public boolean add(Object p_add_1_) {
          Iterator var2 = this.knownKeys.iterator();

          while(var2.hasNext()) {
               Class oclass = (Class)var2.next();
               if (oclass.isAssignableFrom(p_add_1_.getClass())) {
                    this.addForClass(p_add_1_, oclass);
               }
          }

          return true;
     }

     private void addForClass(Object value, Class parentClass) {
          List list = (List)this.map.get(parentClass);
          if (list == null) {
               this.map.put(parentClass, Lists.newArrayList(new Object[]{value}));
          } else {
               list.add(value);
          }

     }

     public boolean remove(Object p_remove_1_) {
          Object t = p_remove_1_;
          boolean flag = false;
          Iterator var4 = this.knownKeys.iterator();

          while(var4.hasNext()) {
               Class oclass = (Class)var4.next();
               if (oclass.isAssignableFrom(t.getClass())) {
                    List list = (List)this.map.get(oclass);
                    if (list != null && list.remove(t)) {
                         flag = true;
                    }
               }
          }

          return flag;
     }

     public boolean contains(Object p_contains_1_) {
          return Iterators.contains(this.getByClass(p_contains_1_.getClass()).iterator(), p_contains_1_);
     }

     public Iterable getByClass(final Class clazz) {
          return new Iterable() {
               public Iterator iterator() {
                    List list = (List)ClassInheritanceMultiMap.this.map.get(ClassInheritanceMultiMap.this.initializeClassLookup(clazz));
                    if (list == null) {
                         return Collections.emptyIterator();
                    } else {
                         Iterator iterator = list.iterator();
                         return Iterators.filter(iterator, clazz);
                    }
               }
          };
     }

     public Iterator iterator() {
          return (Iterator)(this.values.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.values.iterator()));
     }

     public int size() {
          return this.values.size();
     }
}
