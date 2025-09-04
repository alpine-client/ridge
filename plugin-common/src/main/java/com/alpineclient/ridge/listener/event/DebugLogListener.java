/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.alpineclient.ridge.listener.event;

import com.alpineclient.ridge.Reference;
import com.alpineclient.ridge.api.event.ClientDisconnectEvent;
import com.alpineclient.ridge.api.event.ClientHandshakeEvent;
import com.alpineclient.ridge.config.ConfigManager;
import com.alpineclient.ridge.config.impl.GeneralConfig;
import com.alpineclient.ridge.framework.EventListener;
import org.bukkit.event.EventHandler;

/**
 * @author Thomas Wearmouth
 * Created on 24/06/2023
 */
public final class DebugLogListener extends EventListener {
    private final GeneralConfig generalConfig = ConfigManager.getInstance().getConfig(GeneralConfig.class);

    @EventHandler
    public void onPlayerHandshake(ClientHandshakeEvent event) {
        if (this.generalConfig.logging) {
            Reference.LOGGER.info("Player completed handshake: {} @ {}", event.getPlayer().getBukkitPlayer().getName(), event.getPlayer().getClientBrand());
        }
    }

    @EventHandler
    public void onPlayerDisconnect(ClientDisconnectEvent event) {
        if (this.generalConfig.logging) {
            Reference.LOGGER.info("Player disconnected: {}", event.getPlayer().getBukkitPlayer().getName());
        }
    }
}
