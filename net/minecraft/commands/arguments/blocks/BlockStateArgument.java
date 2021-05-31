package net.minecraft.commands.arguments.blocks;

import java.util.Arrays;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.world.level.block.state.properties.Property;
import java.util.Set;
import com.mojang.brigadier.StringReader;
import java.util.Collection;
import com.mojang.brigadier.arguments.ArgumentType;

public class BlockStateArgument implements ArgumentType<BlockInput> {
    private static final Collection<String> EXAMPLES;
    
    public static BlockStateArgument block() {
        return new BlockStateArgument();
    }
    
    public BlockInput parse(final StringReader stringReader) throws CommandSyntaxException {
        final BlockStateParser dh3 = new BlockStateParser(stringReader, false).parse(true);
        return new BlockInput(dh3.getState(), (Set<Property<?>>)dh3.getProperties().keySet(), dh3.getNbt());
    }
    
    public static BlockInput getBlock(final CommandContext<CommandSourceStack> commandContext, final String string) {
        return (BlockInput)commandContext.getArgument(string, (Class)BlockInput.class);
    }
    
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> commandContext, final SuggestionsBuilder suggestionsBuilder) {
        final StringReader stringReader4 = new StringReader(suggestionsBuilder.getInput());
        stringReader4.setCursor(suggestionsBuilder.getStart());
        final BlockStateParser dh5 = new BlockStateParser(stringReader4, false);
        try {
            dh5.parse(true);
        }
        catch (CommandSyntaxException ex) {}
        return dh5.fillSuggestions(suggestionsBuilder);
    }
    
    public Collection<String> getExamples() {
        return BlockStateArgument.EXAMPLES;
    }
    
    static {
        EXAMPLES = (Collection)Arrays.asList((Object[])new String[] { "stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}" });
    }
}