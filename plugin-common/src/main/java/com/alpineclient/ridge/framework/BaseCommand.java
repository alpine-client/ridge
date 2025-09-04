/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.alpineclient.ridge.framework;

import com.alpineclient.ridge.PluginMain;
import com.alpineclient.ridge.config.ConfigManager;
import com.alpineclient.ridge.config.impl.MessageConfig;

/**
 * @author BestBearr
 * Created on 07/06/23
 */
public abstract class BaseCommand {
    protected final PluginMain main = PluginMain.getInstance();

    protected final MessageConfig messageConfig = ConfigManager.getInstance().getConfig(MessageConfig.class);
}
