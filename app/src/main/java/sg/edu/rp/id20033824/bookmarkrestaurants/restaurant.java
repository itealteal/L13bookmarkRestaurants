package sg.edu.rp.id20033824.bookmarkrestaurants;

import java.io.Serializable;

public class restaurant implements Serializable {
    private int id;
    private String name;
    private String description;
    private String cuisine;
    private int stars;

    public restaurant(int id, String name, String cuisine, String description, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cuisine = cuisine;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
