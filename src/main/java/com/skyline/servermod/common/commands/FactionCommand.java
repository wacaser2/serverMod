package com.skyline.servermod.common.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.skyline.servermod.common.data.FactionSavedData;
import com.skyline.servermod.common.data.FactionSavedData.FactionPlayerData;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class FactionCommand {
	public static void register(final CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("faction")
			.then(Commands.literal("list").executes((cc) -> {
				CommandSource source = cc.getSource();
				FactionSavedData data = FactionSavedData.get(source.getServer());
				
				int online, active, total;
				for (String faction : data.factions.keySet()) {
					online = 0;
					active = 0;
					total = 0;
					
					for(Entry<String, FactionPlayerData> entry : data.players.entrySet()) {
						if(getFactionNameFromFactionKey(entry.getKey()) == faction) {
							total++;
							if(entry.getValue().active) {
								active++;
								if (source.getServer().getPlayerList()
										.getPlayerByUsername(getPlayerNameFromFactionKey(entry.getKey())) != null) {
									online++;
								}
							}
						}
					}

					source.sendFeedback(new TranslationTextComponent("commands.faction.list",
							new StringTextComponent(faction), new StringTextComponent(Integer.toString(online)),
							new StringTextComponent(Integer.toString(active)),
							new StringTextComponent(Integer.toString(total))), false);
				}
				
				return 1;
			}))
			.then(Commands.literal("query")
				.then(Commands.argument("faction", StringArgumentType.word()).executes((cc)->{
					CommandSource source = cc.getSource();
					FactionSavedData data = FactionSavedData.get(source.getServer());
					
					String faction  = StringArgumentType.getString(cc, "faction");
					
					int online, active, total;
					online = 0;
					active = 0;
					total = 0;
					
					List<String> onlinePlayers = new ArrayList<String>();
					List<String> activePlayers = new ArrayList<String>();
					List<String> inactivePlayers = new ArrayList<String>();

					for(Entry<String, FactionPlayerData> entry : data.players.entrySet()) {
						if(getFactionNameFromFactionKey(entry.getKey()) == faction) {
							String playerName = getPlayerNameFromFactionKey(entry.getKey());
							total++;
							if(entry.getValue().active) {
								active++;
								if (source.getServer().getPlayerList()
										.getPlayerByUsername(playerName) != null) {
									online++;
									onlinePlayers.add(playerName);
								}else {
									activePlayers.add(playerName);
								}
							}else {
								inactivePlayers.add(playerName);
							}
						}
					}

					source.sendFeedback(new TranslationTextComponent("commands.faction.list",
							new StringTextComponent(faction), new StringTextComponent(Integer.toString(online)),
							new StringTextComponent(Integer.toString(active)),
							new StringTextComponent(Integer.toString(total))), false);

					source.sendFeedback(new TranslationTextComponent("commands.faction.queryteamlist",
							new StringTextComponent(String.join(", ", onlinePlayers)),
							new StringTextComponent(String.join(", ", activePlayers)),
							new StringTextComponent(String.join(", ", inactivePlayers))), false);

					return 1;
				}))
				.then(Commands.argument("player", EntityArgument.player()).executes((cc)->{
					CommandSource source = cc.getSource();
					ServerPlayerEntity target = EntityArgument.getPlayer(cc, "player");
					FactionSavedData data = FactionSavedData.get(source.getServer());
					
					String sourceID = source.getName();
					String targetID = target.getGameProfile().getName();
	
						for (String faction : data.factions.keySet()) {
							String sourceFactionKey = getFactionKey(faction, sourceID);
							String targetFactionKey = getFactionKey(faction, targetID);
							if (data.players.containsKey(targetFactionKey)) {
								FactionSavedData.FactionPlayerData playerData = data.players.get(targetFactionKey);
								if (playerData.active) {
									source.sendFeedback(new TranslationTextComponent("commands.faction.query",
											target.getName(), new StringTextComponent(faction)), false);
									if (data.players.containsKey(sourceFactionKey)
											&& data.players.get(sourceFactionKey).active) {
										source.sendFeedback(
												new TranslationTextComponent("commands.faction.queryteam", target.getName(),
														new StringTextComponent(target.getPositionVec().toString())),
												false);
									}
									break;
							}
						}
					}
					return 1;
				})
			))
			.then(Commands.literal("switch").then(Commands.argument("faction", StringArgumentType.word()).executes((cc) -> {
				CommandSource source = cc.getSource();
				FactionSavedData data = FactionSavedData.get(source.getServer());
	
				String factionName = StringArgumentType.getString(cc, "faction");
				
				if(data.factions.containsKey(factionName)) {
					String playerID = source.getName();
					//save current faction stuff
					for(String faction : data.factions.keySet()) {
						String factionKey = getFactionKey(faction, playerID);
						if(data.players.containsKey(factionKey)) {
							FactionSavedData.FactionPlayerData playerData = data.players.get(factionKey);
							if(playerData.active) {
								playerData.active = false;
								playerData.position = new FactionSavedData.DimLocation(source.getPos(), source.getWorld().func_234923_W_());
								source.asPlayer().writeAdditional(playerData.playerData);
								break;
							}
						}
					}
					//load next faction stuff
					String newFactionKey = getFactionKey(factionName, playerID);
					if(!data.players.containsKey(newFactionKey)) {
						//new faction for this player
						data.players.put(newFactionKey, new FactionSavedData.FactionPlayerData(true, data.factions.get(factionName).origin, new CompoundNBT()));
					}
					//reload old faction
					FactionSavedData.FactionPlayerData playerData = data.players.get(newFactionKey);
					playerData.active = true;
					source.asPlayer().teleport(source.getServer().getWorld(playerData.position.dim), playerData.position.pos.x, playerData.position.pos.y, playerData.position.pos.z, source.asPlayer().rotationYaw, source.asPlayer().rotationPitch);
					source.asPlayer().readAdditional(playerData.playerData);
					
					data.markDirty();
				} else {
					factionName = "failure";
				}
	
				source.sendFeedback(new TranslationTextComponent("commands.faction.switch", new StringTextComponent(factionName)), true);
	
				return 1;
			})))
			.then(Commands.literal("register").then(Commands.argument("faction", StringArgumentType.word()).executes((cc) -> {
				CommandSource source = cc.getSource();
				FactionSavedData data = FactionSavedData.get(source.getServer());

				String factionName = StringArgumentType.getString(cc, "faction");
				
				data.factions.put(factionName, new FactionSavedData.FactionData(new FactionSavedData.DimLocation(source.getPos(), source.getWorld().func_234923_W_())));
				data.markDirty();

				source.sendFeedback(new TranslationTextComponent("commands.faction.register", new StringTextComponent(factionName)), true);

				return 1;
			})))
		);
	}
	
	private static String getFactionKey(String factionName, String playerID) {
		return factionName + ":" + playerID;
	}
	
	private static String getFactionNameFromFactionKey(String factionKey) {
		return factionKey.split(":")[0];
	}
	
	private static String getPlayerNameFromFactionKey(String factionKey) {
		return factionKey.split(":")[1];
	}
}