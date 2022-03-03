/*
 * VitalBack is a Spigot Plugin that gives players the ability to teleport back to their last location.
 * Copyright © 2022 Leopold Meinel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://github.com/TamrielNetwork/VitalBack/blob/main/LICENSE
 */

package com.tamrielnetwork.vitalback.utils.commands;

import com.google.common.collect.ImmutableMap;
import com.tamrielnetwork.vitalback.VitalBack;
import com.tamrielnetwork.vitalback.utils.Chat;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class CmdSpec {

	private static final VitalBack main = JavaPlugin.getPlugin(VitalBack.class);

	public static void doDelay(@NotNull CommandSender sender, Location location) {

		Player senderPlayer = (Player) sender;

		if (!senderPlayer.hasPermission("vitalspawn.delay.bypass")) {
			String timeRemaining = String.valueOf(main.getConfig().getLong("delay.time"));
			Chat.sendMessage(senderPlayer, ImmutableMap.of("%countdown%", timeRemaining), "countdown");
			new BukkitRunnable() {

				@Override
				public void run() {

					senderPlayer.teleport(location);
				}
			}.runTaskLater(main, (main.getConfig().getLong("delay.time") * 20L));
		} else {
			senderPlayer.teleport(location);
		}
	}

	public static boolean isInvalidCmd(@NotNull CommandSender sender, @NotNull String perm) {

		if (Cmd.isInvalidSender(sender)) {
			Chat.sendMessage(sender, "player-only");
			return true;
		}
		if (Cmd.isNotPermitted(sender, perm)) {
			Chat.sendMessage(sender, "cmd");
			return true;
		}
		return false;
	}

}