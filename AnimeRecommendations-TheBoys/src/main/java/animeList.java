import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class animeList {
    private static final String fileName = "anime.csv";
    private static final String testSave = "saved.csv";
    List<Anime> list = new ArrayList<Anime>();

    String line;

    animeList(){
        try{
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            while((line = reader.readLine()) != null){
                String[] tokens2 = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                int anime_id = Integer.parseInt(tokens2[0]);
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

    int getSize(){
        return list.size();
    }

    void write(){
        String save = "";

        for (int i = 0; i < list.size() - 1; i++) {
            save = save.concat(Integer.toString(list.get(i).getAnime_id())).concat(",");
            save = save.concat(list.get(i).getName()).concat(",");
            save = save.concat(list.get(i).getGenre()).concat(",");
            save = save.concat(list.get(i).getType()).concat(",");
            save = save.concat(Integer.toString(list.get(i).getEpisodes())).concat(",");
            save = save.concat(Double.toString(list.get(i).getRating())).concat(",");
            save = save.concat(Integer.toString(list.get(i).getMembers())).concat(",");
        }
        save = save.concat(Integer.toString(list.get(list.size() - 1).getAnime_id())).concat(",");
        save = save.concat(list.get(list.size() - 1).getName()).concat(",");
        save = save.concat(list.get(list.size() - 1).getGenre()).concat(",");
        save = save.concat(list.get(list.size() - 1).getType()).concat(",");
        save = save.concat(Integer.toString(list.get(list.size() - 1).getEpisodes())).concat(",");
        save = save.concat(Double.toString(list.get(list.size() - 1).getRating())).concat(",");
        save = save.concat(Integer.toString(list.get(list.size() - 1).getMembers()));

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

    List<Anime> getList(){return list;}

}
