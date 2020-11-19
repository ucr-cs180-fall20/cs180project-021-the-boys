import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class animeList {
    private static final String fileName = "anime.csv";
    private static final String testSave = "savedAnime.csv";
    private static final String backupFile = "backupListAnime.csv";
    List<Anime> list = new ArrayList<Anime>();

    String line;

    animeList(){
        try{
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            while((line = reader.readLine()) != null){
                String[] tokens2 = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String anime_id = tokens2[0];
                System.out.println(anime_id);
                String name = tokens2[1];
                System.out.println(name);
                String genre = tokens2[2];
                System.out.println(genre);
                String type = tokens2[3];
                System.out.println(type);
                int episodes;
                try {
                    episodes = Integer.parseInt(tokens2[4]);
                }
                catch (Exception e){
                    episodes = -1;
                }
                System.out.println(episodes);
                double rating;
                try {
                    rating = Double.parseDouble(tokens2[5]);
                }
                catch (Exception e){
                    rating = -1;
                }
                System.out.println(rating);
                int members = Integer.parseInt(tokens2[6]);
                System.out.println(members);
                Anime anime = new Anime(anime_id, name, genre, type, episodes, rating, members);
                addAnimeToList(anime);
            }
            // list add read files
            reader.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void addAnimeToList(Anime anime){list.add(anime);}

    int getSize(){return list.size();}

    void write(){
        String save = ""; //String to be store

        for (Anime anime : list) { //per row
            save = save.concat(anime.getAnime_id()).concat(","); //ID
            save = save.concat(anime.getName()).concat(","); //Name
            save = save.concat(anime.getGenre()).concat(","); //Genre
            save = save.concat(anime.getType()).concat(","); //Type
            save = save.concat(Integer.toString(anime.getEpisodes())).concat(","); //Episodes
            save = save.concat(Double.toString(anime.getRating())).concat(","); //Ratings
            save = save.concat(Integer.toString(anime.getMembers())).concat("\n"); //Members
        }

        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(testSave));
            //save list to files
            writer.write(save);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //backup function
    void animeBackup(){

        String saveBU = "";

        for (Anime anime : list) { //per row
            saveBU = saveBU.concat(anime.getAnime_id()).concat(","); //ID
            saveBU = saveBU.concat(anime.getName()).concat(","); //Name
            saveBU = saveBU.concat(anime.getGenre()).concat(","); //Genre
            saveBU = saveBU.concat(anime.getType()).concat(","); //Type
            saveBU = saveBU.concat(Integer.toString(anime.getEpisodes())).concat(","); //Episodes
            saveBU = saveBU.concat(Double.toString(anime.getRating())).concat(","); //Ratings
            saveBU = saveBU.concat(Integer.toString(anime.getMembers())).concat("\n"); //Members
        }

        try{
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(backupFile));
            //save list to files
            writer.write(saveBU);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    List<Anime> getList(){return list;}

    void updateEpisodes(int listItem, int episodes){list.get(listItem).setEpisodes(episodes);}

    void updateRating(int listItem, double rating){list.get(listItem).setRating(rating);}

    void updateMembers(int listItem, int members){list.get(listItem).setMembers(members);}

    //added update genre
    void updateGenre(int listItem, String genre){list.get(listItem).setGenre(genre);}

    //added update type
    void updateType(int listItem, String type){list.get(listItem).setType(type);}



    void deleteAnime(int i){list.remove(i);}
}
