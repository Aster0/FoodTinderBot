package bot.data;

public class FoodData {


    private String name, thumbnail;

    public FoodData(String name, String thumbnail) {

        this.name = name;
        this.thumbnail = thumbnail;
    }


    public String getName() {
        return this.name;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }
}
