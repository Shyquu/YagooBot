package de.koo.cmd.moderation;

import de.koo.other.StringManager;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Shyqu
 * @created 24/03/2021 - 21:45
 * @project HololiveBot
 */

public class C_Delete implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] message = event.getMessageContent().split(" ");
        TextChannel channel = event.getChannel();
        if(message[0].equalsIgnoreCase(StringManager.PREFIX + "delete") && message.length == 2 && event.getMessageAuthor().isServerAdmin() && isNumber(message[1])) {
            int number = Integer.parseInt(message[1]);
            try {
                channel.getMessagesBefore(number, event.getMessage()).get().deleteAll();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            channel.sendMessage("``Deleted " + number + " Messages for you. Self-destruction in 5 seconds.``");
            try {
                Message msg = channel.getMessagesAfter(1, event.getMessage()).get().first();
                Thread.sleep(5000);
                msg.delete();
                event.getMessage().delete();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        } else if (message[0].equalsIgnoreCase(StringManager.PREFIX + "delete") && message.length == 2 && event.getMessageAuthor().isServerAdmin() && !isNumber(message[1])) {
            channel.sendMessage("Invalid usage: ``yg!delete <number>``");
        } else if (message[0].equalsIgnoreCase(StringManager.PREFIX + "delete") && (message.length == 1 || message.length > 2) && event.getMessageAuthor().isServerAdmin() && isNumber(message[1])) {
            channel.sendMessage("Invalid usage: ``yg!delete <number>``");
        } else if (message[0].equalsIgnoreCase(StringManager.PREFIX + "delete") && !event.getMessageAuthor().isServerAdmin() && !isNumber(message[1])) {
            channel.sendMessage("``Du hast keine Berechtigungen dazu!``");
        }
    }

    public boolean isNumber(String string) {
        if(string.matches("[0-9]+")) return true;
        return false;
    }
}
