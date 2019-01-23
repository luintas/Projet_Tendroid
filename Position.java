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
        if(this==p)
            return true;
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
    public static void main(String[] args) {
        int i1=4,i2=32;
        Position p = new Position(i1, i2);
        PositionList ps = PositionList();
        System.out.println(new Position(i1,i2).getCol() == i1);
        System.out.println(new Position(i1,i2).getLig() == i2);
        System.out.println(p.equals(p));
        System.out.println(p.equals(new Position(i1,i2)));
        System.out.println(p.equals(new Position(i2,i1)));
        ps.add(p);
        
    }
}