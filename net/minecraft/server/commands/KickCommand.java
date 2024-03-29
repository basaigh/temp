package net.minecraft.server.commands;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import java.util.Iterator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import java.util.Collection;
import net.minecraft.commands.arguments.MessageArgument;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.arguments.EntityArgument;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.Commands;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import com.mojang.brigadier.CommandDispatcher;

public class KickCommand {
    public static void register(final CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("kick").requires(cd -> cd.hasPermission(3))).then(((RequiredArgumentBuilder)Commands.argument("targets", (com.mojang.brigadier.arguments.ArgumentType<Object>)EntityArgument.players()).executes(commandContext -> kickPlayers((CommandSourceStack)commandContext.getSource(), EntityArgument.getPlayers((CommandContext<CommandSourceStack>)commandContext, "targets"), new TranslatableComponent("multiplayer.disconnect.kicked", new Object[0])))).then(Commands.argument("reason", (com.mojang.brigadier.arguments.ArgumentType<Object>)MessageArgument.message()).executes(commandContext -> kickPlayers((CommandSourceStack)commandContext.getSource(), EntityArgument.getPlayers((CommandContext<CommandSourceStack>)commandContext, "targets"), MessageArgument.getMessage((CommandContext<CommandSourceStack>)commandContext, "reason"))))));
    }
    
    private static int kickPlayers(final CommandSourceStack cd, final Collection<ServerPlayer> collection, final Component jo) {
        for (final ServerPlayer vl5 : collection) {
            vl5.connection.disconnect(jo);
            cd.sendSuccess(new TranslatableComponent("commands.kick.success", new Object[] { vl5.getDisplayName(), jo }), true);
        }
        return collection.size();
    }
}
