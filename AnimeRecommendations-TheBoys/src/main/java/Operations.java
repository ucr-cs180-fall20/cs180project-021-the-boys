import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Random;

public class Operations {
    private final animeList list = new animeList();
    //don't uncomment this one unless you want to see how long it would take to
    //parse through the second file that is really long
    //I say Brian will take over 20 minutes or just crash, requires at least
    //700 Mb's of ram available to run
    //private final ratingList rlist = new ratingList();
    private final favoriteList favorites = new favoriteList();

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

    MessageEmbed startMenu(){
        EmbedBuilder msgBuilder = new EmbedBuilder();
        msgBuilder.setTitle("Help Menu");
        msgBuilder.addField("!menu/!help","Sends a message of this menu.",false);
        msgBuilder.addField("!top","Sends a message of the top anime.",false);
        msgBuilder.addField("!ftop","Sends a message of your favorite anime.",false);
        msgBuilder.addField("!searcha","Search for a specific anime. (ex: !searcha Death Note)",false);
        msgBuilder.addField("!searchw","Search for a specific word in anime titles. (ex: !searchw death)",false);
        msgBuilder.addField("!random","Gives you a random anime in the list of over 10,000 anime.",false);
        msgBuilder.addField("!favorite","Saves x anime to favorites. (ex: !favorite Death Note)",false);
        msgBuilder.addField("!deleteF","Deletes x anime from favorites. (ex: !deleteF [Death Note])",false);
        msgBuilder.addField("","",false);
        msgBuilder.addField("!bargraph","Shows an image of a graph for specified parameters.",false);
        msgBuilder.addField("!rgraph","Shows an image of a graph that compares **ratings** of different anime.",false);
        msgBuilder.addField("!egraph","Shows an image of a graph that compares **episodes** of specified anime.",false);
        msgBuilder.addField("!ratings","Shows an image of a graph that compares **episodes** of a specific anime.",false);
        msgBuilder.addField("!genrepie","Shows an image of a graph that compares **episodes** of a specific anime.",false);
        msgBuilder.addField("","",false);
        msgBuilder.addField("!addA","Adds empty anime with specified name. (ex: !addA [Red])",false);
        msgBuilder.addField("!updateAR","Changes anime **rating** to specified. (ex: !updateAR [Red][8.67])",false);
        msgBuilder.addField("!updateAT","Changes anime **type** to specified. (ex: !updateAT [Red][TV])",false);
        msgBuilder.addField("!updateAE","Changes anime **episodes** to specified. (ex: !updateAE [Red][25])",false);
        msgBuilder.addField("!updateAW","Changes anime **watched** to specified. (ex: !updateAW [Red][932492])",false);
        msgBuilder.addField("!updateAG","Changes anime **genre** to specified. (ex: !updateAG [Red][Adventure, Comedy])",false);
        msgBuilder.addField("!deleteA","Deletes specified anime. (ex: !deleteA [Red])",false);
        return msgBuilder.build();
    }

    void searchFunction(MessageReceivedEvent event){
        String input = event.getMessage().getContentRaw();
        boolean found = false;
        if(input.startsWith("!searcha")){
            input = input.substring(9);
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
        if(input.startsWith("!searchw")) {
            String temp = new String();
            StringBuilder Listname = new StringBuilder();
            StringBuilder Listname2 = new StringBuilder();
            input = input.substring(9);
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

    private int animeListPage = 0;
    private String animeListSort;
    MessageEmbed animeListEmbed(String message, boolean reset, boolean nextpage, boolean previouspage){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Date date = new Date();
        if(reset) {
            animeListPage = 0;
            animeListSort = message;
        }
        if(nextpage) {
            if(animeListPage >= list.getSize()/15){
                animeListPage = 0;
            }
            else animeListPage++;
        }
        if(previouspage) {
            if(animeListPage == 0){
                animeListPage = list.getSize()/15;
            }
            else animeListPage--;
        }

        int startRank = animeListPage * 15;
        int endRank = startRank + 15;

        List<Anime> listCopy = new ArrayList<>(list.getList());
        //listCopy.sort(Comparator.comparing(Anime::getMembers));
        // Collections.reverse(listCopy);

        //gets most watched
        if(animeListSort.equals("topw")){
            //listCopy = new ArrayList<>(list.getList());
            listCopy.sort(Comparator.comparing(Anime::getMembers));
            Collections.reverse(listCopy);
        }

        //gets the top rating
        else if(animeListSort.equals("topr")){
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
        lbBuilder.addField("Anime rankings - Page " + (animeListPage == 0 ? 1 : animeListPage + 1), desc.toString(), false);

        if (animeListPage == 0)
            lbBuilder.addField("", descTwo.toString(), false);
        lbBuilder.addField("", descThree.toString(),false);

        lbBuilder.setColor(new Color(0, 153, 255));
        lbBuilder.setFooter("Today at " + formatter.format(date),
                "https://cdn.frankerfacez.com/emoticon/251321/4");
        return lbBuilder.build();
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
                    + "\"'s** type to **" + type + "**" +
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
                    + "\"'s** genre(s) to **" + genre + "**" +
                    "\n Don't forget to save list after updating an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();

    }

    //function for bringing up a random anime to watch
    void randomAnime(MessageReceivedEvent event){
        String input = event.getMessage().getContentRaw();
        boolean found = false;


            Anime randAnime = getRandom();

            EmbedBuilder msgBuilder = new EmbedBuilder();
            msgBuilder.setTitle(randAnime.getName());
            msgBuilder.addField("Genre",randAnime.getGenre(), false);
            msgBuilder.addField("Episodes", ""+randAnime.getEpisodes(), false);
            msgBuilder.addField("Rating", ""+ randAnime.getRating(), false);

            event.getChannel().sendMessage(msgBuilder.build()).complete();
            found = true;

            if(found){
                event.getChannel().sendMessage("Random Anime: **" + randAnime.getName() + "**").complete();
            }

    }
    //get random function
    Anime getRandom(){
        Random random = new Random();
        int index = random.nextInt(list.getSize());
        return list.list.get(index);

    }

    //prototype function for random genre
    void randomGenre(MessageReceivedEvent event){
        String input = event.getMessage().getContentRaw();
        boolean found = false;


        Anime randAnime = getRandom();

        EmbedBuilder msgBuilder = new EmbedBuilder();
        msgBuilder.addField("Random Genre",randAnime.getGenre(), false);
        event.getChannel().sendMessage(msgBuilder.build()).complete();
        found = true;


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
        int newAnimeId = Integer.parseInt(listCopy.get(0).getAnime_id()) + 1;
        Anime newAnime = new Anime(Integer.toString(newAnimeId), animeName,"" ,"" ,0 ,0 ,0);
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

    void testPieChart(MessageReceivedEvent event){
        MakeChart chart = new MakeChart();
        chart.createTestPieChart("Test Chart");
        event.getChannel().sendFile(new File("temp.png")).complete();
    }

    void testBarChart(MessageReceivedEvent event){
        MakeChart chart = new MakeChart();
        chart.createTestBarChart("Test Chart");
        event.getChannel().sendFile(new File("temp.png")).complete();
    }

    /*
    String[] genres = new String[500];
    int genrecount = 0;
    void checkGenres(String genre){
        boolean found = false;
        for(int i = 0; i < genres.length; i++){
            if(genre != genres[i]) found = true;
        }
        if (!found) genres[genrecount] = genre;
    }
    void genrePieGraph(MessageReceivedEvent event){
        StringBuilder coolstring = new StringBuilder();
        for(int i = 0; i < list.getSize(); i++){
            Anime tempAnime = list.getList().get(i);
            String temp1 = tempAnime.getGenre().replace('\"',' ');
            String[] tempgenres = temp1.split(",");
            for(int j = 0; j < tempgenres.length; j++){
                checkGenres(tempgenres[j]);
            }
        }
        for(int i = 0; i < genres.length; i++){
            coolstring.append(genres[i] + "\n");
        }
        event.getChannel().sendMessage(coolstring.toString()).complete();
    }
     */

    //don't uncomment this one unless you want to see how long it would take to
    //parse through the second file that is really long
    //I say Brian will take over 20 minutes or just crash, requires at least
    //700 Mb's of ram available to run

    /*
    void ratingsGraph(MessageReceivedEvent event){
        String input = event.getMessage().getContentRaw();
        boolean found = false;
        input = input.substring(8);
        Anime foundAnime = new Anime();
        for(int i = 0; i <= list.getSize()-1; i++){
            Anime exampleAnime = list.getList().get(i);
            if (input.toUpperCase().replaceAll("\\s+","").equals(exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = list.getList().get(i);
                found = true;
                break;
            }
        }
        SimpleHistogramDataset dataset = new SimpleHistogramDataset(foundAnime.getName());
        SimpleHistogramBin bin0 = new SimpleHistogramBin(-0.1,0.1,false,false);
        SimpleHistogramBin bin1 = new SimpleHistogramBin(0.9,1.1,false,false);
        SimpleHistogramBin bin2 = new SimpleHistogramBin(1.9,2.1,false,false);
        SimpleHistogramBin bin3 = new SimpleHistogramBin(2.9,3.1,false,false);
        SimpleHistogramBin bin4 = new SimpleHistogramBin(3.9,4.1,false,false);
        SimpleHistogramBin bin5 = new SimpleHistogramBin(4.9,5.1,false,false);
        SimpleHistogramBin bin6 = new SimpleHistogramBin(5.9,6.1,false,false);
        SimpleHistogramBin bin7 = new SimpleHistogramBin(6.9,7.1,false,false);
        SimpleHistogramBin bin8 = new SimpleHistogramBin(7.9,8.1,false,false);
        SimpleHistogramBin bin9 = new SimpleHistogramBin(8.9,9.1,false,false);
        SimpleHistogramBin bin10 = new SimpleHistogramBin(9.9,10.1,false,false);
        SimpleHistogramBin bin11 = new SimpleHistogramBin(-1.1,-0.9,false,true);
        int none = 0;
        int zero = 0;
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        int six = 0;
        int seven = 0;
        int eight = 0;
        int nine = 0;
        int ten = 0;

        if(!found){
            event.getChannel().sendMessage("Anime was not found!").complete();
        }
        else {
            Rating rating;
            for(int i = 0; i <= rlist.getSize()-1; i++){
                rating = rlist.getList().get(i);
                if(foundAnime.getAnime_id().equals(rating.getAnime_id())){
                    if(rating.getRating() == -1) none++;
                    else if(rating.getRating() == 0) zero++;
                    else if(rating.getRating() == 1) one++;
                    else if(rating.getRating() == 2) two++;
                    else if(rating.getRating() == 3) three++;
                    else if(rating.getRating() == 4) four++;
                    else if(rating.getRating() == 5) five++;
                    else if(rating.getRating() == 6) six++;
                    else if(rating.getRating() == 7) seven++;
                    else if(rating.getRating() == 8) eight++;
                    else if(rating.getRating() == 9) nine++;
                    else if(rating.getRating() == 10) ten++;
                }
            }
            bin0.setItemCount(zero);
            bin1.setItemCount(one);
            bin2.setItemCount(two);
            bin3.setItemCount(three);
            bin4.setItemCount(four);
            bin5.setItemCount(five);
            bin6.setItemCount(six);
            bin7.setItemCount(seven);
            bin8.setItemCount(eight);
            bin9.setItemCount(nine);
            bin10.setItemCount(ten);
            bin11.setItemCount(none);
            dataset.addBin(bin11);
            dataset.addBin(bin0);
            dataset.addBin(bin1);
            dataset.addBin(bin2);
            dataset.addBin(bin3);
            dataset.addBin(bin4);
            dataset.addBin(bin5);
            dataset.addBin(bin6);
            dataset.addBin(bin7);
            dataset.addBin(bin8);
            dataset.addBin(bin9);
            dataset.addBin(bin10);

            MakeChart chart = new MakeChart();
            chart.createHistogram(foundAnime.getName() + " Ratings", dataset);
            event.getChannel().sendFile(new File("temp.png")).complete();
        }
    }

     */

    void barGraph(MessageReceivedEvent event){
        boolean found = false;
        String input1 = event.getMessage().getContentRaw();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if(input1.startsWith("!")){
            input1 = input1.substring(1);
            if(input1.equals("top10ratings")){
                for(int i = 0; i <= 10; i++) {
                    Anime exampleAnime = list.getList().get(i);
                    dataset.addValue(exampleAnime.getRating(), exampleAnime.getName(), " ");

                }
                MakeChart chart = new MakeChart();
                chart.createBarGraph("Ratings",dataset);
                event.getChannel().sendFile(new File("temp.png")).complete();

            }
        }
        if(input1.startsWith("!rgraph")){
            input1 = input1.substring(7);
            input1 = input1.replaceAll("\\s+","");
            String[] newinput = input1.split("&");
            for(int w = 0; w < newinput.length; w++){
                for (int j = 0; j <= list.getSize()-1 ; j++) {
                    Anime exampleAnime1 = list.getList().get(j);
                    if (newinput[w].toUpperCase().equals(exampleAnime1.getName().toUpperCase().replaceAll("\\s+", ""))) {
                        dataset.addValue(exampleAnime1.getRating(), exampleAnime1.getName(), " ");
                        found = true;

                    }
                }
            }
            if (!found)
            {
                event.getChannel().sendMessage("no results found").complete();
            }
            else {
                MakeChart chart = new MakeChart();
                chart.createBarGraph("RATINGS", dataset);
                event.getChannel().sendFile(new File("temp.png")).complete();
            }
        }
        if(input1.startsWith("!egraph")){
            input1 = input1.substring(7);
            input1 = input1.replaceAll("\\s+","");
            String[] newinput = input1.split("&");
            for(int w = 0; w < newinput.length; w++){
                for (int j = 0; j <= list.getSize()-1 ; j++) {
                    Anime exampleAnime1 = list.getList().get(j);
                    if (newinput[w].toUpperCase().equals(exampleAnime1.getName().toUpperCase().replaceAll("\\s+", ""))) {
                        dataset.addValue(exampleAnime1.getEpisodes(), exampleAnime1.getName(), " ");
                        found = true;

                    }
                }
            }
            if (!found)
            {
                event.getChannel().sendMessage("no results found").complete();
            }
            else {
                MakeChart chart = new MakeChart();
                chart.createBarGraph("EPISODES", dataset);
                event.getChannel().sendFile(new File("temp.png")).complete();
            }

        }

    }

    void saveFavorite(MessageReceivedEvent event){
        String input = event.getMessage().getContentRaw();
        boolean exists = false;
        boolean found = false;
        if(input.startsWith("favorite")){
            input = input.substring(8);
            for(int j = 0; j <= favorites.getSize()-1; j++){
                Anime favorite = favorites.getList().get(j);
                if (input.toUpperCase().replaceAll("\\s+","").equals(favorite.getName().toUpperCase().replaceAll("\\s+",""))){
                    event.getChannel().sendMessage("Anime already in list.").complete();
                    exists = true;
                }
            }
            if(!exists) {
                for (int i = 0; i <= list.getSize() - 1; i++) {
                    Anime anime = list.getList().get(i);
                    if (input.toUpperCase().replaceAll("\\s+", "").equals(anime.getName().toUpperCase().replaceAll("\\s+", ""))) {
                        favorites.add(anime);
                        event.getChannel().sendMessage(anime.getName() + " added to the favorite list.").complete();
                        found = true;
                    }
                }
                if (!found) {
                    event.getChannel().sendMessage("no results found").complete();
                }
            }
        }
    }

    void exportSave(MessageReceivedEvent event){
        event.getChannel().sendMessage("Exporting favorites, please wait...").complete();
        favorites.write();
        event.getChannel().sendMessage("Favorites exported!").complete();
    }

    MessageEmbed favoriteListEmbed(String message, boolean reset, boolean nextpage, boolean previouspage){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");
        formatter.setTimeZone(TimeZone.getTimeZone("PST"));
        Date date = new Date();
        if(reset) {
            animeListPage = 0;
            animeListSort = message;
        }
        if(nextpage) {
            if(animeListPage >= favorites.getSize()/15){
                animeListPage = 0;
            }
            else animeListPage++;
        }
        if(previouspage) {
            if(animeListPage == 0){
                animeListPage = favorites.getSize()/15;
            }
            else animeListPage--;
        }

        int startRank = animeListPage * 15;
        int endRank = startRank + 15;

        List<Anime> listCopy = new ArrayList<>(favorites.getList());
        //listCopy.sort(Comparator.comparing(Anime::getMembers));
        // Collections.reverse(listCopy);

        //gets most watched
        if(animeListSort.equals("ftopw")){
            //listCopy = new ArrayList<>(list.getList());
            listCopy.sort(Comparator.comparing(Anime::getMembers));
            Collections.reverse(listCopy);
        }

        //gets the top rating
        else if(animeListSort.equals("ftopr")){
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
        lbBuilder.addField("Anime rankings - Page " + (animeListPage == 0 ? 1 : animeListPage + 1), desc.toString(), false);

        if (animeListPage == 0)
            lbBuilder.addField("", descTwo.toString(), false);
        lbBuilder.addField("", descThree.toString(),false);

        lbBuilder.setColor(new Color(0, 153, 255));
        lbBuilder.setFooter("Today at " + formatter.format(date),
                "https://cdn.frankerfacez.com/emoticon/251321/4");
        return lbBuilder.build();
    }

    void deleteFavorite(MessageReceivedEvent event){
        String msgArray[] = event.getMessage().getContentRaw().split("[\\[\\]]+");
        String animeName = msgArray[1];
        Anime foundAnime = new Anime();
        boolean found = false;
        for(int i = 0; i <= favorites.getSize()-1; i++){
            Anime exampleAnime = favorites.getList().get(i);
            if(animeName.toUpperCase().replaceAll("\\s+","").equals
                    (exampleAnime.getName().toUpperCase().replaceAll("\\s+",""))){
                foundAnime = exampleAnime;
                favorites.deleteFavorite(i);
                found = true;
            }
        }
        if(found){
            event.getChannel().sendMessage("Deleted **\"" + foundAnime.getName()+ "\"** from the favorite list" +
                    "\n Don't forget to save the list after removing an item!").complete();
        }
        else event.getChannel().sendMessage("Anime **\"" + animeName + "\"** not found").complete();
    }

}