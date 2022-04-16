package net.minecraft.block.properties;

import com.google.common.base.Optional;
import java.util.Collection;

public interface IProperty {
      String getName();

      Collection getAllowedValues();

      Class getValueClass();

      Optional parseValue(String var1);

      String getName(Comparable var1);
}
