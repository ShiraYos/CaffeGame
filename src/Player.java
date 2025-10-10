import java.awt.Image;

abstract class Player {

    protected FoodItem dish;
    protected Image playerImage;

    protected int playerX;
    protected int playerY;

    public Player() {
        
        this.dish = null;
        this.playerX = 100;
        this.playerY = 100;
    }

    public void setDish(FoodItem item) {
        this.dish = item;
    }

    public FoodItem getDish() {
        return this.dish;
    }

    public void setPlayerImage(Image image) {
        this.playerImage = image;
    }

    public Image getPlayerImage() {
        return this.playerImage;
    }
}
