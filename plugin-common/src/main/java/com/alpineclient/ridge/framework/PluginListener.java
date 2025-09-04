/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.alpineclient.ridge.framework;

import com.alpineclient.ridge.RidgePlugin;
import com.alpineclient.ridge.Reference;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
public abstract class PluginListener implements PluginMessageListener {
    @Getter
    private final String channelId;

    protected final RidgePlugin main = RidgePlugin.getInstance();

    protected PluginListener(String channelId) {
        this.channelId = channelId;
    }

    public abstract void onMessage(@NotNull Player player, byte[] message);

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, byte[] message) {
        if (this.channelId.equals(channel)) {
            this.onMessage(player, message);
        }
        else {
            Reference.LOGGER.warn("Channel mismatch; {} != {}", this.channelId, channel);
        }
    }
}
