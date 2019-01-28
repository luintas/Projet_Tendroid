public class TenGrid extends Grid{
    public TenGrid(){
        super(5,5);
    }
    public TenGrid(Integer ns[]){
        super(5,5);
        int i;
        for(i=0;i<25;i++){
            super.tab[i/5][i%5] = ns[i];
        }
    }
    public PositionList getGroup(Position p){
        PositionList groupe = new PositionList();
        getGroupRec(p, groupe);
        return groupe;
    }
    public PositionList getGroupRec(Position p, PositionList groupe){
        if(groupe.contains(p)==true){
            return null;
        }
        groupe.add(new Position(p.getCol(),p.getLig()));
        int i=0;
        while(adjPosition(p).get(i)!=null){
            if(tab[adjPosition(p).get(i).getCol()][adjPosition(p).get(i).getLig()]==tab[p.getCol()][p.getLig()]){
                getGroupRec(adjPosition(p).get(i),groupe);
            }
            i++;
        }
        return groupe;
    }
    public void collapseGroup(Position p){
        super.set(p,tab[p.getCol()][p.getLig()]+1);
        int i=1;
        while(getGroup(p).get(i)!=null){
            super.unset(getGroup(p).get(i));
            i++;
        }
    }
    public PositionList emptyPositions(){
        PositionList posvide = new PositionList();
        int i;
        for(i=0;i<25;i++){
            if(isEmpty(super.allPosition().get(i))==true){
                posvide.add(allPosition().get(i));
            }
        }
        return posvide;
    }
    public void refill(int ns[]){
        int i=0;
        while(emptyPositions().get(i)!=null){
            tab[emptyPositions().get(i).getCol()][emptyPositions().get(i).getLig()]=ns[i];
            i++;
        }
    }
    public void pack(){
        /*for(i=0;i<5;i++){
            for(j=0;j<5;j++){
                if(tab[i][j]==null){
                    k=0
                    while(tab[i][j-k]==null && j-k>=0){
                        
                        k++;
                        
                    }
                }
            }
        }*/
        for(i=0;i<5;i++){
            for(j=4;j>=0;j--){
                if(tab[i][j]){
                    k=1;
                    while(tab[i][j-k]==null && j-k>=0){
                        k++;
                    }
                    if(tab[i][j-1]!=null && j-k>-1){
                        set(new Position(i,j+k-1),tab[i][j];
                        unset(new Position(i,j);
                    }
                }
            }
        }
        /*
        PositionList intermediaire = new PositionList();
        intermediaire = emptyPositions();
        while(intermediaire.get(i)!=null){
            int i = intermediaire.get(i).getLig();
        while(i<5){
                if(tab[intermediaire.get(i).getLig()+1][intermediaire.get(i).getCol()]!=null){
                    
                }

            }
        }*/
    }
    public static void main(String[] args){
        System.out.println((new TenGrid()).nbCol == 5);
        System.out.println((new TenGrid()).nbLig == 5);
        Integer[] ns = new Integer[52];
        System.out.println((new TenGrid(ns)).nbCol == 5);
        System.out.println((new TenGrid(ns)).nbLig == 5);
        int i;
        for(i=0;i<25;i++){
        System.out.println((new TenGrid(ns)).get(new Position(i%5,i/5)) == ns[i]);
        System.out.println((new TenGrid(ns)).get(p) == ns[p.nbLig()*5+p.nbCol()]);
        }
    }
}
