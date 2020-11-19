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

    public int getAnime_id(){return Integer.parseInt(anime_id);}

    public int getUser_id(){return Integer.parseInt(user_id);}

    public int getRating(){return Integer.parseInt(rating);}

    public void setRating(int d){rating = Integer.toString(d);}
}
