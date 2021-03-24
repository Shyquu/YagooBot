package de.koo.start;

import de.koo.other.StringManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.permission.Permissions;

/**
 * @author Shyqu
 * @created 24/03/2021 - 20:17
 * @project HololiveBot
 */

public class Hololive {

    public static DiscordApi api;

    public static void main(String[] args) {

        api = new DiscordApiBuilder().setToken(StringManager.TOKEN).login().join();
        api.updateActivity(ActivityType.WATCHING, "Minato Aqua | " + api.getServers().size() + " | yg!help");
        Register.__init__();
        System.out.println(api.createBotInvite(Permissions.fromBitmask(0x8)));

    }

}
