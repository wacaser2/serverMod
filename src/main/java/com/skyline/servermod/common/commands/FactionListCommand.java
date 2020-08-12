package com.skyline.servermod.common.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class FactionListCommand {
    public static void register(final CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("faction").then(Commands.literal("list").executes((cc) -> {
            return 1;
        })));
    }
}