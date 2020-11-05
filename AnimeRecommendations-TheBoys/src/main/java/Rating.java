public class Rating {
    private int user_id;
    private int anime_id;
    private int rating;

    public Rating(){

    }

    Rating(int _anime_id, int _user_id, int _rating){
        user_id = _user_id;
        anime_id = _anime_id;
        rating = _rating;
    }

    public int getAnime_id(){return anime_id;}

    public int getUser_id(){return user_id;}

    public int getRating(){return rating;}
    public void setRating(int d){rating = d;}
}
