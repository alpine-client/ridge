/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.ridge.api.objects;

import com.alpineclient.ridge.api.Ridge;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Holds data about an Alpine Client user and provides methods
 * for interacting with them.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public interface AlpinePlayer {

    /**
     * Get the underlying Bukkit player object.
     *
     * @return the {@link org.bukkit.entity.Player}
     */
    @NotNull Player getBukkitPlayer();

    /**
     * Get the mod platform the user is on.
     * <p>
     * e.g. {@code fabric}, {@code forge}
     *
     * @return the mod platform
     */
    @NotNull String getPlatform();

    /**
     * Get the Minecraft version the user is on.
     * <p>
     * e.g. {@code 1.8.9}, {@code 1.20}
     *
     * @return the Minecraft version
     */
    @NotNull String getVersion();

    /**
     * Get the full client brand consisting of their version
     * and platform.
     * <p>
     * e.g. {@code 1.18.2-fabric}
     *
     * @return the client brand
     */
    @NotNull String getClientBrand();

    /**
     * Sends a notification to this player.
     *
     * @param notification the notification to send
     */
    void sendNotification(@NotNull Notification notification);

    /**
     * Sends a waypoint to this player.
     *
     * @param waypoint the waypoint to send
     */
    void sendWaypoint(@NotNull Waypoint waypoint);

    /**
     * Requests the player delete a waypoint with a given UUID.
     *
     * @param id the waypoint to delete
     *
     * @since 1.2.1
     */
    void deleteWaypoint(@NotNull UUID id);

    /**
     * Sends a cooldown to this player.
     * <p>
     * If you intend to utilize the Cooldowns module, you must first declare
     * the capability with {@link Ridge#registerCapabilities(Plugin, Capability...)}.
     *
     * @param cooldown the cooldown to send
     *
     * @since 1.3.0
     */
    void sendCooldown(@NotNull Cooldown cooldown);
}
