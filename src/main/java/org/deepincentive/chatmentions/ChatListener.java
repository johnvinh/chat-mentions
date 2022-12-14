package org.deepincentive.chatmentions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {
    private final Pattern mentionPattern = Pattern.compile("@([A-Za-z0-9_]+)", Pattern.CASE_INSENSITIVE);
    private final Sound mentionSound = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
    private static final int TICKS_PER_SECOND = 20;

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Matcher matcher = mentionPattern.matcher(e.getMessage());
        // will ping multiple players if there are multiple mentions
        while (matcher.find()) {
            Player target = Bukkit.getPlayer(matcher.group(1));
            if (target == null) {
                return;
            }
            target.playSound(target.getLocation(), mentionSound, 1.0F, 1.0F);
            target.sendTitle(ChatColor.RED + "You got mentioned!",
                    ChatColor.YELLOW + e.getPlayer().getName() + " mentioned you!",
                    TICKS_PER_SECOND,
                    TICKS_PER_SECOND * 5,
                    TICKS_PER_SECOND);
        }
    }
}
