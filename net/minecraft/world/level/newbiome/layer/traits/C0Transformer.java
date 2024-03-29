package net.minecraft.world.level.newbiome.layer.traits;

import net.minecraft.world.level.newbiome.area.Area;
import net.minecraft.world.level.newbiome.context.BigContext;
import net.minecraft.world.level.newbiome.context.Context;

public interface C0Transformer extends AreaTransformer1, DimensionOffset0Transformer {
    int apply(final Context cly, final int integer);
    
    default int applyPixel(final BigContext<?> clx, final Area clt, final int integer3, final int integer4) {
        return this.apply(clx, clt.get(this.getParentX(integer3), this.getParentY(integer4)));
    }
}
