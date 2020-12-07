import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class favoriteList {
    private final animeList list = new animeList();
    private static final String favoriteSave = "savedFavorite.csv";
    List<Anime> saveList = new ArrayList<Anime>();

    String line;

    public favoriteList(){

    }

    void add(Anime anime){saveList.add(anime);}

    void deleteFavorite(int i){saveList.remove(i);}

    int getSize(){return saveList.size();}

    List<Anime> getList(){return saveList;}

    void write(){
        String save = ""; //String to be store

        for (Anime anime : saveList) { //per row
            save = save.concat(anime.getAnime_id()).concat(","); //ID
            save = save.concat(anime.getName()).concat(","); //Name
            save = save.concat(anime.getGenre()).concat(","); //Genre
            save = save.concat(anime.getType()).concat(","); //Type
            save = save.concat(Integer.toString(anime.getEpisodes())).concat(","); //Episodes
            save = save.concat(Double.toString(anime.getRating())).concat(","); //Ratings
            save = save.concat(Integer.toString(anime.getMembers())).concat("\n"); //Members
        }

        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(favoriteSave));
            //save list to files
            writer.write(save);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
