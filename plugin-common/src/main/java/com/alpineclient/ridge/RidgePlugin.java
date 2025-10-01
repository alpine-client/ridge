/*
 * This file is part of Ridge - https://github.com/alpine-client/ridge
 * Copyright (C) 2025 Crystal Development, LLC
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package com.alpineclient.ridge;

import com.alpineclient.ridge.api.Ridge;
import com.alpineclient.ridge.api.objects.AlpinePlayer;
import com.alpineclient.ridge.api.objects.Capability;
import com.alpineclient.ridge.command.*;
import com.alpineclient.ridge.config.ConfigManager;
import com.alpineclient.ridge.config.impl.MessageConfig;
import com.alpineclient.ridge.framework.EventListener;
import com.alpineclient.ridge.framework.PluginListener;
import com.alpineclient.ridge.handler.CapabilityHandler;
import com.alpineclient.ridge.handler.PlayerHandler;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.LiteCommandsBuilder;
import dev.rollczi.litecommands.adventure.bukkit.platform.LiteAdventurePlatformExtension;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.LiteBukkitMessages;
import dev.rollczi.litecommands.bukkit.LiteBukkitSettings;
import dev.rollczi.litecommands.message.LiteMessages;
import dev.rollczi.litecommands.schematic.SchematicFormat;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Thomas Wearmouth
 * Created on 21/06/2023
 */
@ApiStatus.Internal
public final class RidgePlugin extends JavaPlugin implements Ridge {
    @Getter
    private static RidgePlugin instance;
    {
        instance = this;
    }

    @Getter
    private ConfigManager configManager;

    @Getter
    private LiteCommands<CommandSender> commandManager;

    @Getter
    private PlayerHandler playerHandler;

    @Getter
    private CapabilityHandler capabilityHandler;

    @Override
    public void onEnable() {
        this.configManager = new ConfigManager();
        this.playerHandler = new PlayerHandler();
        this.capabilityHandler = new CapabilityHandler();
        this.commandManager = this.createCommandManager();

        this.registerAll();
    }

    @SuppressWarnings("UnstableApiUsage")
    private LiteCommands<CommandSender> createCommandManager() {
        MessageConfig config = ConfigManager.getInstance().getConfig(MessageConfig.class);
        LiteCommandsBuilder<CommandSender, LiteBukkitSettings, ?> builder = LiteBukkitFactory.builder(this.getName())
                // <Required Arguments> [Optional Arguments]
                .schematicGenerator(SchematicFormat.angleBrackets())

                // Enable Adventure support
                .extension(new LiteAdventurePlatformExtension<>(Reference.AUDIENCES), cfg -> cfg
                        .miniMessage(true)
                        .legacyColor(true)
                        .colorizeArgument(true)
                        .serializer(Reference.MINI_MESSAGE)
                )

                // Feed in our commands
                .commands(new CommandCheck(), new CommandList(), new CommandNotify())

                // Configure error messages
                .message(LiteMessages.MISSING_PERMISSIONS, input -> config.missingPermissions.build())
                .message(LiteBukkitMessages.PLAYER_NOT_FOUND, input -> config.playerNotFound.build("player", input))
                .message(LiteBukkitMessages.PLAYER_ONLY, input -> config.playerOnly.build())
                .invalidUsage(new CommandInvalidUsage());
        return builder.build();
    }

    @SuppressWarnings({"UnstableApiUsage", "unchecked"})
    private void registerAll() {
        Set<Class<?>> clazzes = ImmutableSet.of();
        try {
            clazzes = ClassPath.from(this.getClassLoader()).getAllClasses().stream()
                    .filter(clazz -> clazz.getPackageName().contains("com.alpineclient.ridge"))
                    .filter(clazz -> !clazz.getPackageName().contains("com.alpineclient.ridge.libs"))
                    .map(ClassPath.ClassInfo::load)
                    .collect(Collectors.toSet());
        }
        catch (Exception ex) {
            Reference.LOGGER.error("Error scanning classpath", ex);
        }
        for (Class<?> clazz : clazzes) {
            if (Modifier.isAbstract(clazz.getModifiers()))
                continue;

            try {
                if (EventListener.class.isAssignableFrom(clazz)) {
                    Constructor<? extends EventListener> constructor = ((Class<? extends EventListener>) clazz).getDeclaredConstructor();
                    constructor.setAccessible(true);
                    EventListener listener = constructor.newInstance();
                    this.getServer().getPluginManager().registerEvents(listener, this);
                }
                else if (PluginListener.class.isAssignableFrom(clazz)) {
                    Constructor<? extends PluginListener> constructor = ((Class<? extends PluginListener>) clazz).getDeclaredConstructor();
                    constructor.setAccessible(true);
                    PluginListener listener = constructor.newInstance();
                    this.getServer().getMessenger().registerOutgoingPluginChannel(this, listener.getChannelId());
                    this.getServer().getMessenger().registerIncomingPluginChannel(this, listener.getChannelId(), listener);
                }
            }
            catch (Exception ex) {
                Reference.LOGGER.error("Failed to register {}", clazz.getName(), ex);
            }
        }
    }

    @Override
    public boolean isPlayerConnected(@NotNull UUID id) {
        return this.getPlayerHandler().isPlayerConnected(id);
    }

    @Override
    public @NotNull Optional<AlpinePlayer> getPlayer(@NotNull Player player) {
        return Optional.ofNullable(this.getPlayerHandler().getConnectedPlayer(player));
    }

    @Override
    public @NotNull Optional<AlpinePlayer> getPlayer(@NotNull UUID id) {
        return Optional.ofNullable(this.getPlayerHandler().getConnectedPlayer(id));
    }

    @Override
    public @NotNull Collection<AlpinePlayer> getAllPlayers() {
        return this.getPlayerHandler().getConnectedPlayers();
    }

    @Override
    public void registerCapabilities(
            @NotNull Plugin plugin,
            @NotNull Capability... capabilities
    ) {
        CapabilityHandler handler = this.getCapabilityHandler();
        for (Capability capability : capabilities) {
            handler.register(plugin, capability);
        }
    }

    @Override
    public void unregisterCapabilities(@NotNull Plugin plugin) {
        this.getCapabilityHandler().unregister(plugin);
    }
}
