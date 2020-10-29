import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class Operations {
    private final animeList list = new animeList();

    Operations(){

    }

    void messageEmbed(MessageReceivedEvent event){
        list.write();
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
        listCopy.sort(Comparator.comparing(Anime::getMembers));
        Collections.reverse(listCopy);

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
}
