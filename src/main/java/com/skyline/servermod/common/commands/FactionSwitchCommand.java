package com.skyline.servermod.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class FactionSwitchCommand {
    public static void register(final CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("faction").then(Commands.literal("switch")
                .then(Commands.argument("name", StringArgumentType.word()).executes((cc) -> {
                    return 1;
                }))));
    }
}