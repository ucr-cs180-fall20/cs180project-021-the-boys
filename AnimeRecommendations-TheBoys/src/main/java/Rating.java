public class Rating {
    private String user_id;
    private String anime_id;
    private String rating;

    public Rating(){

    }

    Rating(String _anime_id, String _user_id, String _rating){
        user_id = _user_id;
        anime_id = _anime_id;
        rating = _rating;
    }

    public String getAnime_id(){return anime_id;}

    public String getUser_id(){return user_id;}

    public String getRating(){return rating;}

    public void setRating(int d){rating = Integer.toString(d);}
}
