/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.ridge.framework;

import com.alpineclient.ridge.PluginMain;
import com.alpineclient.ridge.config.ConfigManager;
import com.alpineclient.ridge.config.impl.MessageConfig;
import org.bukkit.event.Listener;

/**
 * @author BestBearr
 * Created on 09/06/23
 */
public abstract class EventListener implements Listener {
    protected final PluginMain main = PluginMain.getInstance();

    protected final MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class);
}
