public class Position{
    private int col,lig;

    public Position(int col,int lig){
        this.col=col;
        this.lig=lig;
    }
    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }
    /**
     * @return the lig
     */
    public int getLig() {
        return lig;
    }
    public boolean equals(Position p){
        if((this.col==p.col)&&(this.lig==p.lig))
                return true;
        return false;
    }
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(this==null)
            return false;
        if(this.getClass()==obj.getClass()){
            Position p= (Position) obj;
            if((this.col==p.col)&&(this.lig==p.lig))
                return true;
        }
        return false;
    }
}