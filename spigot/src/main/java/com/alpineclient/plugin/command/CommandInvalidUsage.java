/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package com.alpineclient.plugin.command;

import com.alpineclient.plugin.PluginMain;
import com.alpineclient.plugin.Reference;
import com.alpineclient.plugin.config.impl.MessageConfig;
import dev.rollczi.litecommands.handler.result.ResultHandlerChain;
import dev.rollczi.litecommands.invalidusage.InvalidUsage;
import dev.rollczi.litecommands.invalidusage.InvalidUsageHandler;
import dev.rollczi.litecommands.invocation.Invocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

/**
 * @author Thomas Wearmouth <tomwmth@pm.me>
 * Created on 1/02/2024
 */
public final class CommandInvalidUsage implements InvalidUsageHandler<CommandSender> {
    @Override
    public void handle(Invocation<CommandSender> invocation, InvalidUsage<CommandSender> result, ResultHandlerChain<CommandSender> chain) {
        MessageConfig config = PluginMain.getInstance().getConfigManager().getConfig(MessageConfig.class);
        CommandSender sender = invocation.sender();
        Schematic command = result.getSchematic();

        if (!command.isOnlyFirst()) {
            Reference.AUDIENCES.sender(sender).sendMessage(config.invalidUsageMultiHeader.build());
            for (String syntax : command.all()) {
                Reference.AUDIENCES.sender(sender).sendMessage(config.invalidUsageMultiContent.build("syntax", syntax));
            }
        }
        else {
            Reference.AUDIENCES.sender(sender).sendMessage(config.invalidUsageSingle.build("syntax", command.first()));
        }
    }
}
