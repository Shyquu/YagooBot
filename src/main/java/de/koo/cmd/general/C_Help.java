package de.koo.cmd.general;

import de.koo.cmd.moderation.C_Delete;
import de.koo.other.StringManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.channel.VoiceChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Shyqu
 * @created 24/03/2021 - 21:17
 * @project HololiveBot
 */
public class C_Help implements MessageCreateListener {

    HashMap map = new HashMap();
    C_Delete tool = new C_Delete();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] message = event.getMessageContent().split(" ");
        TextChannel channel = event.getChannel();
        if(message[0].equalsIgnoreCase(StringManager.PREFIX + "help") && message.length == 1) {
            channel.sendMessage(new EmbedBuilder()
                    .setTitle("Help")
                    .setDescription("For more help ``yg!help <catergory>``")
                    .addField("Categories", "▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ")
                    .setUrl("https://www.youtube.com/watch?v=bnSa5EfzMEQ")
                    .addField("General", "``General Bot relatet stuff``")
                    .addField("Fun", "``Funny tools like 8ball``")
                    .addField("Games", "``Games4Two``")
                    .addField("Work or CoinSystem", "``Be the richest``")
                    .addField("Moderation", "``Not four silly little users``")
                    .addField("Hololive", "``Godlike``")
                    .addField("▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ", "# Note: If something goes wrong, join our [help server.](https://discord.gg/vmXC7f2HHK)")
                    .setFooter("Requested by " + event.getMessageAuthor().getName())
            );
        } else if(message[0].equalsIgnoreCase(StringManager.PREFIX + "help") && message.length == 2 && !tool.isNumber(message[1])) {
            String cat = message[1];
            if(cat.equalsIgnoreCase("general")) {
                channel.sendMessage(sendHelp("General", event.getMessageAuthor(), "Help", "yg!help <category>"));
            } else if(cat.equalsIgnoreCase("fun")) {
                channel.sendMessage(sendHelp("Fun", event.getMessageAuthor(), "null", "null"));
            } else if(cat.equalsIgnoreCase("games")) {
                channel.sendMessage(sendHelp("Games", event.getMessageAuthor(), "null", "null"));
            } else if(cat.equalsIgnoreCase("work") || cat.equalsIgnoreCase("coinsystem")) {
                channel.sendMessage(sendHelp("Work & CoinSystem", event.getMessageAuthor(), "null", "null"));
            } else if(cat.equalsIgnoreCase("moderation")) {
                channel.sendMessage(sendHelp("Fun", event.getMessageAuthor(), "Delete", "yg!delete <number>"));
            } else if(cat.equalsIgnoreCase("hololive")) {
                channel.sendMessage(sendHelp("Hololive", event.getMessageAuthor(), "Hololive", "yg!hololive <member>"));
            } else channel.sendMessage("Invalid usage: ``yg!help <category>``");
        }
    }

    public void addCmd(String category, String cmd) {
        map.put(category, cmd);
    }

    public HashMap getCmd() {
        return map;
    }

    public EmbedBuilder sendHelp(String category, MessageAuthor author, String cmd1, String usage1) {
        return new EmbedBuilder()
                .setTitle("Help")
                .setDescription("For more help ``yg!help <catergory>``")
                .addField(category, "▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ")
                .setUrl("https://www.youtube.com/watch?v=bnSa5EfzMEQ")
                .addField(cmd1, "Usage: ``" + usage1 + "``")
                .addField("▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ▆ ", "# Note: If something goes wrong, join our [help server.](https://discord.gg/vmXC7f2HHK)")
                .setFooter("Requested by " + author.getName());
    }

}
