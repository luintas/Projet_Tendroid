import java.util.ArrayList;

public class PositionList extends ArrayList<Position>{

    public PositionList(){
        super();
    }
    public boolean add(int col,int lig){
        Position p = new Position(col, lig);
        this.add(p);
        return true;
    }
    public boolean contains(Position p){
        for(Position p1 : tab){
            if(p1.equals(p)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        
    }
}