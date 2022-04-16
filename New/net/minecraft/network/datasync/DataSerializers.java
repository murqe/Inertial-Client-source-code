package net.minecraft.network.datasync;

import com.google.common.base.Optional;
import java.io.IOException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.text.ITextComponent;

public class DataSerializers {
     private static final IntIdentityHashBiMap REGISTRY = new IntIdentityHashBiMap(16);
     public static final DataSerializer BYTE = new DataSerializer() {
          public void write(PacketBuffer buf, Byte value) {
               buf.writeByte(value);
          }

          public Byte read(PacketBuffer buf) throws IOException {
               return buf.readByte();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Byte func_192717_a(Byte p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer VARINT = new DataSerializer() {
          public void write(PacketBuffer buf, Integer value) {
               buf.writeVarIntToBuffer(value);
          }

          public Integer read(PacketBuffer buf) throws IOException {
               return buf.readVarIntFromBuffer();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Integer func_192717_a(Integer p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer FLOAT = new DataSerializer() {
          public void write(PacketBuffer buf, Float value) {
               buf.writeFloat(value);
          }

          public Float read(PacketBuffer buf) throws IOException {
               return buf.readFloat();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Float func_192717_a(Float p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer STRING = new DataSerializer() {
          public void write(PacketBuffer buf, String value) {
               buf.writeString(value);
          }

          public String read(PacketBuffer buf) throws IOException {
               return buf.readStringFromBuffer(32767);
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public String func_192717_a(String p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer TEXT_COMPONENT = new DataSerializer() {
          public void write(PacketBuffer buf, ITextComponent value) {
               buf.writeTextComponent(value);
          }

          public ITextComponent read(PacketBuffer buf) throws IOException {
               return buf.readTextComponent();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public ITextComponent func_192717_a(ITextComponent p_192717_1_) {
               return p_192717_1_.createCopy();
          }
     };
     public static final DataSerializer OPTIONAL_ITEM_STACK = new DataSerializer() {
          public void write(PacketBuffer buf, ItemStack value) {
               buf.writeItemStackToBuffer(value);
          }

          public ItemStack read(PacketBuffer buf) throws IOException {
               return buf.readItemStackFromBuffer();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public ItemStack func_192717_a(ItemStack p_192717_1_) {
               return p_192717_1_.copy();
          }
     };
     public static final DataSerializer OPTIONAL_BLOCK_STATE = new DataSerializer() {
          public void write(PacketBuffer buf, Optional value) {
               if (value.isPresent()) {
                    buf.writeVarIntToBuffer(Block.getStateId((IBlockState)value.get()));
               } else {
                    buf.writeVarIntToBuffer(0);
               }

          }

          public Optional read(PacketBuffer buf) throws IOException {
               int i = buf.readVarIntFromBuffer();
               return i == 0 ? Optional.absent() : Optional.of(Block.getStateById(i));
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Optional func_192717_a(Optional p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer BOOLEAN = new DataSerializer() {
          public void write(PacketBuffer buf, Boolean value) {
               buf.writeBoolean(value);
          }

          public Boolean read(PacketBuffer buf) throws IOException {
               return buf.readBoolean();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Boolean func_192717_a(Boolean p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer ROTATIONS = new DataSerializer() {
          public void write(PacketBuffer buf, Rotations value) {
               buf.writeFloat(value.getX());
               buf.writeFloat(value.getY());
               buf.writeFloat(value.getZ());
          }

          public Rotations read(PacketBuffer buf) throws IOException {
               return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Rotations func_192717_a(Rotations p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer BLOCK_POS = new DataSerializer() {
          public void write(PacketBuffer buf, BlockPos value) {
               buf.writeBlockPos(value);
          }

          public BlockPos read(PacketBuffer buf) throws IOException {
               return buf.readBlockPos();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public BlockPos func_192717_a(BlockPos p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer OPTIONAL_BLOCK_POS = new DataSerializer() {
          public void write(PacketBuffer buf, Optional value) {
               buf.writeBoolean(value.isPresent());
               if (value.isPresent()) {
                    buf.writeBlockPos((BlockPos)value.get());
               }

          }

          public Optional read(PacketBuffer buf) throws IOException {
               return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readBlockPos());
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Optional func_192717_a(Optional p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer FACING = new DataSerializer() {
          public void write(PacketBuffer buf, EnumFacing value) {
               buf.writeEnumValue(value);
          }

          public EnumFacing read(PacketBuffer buf) throws IOException {
               return (EnumFacing)buf.readEnumValue(EnumFacing.class);
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public EnumFacing func_192717_a(EnumFacing p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer OPTIONAL_UNIQUE_ID = new DataSerializer() {
          public void write(PacketBuffer buf, Optional value) {
               buf.writeBoolean(value.isPresent());
               if (value.isPresent()) {
                    buf.writeUuid((UUID)value.get());
               }

          }

          public Optional read(PacketBuffer buf) throws IOException {
               return !buf.readBoolean() ? Optional.absent() : Optional.of(buf.readUuid());
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public Optional func_192717_a(Optional p_192717_1_) {
               return p_192717_1_;
          }
     };
     public static final DataSerializer field_192734_n = new DataSerializer() {
          public void write(PacketBuffer buf, NBTTagCompound value) {
               buf.writeNBTTagCompoundToBuffer(value);
          }

          public NBTTagCompound read(PacketBuffer buf) throws IOException {
               return buf.readNBTTagCompoundFromBuffer();
          }

          public DataParameter createKey(int id) {
               return new DataParameter(id, this);
          }

          public NBTTagCompound func_192717_a(NBTTagCompound p_192717_1_) {
               return p_192717_1_.copy();
          }
     };

     public static void registerSerializer(DataSerializer serializer) {
          REGISTRY.add(serializer);
     }

     @Nullable
     public static DataSerializer getSerializer(int id) {
          return (DataSerializer)REGISTRY.get(id);
     }

     public static int getSerializerId(DataSerializer serializer) {
          return REGISTRY.getId(serializer);
     }

     static {
          registerSerializer(BYTE);
          registerSerializer(VARINT);
          registerSerializer(FLOAT);
          registerSerializer(STRING);
          registerSerializer(TEXT_COMPONENT);
          registerSerializer(OPTIONAL_ITEM_STACK);
          registerSerializer(BOOLEAN);
          registerSerializer(ROTATIONS);
          registerSerializer(BLOCK_POS);
          registerSerializer(OPTIONAL_BLOCK_POS);
          registerSerializer(FACING);
          registerSerializer(OPTIONAL_UNIQUE_ID);
          registerSerializer(OPTIONAL_BLOCK_STATE);
          registerSerializer(field_192734_n);
     }
}
