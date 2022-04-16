package net.minecraft.client.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;

public class SearchTreeManager implements IResourceManagerReloadListener {
     public static final SearchTreeManager.Key field_194011_a = new SearchTreeManager.Key();
     public static final SearchTreeManager.Key field_194012_b = new SearchTreeManager.Key();
     private final Map field_194013_c = Maps.newHashMap();

     public void onResourceManagerReload(IResourceManager resourceManager) {
          Iterator var2 = this.field_194013_c.values().iterator();

          while(var2.hasNext()) {
               SearchTree searchtree = (SearchTree)var2.next();
               searchtree.func_194040_a();
          }

     }

     public void func_194009_a(SearchTreeManager.Key p_194009_1_, SearchTree p_194009_2_) {
          this.field_194013_c.put(p_194009_1_, p_194009_2_);
     }

     public ISearchTree func_194010_a(SearchTreeManager.Key p_194010_1_) {
          return (ISearchTree)this.field_194013_c.get(p_194010_1_);
     }

     public static class Key {
     }
}
