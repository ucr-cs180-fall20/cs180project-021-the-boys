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

    Message messageEmbed;

    public static void main(String[] args) throws LoginException, InterruptedException {
        op = new Operations();
        jda = JDABuilder.createDefault("").build().awaitReady();
        jda.addEventListener(new main());

//        System.out.println("Welcome! I'm Kawa Gawa-chan.\n");
//        System.out.println("I was constructed to guide you through a list of anime such as tv shows or movies. All provided through the MyAnimelist database\n");
//        System.out.println("Just type in !help or !menu to get started!\n");

    }
    //Command/Message Interpreter
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String channelID = event.getChannel().getId();
        if(channelID.equals("766151933307125763")){
            if(event.getAuthor().isBot()) return;
            System.out.println("Message received: " + event.getMessage().getContentRaw() + "\n From: " + event.getAuthor().getName());

            if(event.getMessage().getContentRaw().equals("!menu") || event.getMessage().getContentRaw().equals("!help")){
                messageEmbed = event.getChannel().sendMessage(op.startMenu()).complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!top")){
                MessageEmbed temp = op.animeListEmbed(event.getMessage().getContentRaw(),true, false, false, false);
                messageEmbed = event.getChannel().sendMessage(temp).complete();
                messageEmbed.addReaction("U+2b05").complete();
                messageEmbed.addReaction("U+27a1").complete();
                messageEmbed.addReaction("U+1f524").complete();
                messageEmbed.addReaction("U+1F51D").complete();
                messageEmbed.addReaction("U+0023U+fe0fU+20e3").complete();
                messageEmbed.addReaction("U+1f441").complete();
                messageEmbed.addReaction("U+1f4C8").complete();
                messageEmbed.addReaction("U+1f4C9").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!ftop")){
                MessageEmbed temp = op.favoriteListEmbed(event.getMessage().getContentRaw(),true, false, false);
                messageEmbed = event.getChannel().sendMessage(temp).complete();
                //favoriteListEmbed.addReaction("U+2b05").complete();
                //favoriteListEmbed.addReaction("U+27a1").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!searcha")){
                //op.searchFunction(event);
                messageEmbed = event.getChannel().sendMessage(op.searchFunction(event)).complete();
                messageEmbed.addReaction("U+1F498").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!searchw")){
                op.searchFunction(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!random")){
                //op.randomAnime(event);
                messageEmbed = event.getChannel().sendMessage(op.randomAnime(event)).complete();
                messageEmbed.addReaction("U+1F498").complete();
            }
            if(event.getMessage().getContentRaw().startsWith("!randG") || event.getMessage().getContentRaw().startsWith("!randg")){
                op.randomGenre(event);
            }

            if(event.getMessage().getContentRaw().equals("!save")){
                op.saveList(event);
            }
            if(event.getMessage().getContentRaw().equals("!genre")){
                op.genreBarGraph(event);
            }
            if(event.getMessage().getContentRaw().equals("!bgenre")){
                op.genreBarGraph(event);
            }
            if(event.getMessage().getContentRaw().startsWith("!mbgenre")){
                op.genreBarGraph(event);
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
            if(event.getMessage().getContentRaw().startsWith("!piegenre")){
                op.genrePieGraph(event);
            }
            if(event.getMessage().getContentRaw().startsWith("hello") || event.getMessage().getContentRaw().startsWith("hi")){
                op.nyahallo(event);
            }
        }
    }
    //React Button Navigation
    @Override
    public void onGenericMessageReaction(@NotNull GenericMessageReactionEvent event) {
        try {
            if (!event.getUser().isBot()) {
                if (event.getMessageId().equals(messageEmbed.getId())) {
                    //event.getChannel().sendMessage(event.getReactionEmote().getAsCodepoints()).complete();
                    if (event.getReactionEmote().getAsCodepoints().equals("U+2b05")) {
                        MessageEmbed temp = op.animeListEmbed("", false, false, true, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+27a1")) {
                        MessageEmbed temp = op.animeListEmbed("", false, true, false, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+1f524")) {
                        MessageEmbed temp = op.animeListEmbed("topa", true, false, false, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+1f51d")) {
                        MessageEmbed temp = op.animeListEmbed("topr", true, false, false, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+23U+fe0fU+20e3")) {
                        MessageEmbed temp = op.animeListEmbed("tope", true, false, false, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+1f441")) {
                        MessageEmbed temp = op.animeListEmbed("topw", true, false, false, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+1f4c9")) {
                        MessageEmbed temp = op.animeListEmbed("", false, false, false, false);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+1f4c8")) {
                        MessageEmbed temp = op.animeListEmbed("", false, false, false, true);
                        messageEmbed = messageEmbed.editMessage(temp).complete();
                    }
                    if(event.getReactionEmote().getAsCodepoints().equals("U+1f498")){
                        op.saveFavoriteByEmoji();
                        event.getChannel().sendMessage("Anime added to favorites").complete();
                    }
                }
                /*else if (event.getMessageId().equals(favoriteListEmbed.getId())) {
                    if (event.getReactionEmote().getAsCodepoints().equals("U+2b05")) {
                        MessageEmbed temp = op.animeListEmbed("", false, false, true);
                        favoriteListEmbed = favoriteListEmbed.editMessage(temp).complete();
                    } else if (event.getReactionEmote().getAsCodepoints().equals("U+27a1")) {
                        MessageEmbed temp = op.animeListEmbed("", false, true, false);
                        favoriteListEmbed = favoriteListEmbed.editMessage(temp).complete();
                    }
                }*/
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}