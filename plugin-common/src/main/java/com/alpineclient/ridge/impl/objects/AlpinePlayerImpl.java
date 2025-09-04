package com.alpineclient.ridge.impl.objects;

import com.alpineclient.ridge.PluginMain;
import com.alpineclient.ridge.api.objects.Cooldown;
import com.alpineclient.ridge.api.objects.Notification;
import com.alpineclient.ridge.api.objects.Waypoint;
import com.alpineclient.ridge.listener.plugin.PlayListener;
import com.alpineclient.ridge.network.Packet;
import com.alpineclient.ridge.network.packet.PacketCooldownAdd;
import com.alpineclient.ridge.network.packet.PacketNotificationAdd;
import com.alpineclient.ridge.network.packet.PacketWaypointAdd;
import com.alpineclient.ridge.network.packet.PacketWaypointRemove;
import com.alpineclient.ridge.util.object.HandshakeData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * @author xEricL
 * Created on 03 September 2025
 */
public final class AlpinePlayerImpl implements com.alpineclient.ridge.api.objects.AlpinePlayer {

    private final Player bukkitPlayer;
    private final String platform;
    private final String version;

    @ApiStatus.Internal
    public AlpinePlayerImpl(@NotNull Player bukkitPlayer, @NotNull HandshakeData data) {
        this(bukkitPlayer, data.getPlatform(), data.getVersion());
    }

    @ApiStatus.Internal
    public AlpinePlayerImpl(@NotNull Player bukkitPlayer, @NotNull String platform, @NotNull String version) {
        this.bukkitPlayer = bukkitPlayer;
        this.platform = platform;
        this.version = version;
    }

    @Override
    public @NotNull Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    @Override
    public @NotNull String getPlatform() {
        return this.platform;
    }

    @Override
    public @NotNull String getVersion() {
        return this.version;
    }

    @Override
    public @NotNull String getClientBrand() {
        return this.version + "-" + this.platform;
    }

    @Override
    public void sendNotification(@NotNull Notification notification) {
        PacketNotificationAdd packet = new PacketNotificationAdd(notification);
        this.sendPacket(packet);
    }

    @Override
    public void sendWaypoint(@NotNull Waypoint waypoint) {
        PacketWaypointAdd packet = new PacketWaypointAdd(waypoint);
        this.sendPacket(packet);
    }

    @Override
    public void deleteWaypoint(@NotNull UUID id) {
        PacketWaypointRemove packet = new PacketWaypointRemove(id);
        this.sendPacket(packet);
    }

    @Override
    public void sendCooldown(@NotNull Cooldown cooldown) {
        PacketCooldownAdd packet = new PacketCooldownAdd(cooldown);
        this.sendPacket(packet);
    }

    private void sendPacket(Packet packet) {
        this.bukkitPlayer.sendPluginMessage(PluginMain.getInstance(), PlayListener.CHANNEL_ID, packet.toBytes());
    }
}