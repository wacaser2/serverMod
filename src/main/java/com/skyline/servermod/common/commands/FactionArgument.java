//package com.skyline.servermod.common.commands;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.concurrent.CompletableFuture;
//
//import com.mojang.brigadier.StringReader;
//import com.mojang.brigadier.arguments.ArgumentType;
//import com.mojang.brigadier.context.CommandContext;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
//import com.mojang.brigadier.suggestion.Suggestions;
//import com.mojang.brigadier.suggestion.SuggestionsBuilder;
//import com.skyline.servermod.common.data.FactionWorldSavedData;
//
//import net.minecraft.command.CommandSource;
//import net.minecraft.util.text.TranslationTextComponent;
//
//public class FactionArgument implements ArgumentType<String> {
//	private static final Collection<String> EXAMPLES = Arrays.asList("factionA", "factionB", "factionC");
//	public static final SimpleCommandExceptionType GENERIC = new SimpleCommandExceptionType(
//			new TranslationTextComponent("argument.faction.invalid"));
//	private boolean active;
//
//	protected FactionArgument(boolean active) {
//		this.active = active;
//	}
//
//	public static FactionArgument creation() {
//		return new FactionArgument(false);
//	}
//
//	public static FactionArgument existing() {
//		return new FactionArgument(true);
//	}
//
//	public static String getFaction(final CommandContext<?> context, final String name) {
//		return context.getArgument(name, String.class);
//	}
//
//	public String parse(StringReader sr) throws CommandSyntaxException {
//		return sr.readUnquotedString();
//	}
//
//	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> cc, SuggestionsBuilder sb) {
////		if (active && cc.getSource() instanceof CommandSource) {
////			for (String faction : FactionWorldSavedData.get((((CommandSource) cc.getSource()).getWorld()).factions
////					.keySet()) {
////				sb.suggest(faction);
////			}
////			return sb.buildFuture();
////		} else {
//		return Suggestions.empty();
////		}
//	}
//
//	public Collection<String> getExamples() {
//		return EXAMPLES;
//	}
//}