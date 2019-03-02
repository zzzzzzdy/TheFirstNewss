package activitytest.exmaple.com.thefirstshoes;

public class Fruit {
    private String title;
    private int id;
    private  String url;

    public Fruit(String title, int id, String url) {

        this.title = title;
        this.id = id;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
