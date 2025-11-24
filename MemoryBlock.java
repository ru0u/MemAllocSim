public class MemoryBlock {
    
     public int blocknum;
    public int size;
    public int startblock;
    public int endblock;
    public int internalFlag;
    public boolean allocated;
    public String processID;

    public MemoryBlock(int blocknum,int size ,int startblock , int endblock){
        this.blocknum=blocknum;
        this.size=size;
        this.startblock=startblock;
        this.endblock=endblock;
        this.allocated=false;
        this.processID="*";
        this.internalFlag=0;

    }//end cons
    
    public void printBlock(){
        System.out.println( "Block #" + blocknum +
            " | Start: " + startblock +
            " | End: " + endblock +
            " | Size: " + size +
            " | Allocated: " + allocated +
            " | Process: " + processID +
            " | Internal Frag: " + internalFlag);
    }

}
