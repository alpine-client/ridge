/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.alpineclient.ridge.api.event;

import com.alpineclient.ridge.api.objects.AlpinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Called whenever a user who had previously completed a handshake disconnects.
 *
 * @author Thomas Wearmouth
 * @since 1.0.0
 */
public final class ClientDisconnectEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final AlpinePlayer player;

    @ApiStatus.Internal
    public ClientDisconnectEvent(@NotNull AlpinePlayer player) {
        this.player = player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    @ApiStatus.Internal
    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    /**
     * Get the player which this event pertains to.
     *
     * @return the {@link AlpinePlayer}
     */
    public @NotNull AlpinePlayer getPlayer() {
        return this.player;
    }
}
