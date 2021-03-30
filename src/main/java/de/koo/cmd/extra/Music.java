package de.koo.cmd.extra;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.koo.other.StringManager;
import de.koo.start.Hololive;
import org.apache.commons.io.FileUtils;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Shyqu
 * @created 24/03/2021 - 22:55
 * @project HololiveBot
 */

public class Music implements MessageCreateListener {

    DiscordApi api = Hololive.api;

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        String[] message = event.getMessageContent().split(" ");
        TextChannel t_channel = event.getChannel();

        if (message[0].equalsIgnoreCase(StringManager.PREFIX + "play") && message.length == 2) {
            if(event.getMessageAuthor().getConnectedVoiceChannel().isEmpty()) {
                t_channel.sendMessage("You aren't connected to any voice channel!");
            }
            ServerVoiceChannel channel = event.getMessageAuthor().getConnectedVoiceChannel().get();
            channel.connect().thenAccept(audioConnection -> {

                AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
                playerManager.registerSourceManager(new YoutubeAudioSourceManager());
                AudioPlayer player = playerManager.createPlayer();

                AudioSource source = new LavaplayerAudioSource(api, player);
                audioConnection.setAudioSource(source);

                playerManager.loadItem(message[1], new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        player.playTrack(track);
                        t_channel.sendMessage(trackPreview(event.getMessageAuthor(), track));
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        for (AudioTrack track : playlist.getTracks()) {
                            player.playTrack(track);
                        }
                    }

                    @Override
                    public void noMatches() {
                        // Notify the user that we've got nothing
                        t_channel.sendMessage("Fail: ``Couldn't find the music you were looking for.``");
                    }

                    @Override
                    public void loadFailed(FriendlyException throwable) {
                        // Notify the user that everything exploded
                        t_channel.sendMessage("Massive boom boom, please contact our [support.](https://discord.gg/vmXC7f2HHK)");
                    }
                });

            }).exceptionally(e -> {
                // Failed to connect to voice channel (no permissions?)
                t_channel.sendMessage("``Can't connect to Voice-Channel. Please try again later. (Check my permissions?)``");
                e.printStackTrace();
                return null;
            });
        } else if(message[0].equalsIgnoreCase(StringManager.PREFIX + "play") && (message.length != 2)) {
            t_channel.sendMessage("Invalid Usage: ``yg!play <url>``");
        } else
        if(message[0].equalsIgnoreCase(StringManager.PREFIX + "leave")) {

            if(!api.getYourself().getConnectedVoiceChannel(event.getServer().get()).isPresent()) {

                t_channel.sendMessage("``Yagoo isn't in a Voice-Channel. :(``");

                try {
                    URL url = new URL("https://static.miraheze.org/hololivewiki/thumb/a/a1/Yagoo.jpg/260px-Yagoo.jpg");
                    File file = new File(url.getFile());
                    t_channel.sendMessage(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
            event.getServer().get().kickUserFromVoiceChannel(api.getYourself());
        }

    }

    public EmbedBuilder trackPreview(MessageAuthor author, AudioTrack track) {
        return new EmbedBuilder()
                .addField("\uD83C\uDFB5 Now playing: ", track.getInfo().title, true)
                .addField("\uD83D\uDE03 Author: ", track.getInfo().author, true)
                .addField("\uD83C\uDFB9 Track Info", "Length: " + track.getDuration(), false)
                .setFooter("Requested by " + author.getName())
                .setColor(Color.CYAN);
    }

}
