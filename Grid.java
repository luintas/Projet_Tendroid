public class Grid{
    public int nbCol;
    public int nbLig;
    public Integer tab[][];


    public Grid(int nbLig, int nbCol){
        this.nbLig = nbLig;
        this.nbCol = nbCol;
        tab = new Integer [nbCol][nbLig];
    }

    public int nbCol(){
        return nbCol;
    }

    public int nbLig(){
        return nbLig;
    }

    public PositionList allPositon(){
        PositionList allpos = new PositionList();
        int i,j;
        for(i=0;i<nbCol;i++){
            for(j=0;j<nbLig;j++){
                allpos.add(i,j);
            }
        }
        return allpos;
    }

    public boolean regularPosition(Position p){
        if(p.getCol()<0 || p.getLig()<0 || p.getCol()>nbCol-1 || p.getLig()>nbLig-1 ){
            return false;
        }
        return true;
    }

    public boolean isEmpty(Position p){
        if(tab[p.getCol()][p.getLig()] == null){
            return true;
        }
        return false;
    }

    public Integer get(Position p){
        return tab[p.getCol()][p.getLig()];
    }

    public void set(Position p, Integer v){
        tab[p.getCol()][p.getLig()] = v;
    }

    public void unset(Position p){
        tab[p.getCol()][p.getLig()] = null;
    }

    public PositionList adjPosition(Position p){
        PositionList adjpos = new PositionList();
        Position p1 = new Position(p.getCol()+1, p.getLig());
        if(regularPosition(p1)){
            adjpos.add(p1);
        }
        p1 = new Position(p.getCol()-1, p.getLig());
        if(regularPosition(p1)){
            adjpos.add(p1);
        }
        p1 = new Position(p.getCol(), p.getLig()+1);
        if(regularPosition(p1)){
            adjpos.add(p1);
        }
        p1 = new Position(p.getCol(), p.getLig()-1);
        if(regularPosition(p1)){
            adjpos.add(p1);
        }
        return adjpos;
    }
}