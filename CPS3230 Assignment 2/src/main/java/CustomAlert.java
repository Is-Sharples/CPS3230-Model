
public class CustomAlert {
    String url;
    String id;
    String description;
    String heading;
    String imageUrl;
    String postedBy;
    int priceInCents;
    String postDate;
    int alertType;
    public boolean login;

    public CustomAlert(String url, String id, String heading, String desc, String imageUrl, String postedBy, int priceInCents, String postDate, int alertType) {
        this.url = url;
        this.id = id;
        this.description = desc;
        this.heading = heading;
        this.imageUrl = imageUrl;
        this.postedBy = postedBy;
        this.priceInCents = priceInCents;
        this.postDate = postDate;
        this.alertType = alertType;
        login = false;
    }
}
