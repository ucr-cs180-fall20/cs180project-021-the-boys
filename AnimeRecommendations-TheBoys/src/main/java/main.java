import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.GenericMessageReactionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Objects;

public class main extends ListenerAdapter {
    private static JDA jda;
    private static Operations op;

    Message startMenu;
    Message testMessage;
    Message animeListEmbed;
    Message favoriteListEmbed;

    public static void main(String[] args) throws  LoginException {
        op = new Operations();
        jda = JDABuilder.createDefault("NzExMDY3NjczNjg0ODY5MTgw.Xr9neA.eVMng9D87Y-WWf0JoYGOxP0oDU4").build();
        jda.addEventListener(new main());
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String channelID = event.getChannel().getId();
        if(channelID.equals("766151933307125763")){
            if(event.getAuthor().isBot()) return;
            System.out.println("Message received: " + event.getMessage().getContentRaw() + "\n From: " + event.getAuthor().getName());

            if(event.getMessage().getContentRaw().equals("!menu") || event.getMessage().getContentRaw().equals("!help")){
                startMenu = event.getChannel().sendMessage(op.startMenu()).complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!top")){
                MessageEmbed temp = op.animeListEmbed(event.getMessage().getContentRaw(),true, false, false);
                animeListEmbed = event.getChannel().sendMessage(temp).complete();
                animeListEmbed.addReaction("U+2b05").complete();
                animeListEmbed.addReaction("U+27a1").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!ftop")){
                MessageEmbed temp = op.favoriteListEmbed(event.getMessage().getContentRaw(),true, false, false);
                favoriteListEmbed = event.getChannel().sendMessage(temp).complete();
                favoriteListEmbed.addReaction("U+2b05").complete();
                favoriteListEmbed.addReaction("U+27a1").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!searcha") || event.getMessage().getContentRaw().startsWith("!searchw")){
                op.searchFunction(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!random")){
                op.randomAnime(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!randG") || event.getMessage().getContentRaw().startsWith("!randg")){
                op.randomGenre(event);
            }
            if(event.getMessage().getContentRaw().equals("!save")){
                op.saveList(event);
            }
            if(event.getMessage().getContentRaw().equals("!backup")){
                op.backUp(event);
            }
            if(event.getMessage().getContentRaw().equals("!fexport")){
                op.exportSave(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!updateAE")){
                op.updateAnimeEpisodes(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!updateAR")){
                op.updateAnimeRating(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!updateAW")){
                op.updateAnimeWatched(event);
            }
            //added two more updates "type and genre"
            if(event.getMessage().getContentRaw().startsWith("!updateAT")){
                op.updateAnimeType(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!updateAG")){
                op.updateAnimeGenre(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!deleteA")){
                op.deleteAnime(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!deleteF")){
                op.deleteFavorite(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!addA")){
                op.addAnimeToList(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!favorite")){
                op.saveFavorite(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!bargraph")){
                op.barGraph(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!rgraph")){
                op.barGraph(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!egraph")){
                op.barGraph(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!ratings")){
                //don't uncomment this one unless you want to see how long it would take to
                //parse through the second file that is really long
                //I say Brian will take over 20 minutes or just crash, requires at least
                //700 Mb's of ram available to run
                //op.ratingsGraph(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!genrepie")){
                //this one is messed up, needs fixing
                //op.genrePieGraph(event);
            }
        }
    }

    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        try {
            if (!event.getUser().isBot()) {
                if (event.getMessageId().equals(animeListEmbed.getId())) {
                    if (event.getReactionEmote().getAsCodepoints().equals("U+2b05")) {
                        MessageEmbed temp = op.animeListEmbed("", false, false, true);
                        animeListEmbed = animeListEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+27a1")) {
                        MessageEmbed temp = op.animeListEmbed("", false, true, false);
                        animeListEmbed = animeListEmbed.editMessage(temp).complete();
                    }
                } else if (event.getMessageId().equals(favoriteListEmbed.getId())) {
                    if (event.getReactionEmote().getAsCodepoints().equals("U+2b05")) {
                        MessageEmbed temp = op.animeListEmbed("", false, false, true);
                        favoriteListEmbed = favoriteListEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+27a1")) {
                        MessageEmbed temp = op.animeListEmbed("", false, true, false);
                        favoriteListEmbed = favoriteListEmbed.editMessage(temp).complete();
                    }
                }
            }
        }
        catch (Exception ignored){}
    }
}