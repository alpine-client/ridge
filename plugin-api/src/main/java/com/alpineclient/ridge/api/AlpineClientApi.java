/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.alpineclient.ridge.api;

import com.alpineclient.ridge.api.objects.AlpinePlayer;
import com.alpineclient.ridge.api.objects.Capability;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Main wrapper class to interact with the API.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 * @deprecated Use {@link Ridge} instead.
 */
@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion = "1.5.0")
public final class AlpineClientApi {

    /**
     * Fake constructor to stop attempted instantiation.
     */
    private AlpineClientApi() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("This is a static utility class and cannot be instantiated");
    }

    /**
     * Check if a player is currently connected via Alpine Client.
     *
     * @param player the {@link AlpinePlayer}
     *
     * @return {@code true} if the player is using Alpine Client
     * @deprecated Use {@link Ridge#isPlayerConnected(AlpinePlayer)} instead.
     */
    public static boolean isPlayerConnected(@NotNull AlpinePlayer player) {
        return isPlayerConnected(player.getBukkitPlayer().getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     *
     * @return {@code true} if the player is using Alpine Client
     * @deprecated Use {@link Ridge#isPlayerConnected(Player)} instead.
     */
    public static boolean isPlayerConnected(@NotNull Player player) {
        return isPlayerConnected(player.getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param id the {@link UUID} of the players account
     *
     * @return {@code true} if the player is using Alpine Client
     * @deprecated Use {@link Ridge#isPlayerConnected(UUID)} instead.
     */
    public static boolean isPlayerConnected(@NotNull UUID id) {
        return Ridge.get().isPlayerConnected(id);
    }

    /**
     * Get a player connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     *
     * @return an {@link Optional} describing the player
     *
     * @since 1.1.2
     * @deprecated Use {@link Ridge#getPlayer(Player)} instead.
     */
    public static @NotNull Optional<AlpinePlayer> getPlayer(@NotNull Player player) {
        return Ridge.get().getPlayer(player);
    }

    /**
     * Get a player connected via Alpine Client.
     *
     * @param id the {@link UUID} of the players account
     *
     * @return an {@link Optional} describing the player
     *
     * @since 1.1.2
     * @deprecated Use {@link Ridge#getPlayer(UUID)} instead.
     */
    public static @NotNull Optional<AlpinePlayer> getPlayer(@NotNull UUID id) {
        return Ridge.get().getPlayer(id);
    }

    /**
     * Get all players connected via Alpine Client.
     *
     * @return a list containing {@link AlpinePlayer}
     *
     * @since 1.1.2
     * @deprecated Use {@link Ridge#getAllPlayers()} instead.
     */
    public static @NotNull Collection<AlpinePlayer> getAllPlayers() {
        return Ridge.get().getAllPlayers();
    }


    // region Capabilities
    /**
     * Registers Alpine Client capabilities for a given plugin.
     * <p>
     * Should be called once for all your desired capabilities on plugin enable.
     *
     * @param plugin the responsible {@link Plugin}
     * @param capabilities a varargs array containing {@link Capability}
     *
     * @see Plugin#onEnable()
     *
     * @since 1.3.0
     * @deprecated Use {@link Ridge#registerCapabilities(Plugin, Capability...)} instead.
     */
    public static void registerCapabilities(@NotNull Plugin plugin, @NotNull Capability... capabilities) {
        Ridge.get().registerCapabilities(plugin, capabilities);
    }

    /**
     * Unregister all Alpine Client capabilities for a given plugin.
     * <p>
     * Should be called once on plugin disable.
     *
     * @param plugin the responsible {@link Plugin}
     *
     * @see Plugin#onDisable()
     *
     * @since 1.3.0
     * @deprecated Use {@link Ridge#unregisterCapabilities(Plugin)} instead.
     */
    public static void unregisterCapabilities(@NotNull Plugin plugin) {
        Ridge.get().unregisterCapabilities(plugin);
    }
    // endregion
}
