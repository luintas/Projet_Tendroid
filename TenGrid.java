public class TenGrid extends Grid{
    public TenGrid(){
        super(5,5);
    }
    public Tengrid(int ns[]){
        super(5,5);
        int i;
        for(i=0;i<25;i++){
            super.tab[i/5][i%5] = ns[i];
        }
    }
    public PositionList getGroup(Position p){
        PositionList groupe = new PositionList();
        getGroupRec(p, groupe);
        return 
    }
    public PositionList getGroupRec(Position p, PositionList groupe){
        if(groupe.contains(p)){
            return null;
        }
        groupe.add(super.tab[p.getCol()][p.getLig()])
        int i=0;
        while(adjPosition(p)[i]){
            if(tab[adjPosition(p)[i].getCol()][adjPosition(p)[i].getLig()]==tab[p.getCol()][p.getLig()]){
                getGroupRec(adjPosition(p)[i],groupe);
            }
            i++;
        }
        return groupe;
    }
    public void collapseGroup(Position p){
        super.set(p,tab[p.getCol()][p.getLig()]+1);
        int i=1;
        while(getGroup(p)[i]){
            super.unset(getGroup(p)[i]);
            i++;
        }
    }
    public PositionList emptyPositions(){
        PositionList posvide = new PositionList();
        int i;
        for(i=0;i<25;i++){
            if(isEmpty.allPosition()[i]){
                posvide.add(allPosition()[i]);
            }
        }
        return posvide;
    }
    public void refill(int ns[]){
        int i=0;
        while(emptyPosition()[i]){
            tab[emptyPosition()[i].getCol()][emptyPosition()[i].getLig()]==ns[i];
            i++;
        }
    }

}