package net.minecraft.client.util;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class SearchTree implements ISearchTree {
     protected SuffixArray field_194044_a = new SuffixArray();
     protected SuffixArray field_194045_b = new SuffixArray();
     private final Function field_194046_c;
     private final Function field_194047_d;
     private final List field_194048_e = Lists.newArrayList();
     private Object2IntMap field_194049_f = new Object2IntOpenHashMap();

     public SearchTree(Function p_i47612_1_, Function p_i47612_2_) {
          this.field_194046_c = p_i47612_1_;
          this.field_194047_d = p_i47612_2_;
     }

     public void func_194040_a() {
          this.field_194044_a = new SuffixArray();
          this.field_194045_b = new SuffixArray();
          Iterator var1 = this.field_194048_e.iterator();

          while(var1.hasNext()) {
               Object t = var1.next();
               this.func_194042_b(t);
          }

          this.field_194044_a.func_194058_a();
          this.field_194045_b.func_194058_a();
     }

     public void func_194043_a(Object p_194043_1_) {
          this.field_194049_f.put(p_194043_1_, this.field_194048_e.size());
          this.field_194048_e.add(p_194043_1_);
          this.func_194042_b(p_194043_1_);
     }

     private void func_194042_b(Object p_194042_1_) {
          ((Iterable)this.field_194047_d.apply(p_194042_1_)).forEach((p_194039_2_) -> {
               this.field_194045_b.func_194057_a(p_194042_1_, p_194039_2_.toString().toLowerCase(Locale.ROOT));
          });
          ((Iterable)this.field_194046_c.apply(p_194042_1_)).forEach((p_194041_2_) -> {
               this.field_194044_a.func_194057_a(p_194042_1_, p_194041_2_.toLowerCase(Locale.ROOT));
          });
     }

     public List func_194038_a(String p_194038_1_) {
          List list = this.field_194044_a.func_194055_a(p_194038_1_);
          if (p_194038_1_.indexOf(58) < 0) {
               return list;
          } else {
               List list1 = this.field_194045_b.func_194055_a(p_194038_1_);
               return (List)(list1.isEmpty() ? list : Lists.newArrayList(new SearchTree.MergingIterator(list.iterator(), list1.iterator(), this.field_194049_f)));
          }
     }

     static class MergingIterator extends AbstractIterator {
          private final Iterator field_194033_a;
          private final Iterator field_194034_b;
          private final Object2IntMap field_194035_c;
          private Object field_194036_d;
          private Object field_194037_e;

          public MergingIterator(Iterator p_i47606_1_, Iterator p_i47606_2_, Object2IntMap p_i47606_3_) {
               this.field_194033_a = p_i47606_1_;
               this.field_194034_b = p_i47606_2_;
               this.field_194035_c = p_i47606_3_;
               this.field_194036_d = p_i47606_1_.hasNext() ? p_i47606_1_.next() : null;
               this.field_194037_e = p_i47606_2_.hasNext() ? p_i47606_2_.next() : null;
          }

          protected Object computeNext() {
               if (this.field_194036_d == null && this.field_194037_e == null) {
                    return this.endOfData();
               } else {
                    int i;
                    if (this.field_194036_d == this.field_194037_e) {
                         i = 0;
                    } else if (this.field_194036_d == null) {
                         i = 1;
                    } else if (this.field_194037_e == null) {
                         i = -1;
                    } else {
                         i = Integer.compare(this.field_194035_c.getInt(this.field_194036_d), this.field_194035_c.getInt(this.field_194037_e));
                    }

                    Object t = i <= 0 ? this.field_194036_d : this.field_194037_e;
                    if (i <= 0) {
                         this.field_194036_d = this.field_194033_a.hasNext() ? this.field_194033_a.next() : null;
                    }

                    if (i >= 0) {
                         this.field_194037_e = this.field_194034_b.hasNext() ? this.field_194034_b.next() : null;
                    }

                    return t;
               }
          }
     }
}
