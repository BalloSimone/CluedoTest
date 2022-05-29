import com.jme3.network.serializing.Serializable;

@Serializable
public class Coord {
    public int x;
    public int y;

    public Coord(){
        this.x = 0;
        this.y = 0;
    }

    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y){
        this.x = x;
        this.y = y;
    }


}
