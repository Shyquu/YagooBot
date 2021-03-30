package de.koo.api;

import de.koo.other.SQLite;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyqu
 * @created 30/03/2021 - 17:49
 * @project HololiveBot
 */
public class HoloMembers {

    public String[] getMember(String name) {
        ResultSet set = SQLite.onQuery("SELECT * FROM holomembers WHERE name = '" + name + "' ORDER BY id DESC LIMIT 1");
        try {
            assert set != null;
            if (set.next()) {
                String group = set.getString("grp");
                String gen = set.getString("gen");
                String gender = set.getString("gender");
                String age = set.getString("age");
                String birthday = set.getString("birthday");
                String height = set.getString("height");
                String weight = set.getString("weight");
                String bloodtype = set.getString("bloodtype");
                String zodiac_sign = set.getString("zodiac_sign");
                String emoji = set.getString("emoji");
                String picture = set.getString("picture");
                return new String[]{name, group, gen, gender, age, birthday, height, weight, bloodtype, zodiac_sign, emoji, picture};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addMember(String name, String group, String gen, String gender, String age, String birthday, String height, String weight, String bloodtype, String zodiac_sign, String emoji, String picture) {
        SQLite.onUpdate("INSERT INTO holomembers(name, grp, gen, gender, age, birthday, height, weight, bloodtype, zodiac_sign, emoji, picture) " +
                "VALUES('" + name + "', '" + group + "', '" + gen + "', '" + gender + "', '" + age + "', '" + birthday + "', '" + height + "', '" + weight + "', '" + bloodtype + "', '" + zodiac_sign + "', '" + emoji + "', '" + picture + "')");
    }

    public void removeMember(String name) {
        SQLite.onUpdate("DELETE FROM holomembers WHERE name = '" + name + "'");
    }

    public List<String> getAvailableMembers() {
        List<String> list = new ArrayList<>();

        ResultSet set = SQLite.onQuery("SELECT count(*) AS amogus FROM holomembers");
        try {
            assert set != null;
            if (set.next()) {

                int size = set.getInt("amogus");
                for (int i = 0; i < size; i++) {
                    ResultSet new_set = SQLite.onQuery("SELECT name FROM holomembers WHERE id = " + (i + 1));
                    assert new_set != null;
                    if(new_set.next()) {
                        String name = new_set.getString("name");
                        list.add(name);
                    }
                }
                return list;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmbedBuilder createCard(String name, String group, String gen, String gender, String age, String birthday, String height, String weight, String bloodtype, String zodiac_sign, String emoji, String picture) {
        int codepoint=Integer.parseInt(emoji.substring(2),16);
        char[] ch=Character.toChars(codepoint);
        return new EmbedBuilder()
                .setImage(picture)
                .setTitle("Hololive Member-Card")
                .addField("Full Name", name, true)
                .addField("Group", group, true)
                .addField("Generation", gen, true)
                .addField("Gender", gender, true)
                .addField("Age", age, true)
                .addField("Birthday", birthday, true)
                .addField("Height", height, true)
                .addField("Weight", weight, true)
                .addField("Blood Type", bloodtype, true)
                .addField("Zodiac Sign", zodiac_sign, true)
                .addField("Emoji", String.valueOf(ch[0]), true)
                .setFooter("From our integrated Hololive Database!")
                .setColor(Color.cyan);
    }

}
