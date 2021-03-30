package de.koo.cmd.extra;

import de.koo.api.HoloMembers;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

/**
 * @author Shyqu
 * @created 30/03/2021 - 18:13
 * @project HololiveBot
 */
public class C_Hololive implements MessageCreateListener {

    // name, group, gen, gender, age, birthday, height, weight, bloodtype, zodiac_sign, emoji

    HoloMembers holomembers = new HoloMembers();

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] message = event.getMessageContent().split(" ");
        TextChannel channel = event.getChannel();
        String cmd = message[0];
        if(cmd.equalsIgnoreCase("yg!hm")) {
            if(message[1].equalsIgnoreCase("add")) {

                if(!(message.length == 15)) {
                    channel.sendMessage("``Wrong Usage: [yg!hm add name group gen gender age birthday height weight bloodtype zodiac_sign emoji, picture(url)]``");
                    return;
                }

                String name = message[2] + " " + message[3];
                String group = message[4];
                String gen = message[5];
                String gender = message[6];
                String age = message[7];
                String birthday = message[8];
                String height = message[9];
                String weight = message[10];
                String bloodtype = message[11];
                String zodiac_sign = message[12];
                String emoji = message[13];
                String picture = message[14];

                holomembers.addMember(name, group, gen, gender, age, birthday, height, weight, bloodtype, zodiac_sign, emoji, picture);

                channel.sendMessage("``Successfully created Member-Card!``");
                channel.sendMessage(holomembers.createCard(name, group, gen, gender, age, birthday, height, weight, bloodtype, zodiac_sign, emoji, picture));

            } else
            if(message[1].equalsIgnoreCase("remove")) {

                if(!(message.length == 4)) {
                    channel.sendMessage("``Wrong Usage: [yg!hm remove name]``");
                    return;
                }
                String name = message[2] + " " + message[3];
                if(holomembers.getMember(name) == null) {
                    channel.sendMessage("``Sorry, but I dont found the Member you where looking for.``");
                    return;
                }
                holomembers.removeMember(name);
                channel.sendMessage("**Removed Member:** ``" + name + "``");

            } else
            if(message[1].equalsIgnoreCase("get")) {

                if(!(message.length == 4)) {
                    channel.sendMessage("``Wrong Usage: [yg!hm get name]``");
                    return;
                }

                String[] holomember = holomembers.getMember((message[2] + " " + message[3]));

                if(holomember == null) {
                    channel.sendMessage("``Sorry, but I dont found the Member you where looking for.``");
                    return;
                }

                String name = holomember[0];
                String group = holomember[1];
                String gen = holomember[2];
                String gender = holomember[3];
                String age = holomember[4];
                String birthday = holomember[5];
                String height = holomember[6];
                String weight = holomember[7];
                String bloodtype = holomember[8];
                String zodiac_sign = holomember[9];
                String emoji = holomember[10];
                String picture = holomember[11];

                channel.sendMessage(holomembers.createCard(name, group, gen, gender, age, birthday, height, weight, bloodtype, zodiac_sign, emoji, picture));

            } else
            if(message[1].equalsIgnoreCase("list")) {
                StringBuilder stringBuilder = new StringBuilder();
                for(int i = 0; i < holomembers.getAvailableMembers().size(); i++) {
                    stringBuilder.append(holomembers.getAvailableMembers().get(i)).append(", ");
                }
                channel.sendMessage("All currently available Members: " + stringBuilder.toString());
            }

        }
    }
}
