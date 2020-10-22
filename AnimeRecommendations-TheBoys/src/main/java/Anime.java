public class Anime{
    private int anime_id;
    private String name;
    private String genre;
    private String type;
    private int episodes;
    private double rating;
    private int members;

    public Anime(){

    }

    Anime(int _anime_id, String _name, String _genre, String _type, int _episodes, double _rating, int _members){
        anime_id = _anime_id;
        name = _name;
        genre = _genre;
        type = _type;
        episodes = _episodes;
        rating = _rating;
        members = _members;
    }

    public int getAnime_id(){return anime_id;}

    public String getName(){return name;}

    public String getGenre(){return genre;}

    public String getType(){return type;}

    public int getEpisodes(){return episodes;}

    public double getRating(){return rating;}

    public int getMembers(){return members;}
}
