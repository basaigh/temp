package net.minecraft.nbt;

import net.minecraft.network.chat.Component;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;
import net.minecraft.ChatFormatting;

public interface Tag {
    public static final String[] TAG_NAMES = { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]", "LONG[]" };
    public static final ChatFormatting SYNTAX_HIGHLIGHTING_KEY = ChatFormatting.AQUA;
    public static final ChatFormatting SYNTAX_HIGHLIGHTING_STRING = ChatFormatting.GREEN;
    public static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER = ChatFormatting.GOLD;
    public static final ChatFormatting SYNTAX_HIGHLIGHTING_NUMBER_TYPE = ChatFormatting.RED;
    
    void write(final DataOutput dataOutput) throws IOException;
    
    void load(final DataInput dataInput, final int integer, final NbtAccounter in) throws IOException;
    
    String toString();
    
    byte getId();
    
    default Tag newTag(final byte byte1) {
        switch (byte1) {
            case 0: {
                return new EndTag();
            }
            case 1: {
                return new ByteTag();
            }
            case 2: {
                return new ShortTag();
            }
            case 3: {
                return new IntTag();
            }
            case 4: {
                return new LongTag();
            }
            case 5: {
                return new FloatTag();
            }
            case 6: {
                return new DoubleTag();
            }
            case 7: {
                return new ByteArrayTag();
            }
            case 11: {
                return new IntArrayTag();
            }
            case 12: {
                return new LongArrayTag();
            }
            case 8: {
                return new StringTag();
            }
            case 9: {
                return new ListTag();
            }
            case 10: {
                return new CompoundTag();
            }
            default: {
                return null;
            }
        }
    }
    
    default String getTagTypeName(final int integer) {
        switch (integer) {
            case 0: {
                return "TAG_End";
            }
            case 1: {
                return "TAG_Byte";
            }
            case 2: {
                return "TAG_Short";
            }
            case 3: {
                return "TAG_Int";
            }
            case 4: {
                return "TAG_Long";
            }
            case 5: {
                return "TAG_Float";
            }
            case 6: {
                return "TAG_Double";
            }
            case 7: {
                return "TAG_Byte_Array";
            }
            case 11: {
                return "TAG_Int_Array";
            }
            case 12: {
                return "TAG_Long_Array";
            }
            case 8: {
                return "TAG_String";
            }
            case 9: {
                return "TAG_List";
            }
            case 10: {
                return "TAG_Compound";
            }
            case 99: {
                return "Any Numeric Tag";
            }
            default: {
                return "UNKNOWN";
            }
        }
    }
    
    Tag copy();
    
    default String getAsString() {
        return this.toString();
    }
    
    default Component getPrettyDisplay() {
        return this.getPrettyDisplay("", 0);
    }
    
    Component getPrettyDisplay(final String string, final int integer);
}
