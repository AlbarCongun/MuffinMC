package com.monia.muffinmc.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MuffinCommand extends Command {
    public MuffinCommand(String name) {
        super(name);
        this.description = "Muffin related commands";
        this.usageMessage = "/muffin [version|reload]";
        this.setPermission("muffin.command.muffin");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Component.text("Muffin version: " + Bukkit.getVersion(), NamedTextColor.GOLD));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "version":
            case "ver":
                sender.sendMessage(Component.text("Muffin version: " + Bukkit.getVersion(), NamedTextColor.GOLD));
                break;
            case "reload":
                sender.sendMessage(Component.text("Reloading Muffin configuration...", NamedTextColor.YELLOW));
                sender.sendMessage(Component.text("Done!", NamedTextColor.GREEN));
                break;
            default:
                sender.sendMessage(Component.text("Unknown subcommand. Use: version, reload", NamedTextColor.RED));
        }
        return true;
    }
}
