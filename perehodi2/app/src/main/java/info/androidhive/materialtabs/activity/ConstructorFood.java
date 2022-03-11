package info.androidhive.materialtabs.activity;

public class ConstructorFood {
    public String nameFood;
    public String imageId;
    public String desFood;
    public String priceFood;
    public String key;

    public ConstructorFood() {
    }

    public ConstructorFood(String nameFood, String desFood, String priceFood, String imageId, String key) {
        this.nameFood = nameFood;
        this.desFood = desFood;
        this.priceFood = priceFood;
        this.imageId = imageId;
        this.key = key;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDesFood() {
        return desFood;
    }

    public void setDesFood(String desFood) {
        this.desFood = desFood;
    }

    public String getPriceFood() {
        return priceFood;
    }

    public void setPriceFood(String priceFood) {
        this.priceFood = priceFood;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
