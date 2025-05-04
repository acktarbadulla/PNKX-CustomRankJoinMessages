package com.acktar.customrankjoinmessages;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.utils.Config;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.LuckPermsProvider;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

public class Loader extends PluginBase implements Listener {

    private LuckPerms luckPerms;
    private Map<String, String> joinMessages = new HashMap<>();
    private Map<String, String> leaveMessages = new HashMap<>();
    private boolean modifyJoinMessages;
    private boolean modifyLeaveMessages;

    // Hardcoded fallbacks (used only if "default" is missing in config)
    private final String fallbackJoinMessage = "§l§7[§a+§7] {name}";
    private final String fallbackLeaveMessage = "§l§7[§c-§7] {name}";

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(this, this);
        Config config = this.getConfig();

        modifyJoinMessages = config.getBoolean("ModifyJoinMessages", true);
        modifyLeaveMessages = config.getBoolean("ModifyLeaveMessages", true);

        if (config.exists("joinMessages")) {
            config.getSection("joinMessages").getAll().forEach((k, v) -> joinMessages.put(k, String.valueOf(v)));
        }
        if (config.exists("leaveMessages")) {
            config.getSection("leaveMessages").getAll().forEach((k, v) -> leaveMessages.put(k, String.valueOf(v)));
        }

        try {
            this.luckPerms = LuckPermsProvider.get();
        } catch (IllegalStateException e) {
            this.getServer().getPluginManager().disablePlugin(this);
            getLogger().warning("LuckPerms is not available! Join/Leave messages won't work.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!modifyJoinMessages || luckPerms == null) return;

        UUID uuid = event.getPlayer().getUniqueId();
        User user = luckPerms.getUserManager().getUser(uuid);

        String group = (user != null) ? user.getPrimaryGroup() : "default";

        String template = joinMessages.containsKey(group)
                ? joinMessages.get(group)
                : joinMessages.getOrDefault("default", fallbackJoinMessage);

        event.setJoinMessage(template.replace("{name}", event.getPlayer().getName()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (!modifyLeaveMessages || luckPerms == null) return;

        UUID uuid = event.getPlayer().getUniqueId();
        User user = luckPerms.getUserManager().getUser(uuid);

        String group = (user != null) ? user.getPrimaryGroup() : "default";

        String template = leaveMessages.containsKey(group)
                ? leaveMessages.get(group)
                : leaveMessages.getOrDefault("default", fallbackLeaveMessage);

        event.setQuitMessage(template.replace("{name}", event.getPlayer().getName()));
    }
}
