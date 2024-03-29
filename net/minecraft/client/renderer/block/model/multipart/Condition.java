package net.minecraft.client.renderer.block.model.multipart;

import java.util.function.Predicate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;

@FunctionalInterface
public interface Condition {
    public static final Condition TRUE = bvu -> bvt -> true;
    public static final Condition FALSE = bvu -> bvt -> false;
    
    Predicate<BlockState> getPredicate(final StateDefinition<Block, BlockState> bvu);
}
