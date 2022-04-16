package net.minecraft.util.registry;

import java.util.Set;
import javax.annotation.Nullable;

public interface IRegistry extends Iterable {
     @Nullable
     Object getObject(Object var1);

     void putObject(Object var1, Object var2);

     Set getKeys();
}
