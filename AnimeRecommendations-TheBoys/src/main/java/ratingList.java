import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ratingList {
    private static final String fileName = "rating.csv";
    private static final String testSave = "savedRating.csv";
    private static final String backupFile = "backupListRating.csv";
    List<Rating> list = new ArrayList<Rating>();
    String[] temp = new String[7813738];
    int count = 0;

    String line;

    ratingList(){
        try{
            BufferedReader reader = Files.newBufferedReader(Paths.get(fileName));
            while((line = reader.readLine()) != null){
                temp[count] = line;
                count++;
            }
            // list add read files
            reader.close();
            start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void start(){
        for(int i = 0; i < 7813737; i++) {
            String[] tokens = temp[i].split(",");
            Rating rating = new Rating(tokens[0], tokens[1], tokens[2]);
            System.out.println(tokens[0]);
            addRatingToList(rating);
        }
        temp = null;
    }

    void addRatingToList(Rating rating){list.add(rating);}

    int getSize(){return list.size();}

    void write(){
        String save = ""; //String to be store

        for (Rating rating : list) { //per row
            save = save.concat(Integer.toString(rating.getUser_id())).concat(","); //ID
            save = save.concat(Integer.toString(rating.getAnime_id())).concat(","); //Episodes
            save = save.concat(Integer.toString(rating.getRating())).concat("\n"); //Members
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
    void ratingBackup(){

        String saveBU = "";

        for (Rating rating : list) { //per row
            saveBU = saveBU.concat(Integer.toString(rating.getUser_id())).concat(","); //ID
            saveBU = saveBU.concat(Integer.toString(rating.getAnime_id())).concat(","); //Episodes
            saveBU = saveBU.concat(Integer.toString(rating.getRating())).concat("\n"); //Members
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

    List<Rating> getList(){return list;}

    void updateRating(int listItem, int rating){list.get(listItem).setRating(rating);}

    void deleteRating(int i){list.remove(i);}
}
