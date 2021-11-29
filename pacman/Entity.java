package pacman;

public class Entity {

    private int x;
    private int y;

    public Entity(String entity) {
        switch (entity) {
            case "coco":
                x = 9*32+3;
                y = 16*32+3;
                break;
            case "red":
                x = 9*32+3;
                y = 8*32+3;
                break;
            case "pink":
                x = 9*32+3;
                y = 10*32+3;
                break;
            case "blue":
                x = 8*32+3;
                y = 10*32+3;
                break;
            case "orange":
                x = 10*32+3;
                y = 10*32+3;
                break;
        }
    }

    public Entity(String entity, int x, int y) {
        switch (entity) {
            case "dot":
                this.x = x*32+14;
                this.y = y*32+14;
                break;

            case "dotBig":
                this.x = x*32+8;
                this.y = y*32+8;
                break;

            case "message":
                this.x = x*32+8;
                this.y = y*32+8;
                break;
        }
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

}
