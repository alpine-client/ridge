package com.alpineclient.ridge.api;

import com.alpineclient.ridge.api.objects.AlpinePlayer;
import com.alpineclient.ridge.api.objects.Capability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * @author xEricL
 * Created on 03 September 2025
 *
 * @since 1.4.0
 */
public interface Ridge {

    /**
     * Retrieves the singleton instance of Ridge.
     *
     * @return The Ridge instance.
     * @since 1.4.0
     */
    static @NotNull Ridge get() {
        return (Ridge) Bukkit.getPluginManager().getPlugin("Ridge");
    }

    /**
     * Check if a player is currently connected via Alpine Client.
     *
     * @param player the {@link AlpinePlayer}
     * @return {@code true} if the player is using Alpine Client
     * @since 1.4.0
     */
    default boolean isPlayerConnected(@NotNull AlpinePlayer player) {
        return isPlayerConnected(player.getBukkitPlayer().getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     * @return {@code true} if the player is using Alpine Client
     * @since 1.4.0
     */
    default boolean isPlayerConnected(@NotNull Player player) {
        return isPlayerConnected(player.getUniqueId());
    }

    /**
     * Check if a player is connected via Alpine Client.
     *
     * @param id the {@link UUID} of the players account
     * @return {@code true} if the player is using Alpine Client
     * @since 1.4.0
     */
    boolean isPlayerConnected(@NotNull UUID id);

    /**
     * Get a player connected via Alpine Client.
     *
     * @param player the {@link org.bukkit.entity.Player}
     * @return an {@link Optional} describing the player
     * @since 1.4.0
     */
    @NotNull Optional<AlpinePlayer> getPlayer(@NotNull Player player);

    /**
     * Get a player connected via Alpine Client.
     *
     * @param id the {@link UUID} of the players account
     * @return an {@link Optional} describing the player
     * @since 1.4.0
     */
    @NotNull Optional<AlpinePlayer> getPlayer(@NotNull UUID id);

    /**
     * Get all players connected via Alpine Client.
     *
     * @return a list containing {@link AlpinePlayer}
     * @since 1.4.0
     */
    @NotNull Collection<AlpinePlayer> getAllPlayers();


    // region Capabilities
    /**
     * Registers Alpine Client capabilities for a given plugin.
     * <p>
     * Should be called once for all your desired capabilities on plugin enable.
     *
     * @param plugin the responsible {@link Plugin}
     * @param capabilities a varargs array containing {@link Capability}
     * @see Plugin#onEnable()
     * @since 1.4.0
     */
    void registerCapabilities(@NotNull Plugin plugin, @NotNull Capability... capabilities);

    /**
     * Unregister all Alpine Client capabilities for a given plugin.
     * <p>
     * Should be called once on plugin disable.
     *
     * @param plugin the responsible {@link Plugin}
     * @see Plugin#onDisable()
     * @since 1.4.0
     */
    void unregisterCapabilities(@NotNull Plugin plugin);
    // endregion
}
