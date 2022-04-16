package net.minecraft.network.datasync;

import java.io.IOException;
import net.minecraft.network.PacketBuffer;

public interface DataSerializer {
     void write(PacketBuffer var1, Object var2);

     Object read(PacketBuffer var1) throws IOException;

     DataParameter createKey(int var1);

     Object func_192717_a(Object var1);
}
