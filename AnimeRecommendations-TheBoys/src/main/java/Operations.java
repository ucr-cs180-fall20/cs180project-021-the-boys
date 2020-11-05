import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Operations {
    private final animeList list = new animeList();

    Operations(){

    }

    void backUp(MessageReceivedEvent event){
        event.getChannel().sendMessage("Saving backup list, please wait...").complete();
        list.animeBackup();
        event.getChannel().sendMessage("Backup list saved!").complete();
    }

    void saveList(MessageReceivedEvent event){
        event.getChannel().sendMessage("Saving list, please wait...").complete();
        list.write();
        event.getChannel().sendMessage("List saved!").complete();
    }

    void messageEmbed(MessageReceivedEvent event){
        EmbedBuilder msgBuilder = new EmbedBuilder();
        msgBuilder.setTitle("title");
        msgBuilder.setDescription("description");
        msgBuilder.addField("field title","field one content", true );
        msgBuilder.addField("field title 2", "field two content", true);
        msgBuilder.addField("field title 3", "content without inline", false);
        msgBuilder.addField("field title 4", "content without inline", false);
        msgBuilder.setFooter("text on bottom");
        event.getChannel().sendMessage(msgBuilder.build()).complete();
    }

    void searchFunction(MessageReceivedEvent event){
        String input = event.getMessage().getContentRaw();
        boolean found = false;
        if(input.startsWith("!")){
            input = input.substring(1);
            for(int i = 0; i <= list.getSize()-1; i++){
                Anime exampleAnime = list.getList().get(i);
                if (input.toUpperCase().replaceAll("\\s+","").equals(exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                    EmbedBuilder msgBuilder = new EmbedBuilder();
                    msgBuilder.setTitle(exampleAnime.getName());
                    msgBuilder.addField("Genre",exampleAnime.getGenre(), false);
                    msgBuilder.addField("Episodes", ""+exampleAnime.getEpisodes(), false);
                    msgBuilder.addField("Rating", ""+ exampleAnime.getRating(), false);
                    //msgBuilder.addField("field title 4", "content without inline", false);
                    //msgBuilder.setFooter("text on bottom");
                    event.getChannel().sendMessage(msgBuilder.build()).complete();
                    found = true;
                }
            }
            if(!found){
                event.getChannel().sendMessage("no results found").complete();
            }
        }
        if(input.startsWith("$")) {
            String temp = new String();
            StringBuilder Listname = new StringBuilder();
            StringBuilder Listname2 = new StringBuilder();
            input = input.substring(1);
            for (int i = 0; i <= list.getSize() - 1; i++) {
                Anime exampleAnime1 = list.getList().get(i);
                if (exampleAnime1.getName().toUpperCase().replaceAll("\\s+","").contains(input.toUpperCase().replaceAll("\\s+",""))) {
                    if (Listname.length() <= 2000) {
                        temp = exampleAnime1.getName();
                        Listname.append(temp);
                        Listname.append("\n");
                        found = true;
                    }
                    else if (Listname.length() >= 2001 && Listname2.length() <= 2000) {
                        temp = exampleAnime1.getName();
                        Listname2.append(temp);
                        Listname2.append("\n");
                    }
                }
            }
            if (!found) {
                event.getChannel().sendMessage("no results found").complete();
            }
            else {
                EmbedBuilder msgBuilder = new EmbedBuilder();
                msgBuilder.setTitle("Search Results");
                msgBuilder.setDescription(Listname.toString());
                event.getChannel().sendMessage(msgBuilder.build()).complete();

                if (Listname2.length() >= 1) {
                    EmbedBuilder msgBuilder2 = new EmbedBuilder();
                    msgBuilder2.setTitle("Search Results");
                    msgBuilder2.setDescription(Listname2.toString());
                    event.getChannel().sendMessage(msgBuilder2.build()).complete();
                }
            }
        }
    }

    void animeListEmbed(MessageReceivedEvent event){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Date date = new Date();
        int page = 0;
        String contents[] = event.getMessage().getContentRaw().split(" ");

        if (contents.length > 1)
            page = Integer.parseInt(contents[1]) - 1;

        int startRank = page * 15;
        int endRank = startRank + 15;

        List<Anime> listCopy = new ArrayList<>(list.getList());
        //listCopy.sort(Comparator.comparing(Anime::getMembers));
        // Collections.reverse(listCopy);

        //gets most watched
        if(contents[0].equals("topw")){
            //listCopy = new ArrayList<>(list.getList());
            listCopy.sort(Comparator.comparing(Anime::getMembers));
            Collections.reverse(listCopy);
        }

        //gets the top rating
        else if(contents[0].equals("topr")){
            //listCopy = new ArrayList<>(list.getList());
            listCopy.sort(Comparator.comparing(Anime::getRating));
            Collections.reverse(listCopy);
        }

        StringBuilder desc = new StringBuilder("```css\nRank   " + String.format("%-20s", "Anime") + "Rating  Type  " +
                "Episodes  Watched\n```");
        StringBuilder descTwo = new StringBuilder("```fix\n");
        StringBuilder descThree = new StringBuilder("```\n");
        for (int i = startRank; i < endRank; ++i)
        {
            if (i < listCopy.size())
            {
                Anime local = listCopy.get(i);
                if (local != null)
                {
                    if (i > 4)
                    {
                        int maxLengthName = (local.getName().length() < 20)?local.getName().length():20;
                        int maxLengthType = (local.getType().length() < 5)?local.getType().length():5;
                        descThree.append(i + 1)
                                .append(i > 8 ? ". " : ".  ")
                                .append(String.format("%-20s\t", local.getName().substring(0, maxLengthName)))
                                .append(String.format("%-4s\t", local.getRating()))
                                .append(String.format("%-5s\t", local.getType().substring(0, maxLengthType)))
                                .append(String.format("%-3s\t", local.getEpisodes()))
                                .append(String.format("%-5s", local.getMembers())+"\n");
                    }
                    else
                    {
                        int maxLengthName = (local.getName().length() < 20)?local.getName().length():20;
                        int maxLengthType = (local.getType().length() < 5)?local.getType().length():5;
                        descTwo.append(i + 1)
                                .append(".  ")
                                .append(String.format("%-20s\t", local.getName().substring(0, maxLengthName)))
                                .append(String.format("%-4s\t", local.getRating()))
                                .append(String.format("%-5s\t", local.getType().substring(0, maxLengthType)))
                                .append(String.format("%-3s\t", local.getEpisodes()))
                                .append(String.format("%-5s", local.getMembers())+"\n");
                    }
                }
            }
        }
        descTwo.append("```");
        descThree.append("```");

        EmbedBuilder lbBuilder = new EmbedBuilder();
        lbBuilder.addField("Anime rankings - Page " + (page == 0 ? 1 : page + 1), desc.toString(), false);

        if (page == 0)
            lbBuilder.addField("", descTwo.toString(), false);
        lbBuilder.addField("", descThree.toString(),false);

        lbBuilder.setColor(new Color(0, 153, 255));
        lbBuilder.setFooter("Today at " + formatter.format(date),
                "https://cdn.frankerfacez.com/emoticon/251321/4");
        event.getChannel().sendMessage(lbBuilder.build()).complete();
    }

    void updateAnimeEpisodes(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        int episodes = Integer.parseInt(msgArray[2]);
        boolean found = false;
        Anime foundAnime = new Anime();

        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                list.updateEpisodes(i,episodes);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Updated **\"" + foundAnime.getName()
                    + "\"'s** episodes to **" + episodes + "**" +
                    "\n Don't forget to save list after updating an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();
    }

    void updateAnimeRating(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        double rating = Double.parseDouble(msgArray[2]);
        boolean found = false;
        Anime foundAnime = new Anime();

        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                list.updateRating(i,rating);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Updated **\"" + foundAnime.getName()
                    + "\"'s** rating to **" + rating + "**" +
                    "\n Don't forget to save list after updating an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();
    }

    void updateAnimeWatched(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        int watched = Integer.parseInt(msgArray[2]);
        boolean found = false;
        Anime foundAnime = new Anime();

        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                list.updateMembers(i,watched);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Updated **\"" + foundAnime.getName()
                    + "\"'s** watched to **" + watched + "**" +
                    "\n Don't forget to save list after updating an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();
    }

    //added updated anime type
    void updateAnimeType(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        String type = msgArray[2];
        boolean found = false;
        Anime foundAnime = new Anime();

        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                list.updateType(i,type);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Updated **\"" + foundAnime.getName()
                    + "\"'s** watched to **" + type + "**" +
                    "\n Don't forget to save list after updating an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();

    }

    //addend updated anime genre
    void updateAnimeGenre(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        String genre = msgArray[2];
        boolean found = false;
        Anime foundAnime = new Anime();

        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                list.updateGenre(i,genre);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Updated **\"" + foundAnime.getName()
                    + "\"'s** watched to **" + genre + "**" +
                    "\n Don't forget to save list after updating an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();

    }

    void deleteAnime(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        Anime foundAnime = new Anime();
        boolean found = false;
        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                list.deleteAnime(i);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Deleted **\"" + foundAnime.getName()+ "\"** from the list" +
                    "\n Don't forget to save the list after removing an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();
    }

    void addAnimeToList(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        List<Anime> listCopy = new ArrayList<>(list.getList());
        listCopy.sort(Comparator.comparing(Anime::getAnime_id));
        Collections.reverse(listCopy);
        int newAnimeId = listCopy.get(0).getAnime_id() + 1;
        Anime newAnime = new Anime(newAnimeId, animeName,"" ,"" ,0 ,0 ,0);
        list.addAnimeToList(newAnime);
        event.getChannel().sendMessage("Anime **\"" + newAnime.getName() + "\"** added to list, " +
                "**please update \"genre, type, episodes, rating, and watched\"** whenever possible!" +
                "\n Don't forget to save list after adding and updating an anime!").complete();
        EmbedBuilder msgBuilder = new EmbedBuilder();
        msgBuilder.setTitle(newAnime.getName());
        msgBuilder.addField("Genre",newAnime.getGenre(), false);
        msgBuilder.addField("Type",newAnime.getType(), false);
        msgBuilder.addField("Episodes", "" + newAnime.getEpisodes(), false);
        msgBuilder.addField("Rating", "" + newAnime.getRating(), false);
        msgBuilder.addField("Watched","" + newAnime.getMembers(), false);
        event.getChannel().sendMessage(msgBuilder.build()).complete();
    }
}
