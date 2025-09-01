/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.handler;

import com.alpineclient.plugin.api.event.ClientDisconnectEvent;
import com.alpineclient.plugin.api.event.ClientHandshakeEvent;
import com.alpineclient.plugin.api.objects.AlpinePlayer;
import com.alpineclient.plugin.util.object.HandshakeData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@ApiStatus.Internal
public final class PlayerHandler {
    private final Map<UUID, AlpinePlayer> connectedPlayers = new HashMap<>();

    public boolean addConnectedPlayer(@NotNull Player player, @NotNull HandshakeData data) {
        UUID id = player.getUniqueId();
        if (!this.connectedPlayers.containsKey(id)) {
            AlpinePlayer alpinePlayer = new AlpinePlayer(player, data);
            this.connectedPlayers.put(id, alpinePlayer);
            Bukkit.getPluginManager().callEvent(new ClientHandshakeEvent(alpinePlayer));
            return true;
        }
        return false;
    }

    public boolean removeConnectedPlayer(@NotNull UUID id) {
        if (this.connectedPlayers.containsKey(id)) {
            AlpinePlayer player = this.connectedPlayers.remove(id);
            Bukkit.getPluginManager().callEvent(new ClientDisconnectEvent(player));
            return true;
        }
        return false;
    }

    public boolean isPlayerConnected(@NotNull UUID id) {
        return this.connectedPlayers.containsKey(id);
    }

    public @Nullable AlpinePlayer getConnectedPlayer(@NotNull Player player) {
        return this.connectedPlayers.get(player.getUniqueId());
    }

    public @Nullable AlpinePlayer getConnectedPlayer(@NotNull UUID id) {
        return this.connectedPlayers.get(id);
    }

    public @NotNull Collection<AlpinePlayer> getConnectedPlayers() {
        return Collections.unmodifiableCollection(this.connectedPlayers.values());
    }
}
