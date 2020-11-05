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
    public void setGenre(String g){genre = g;} //added set genre

    public String getType(){return type;}
    public void setType(String t){type = t;} //added set type

    public int getEpisodes(){return episodes;}
    public void setEpisodes(int e){episodes = e;}

    public double getRating(){return rating;}
    public void setRating(double d){rating = d;}

    public int getMembers(){return members;}
    public void setMembers(int m){members = m;}
}
