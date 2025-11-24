import java.util.ArrayList;
public class MemoryAllocation {

    ArrayList<MemoryBlock> memList;

    public MemoryAllocation(ArrayList<MemoryBlock> memList) {
        this.memList = memList; }

 
//Allocate memory (F/B/W)
 
    public void allocate(String pID, int reqSize, char strategy) {
        int index = -1;
        switch (Character.toUpperCase(strategy)) {

            case 'F':
                index = firstFit(reqSize);
                break;

            case 'B':
                index = bestFit(reqSize);
                break;

            case 'W':
                index = worstFit(reqSize);
                break;

            default:
                System.out.println("Invalid strategy.");
                return; }

        if (index == -1) {
            System.out.println("No suitable block found.");
            return; }

        MemoryBlock block = memList.get(index);

//perfect fit
        if (block.size == reqSize) {
            block.allocated = true;
            block.processID = pID;
            block.internalFlag = 0;

            System.out.println("Process " + pID + " allocated at: "
                    + block.startblock + " - " + block.endblock); }
//split block
        else {
            int oldStart = block.startblock;
            int oldEnd = block.endblock;

//allocated block
            MemoryBlock allocated = new MemoryBlock(
                    block.blocknum,
                    reqSize,
                    oldStart,
                    oldStart + reqSize - 1);
            allocated.allocated = true;
            allocated.processID = pID;

//remaining free block
            MemoryBlock freeBlock = new MemoryBlock(
                    memList.size() + 1,
                    block.size - reqSize,
                    allocated.endblock + 1,
                    oldEnd);

            memList.set(index, allocated);
            memList.add(index + 1, freeBlock);

            System.out.println("Process " + pID + " allocated at: "
                    + allocated.startblock + " - " + allocated.endblock); }}

//FIRST FIT
    private int firstFit(int req) {
        for (int i = 0; i < memList.size(); i++) {
            if (!memList.get(i).allocated && memList.get(i).size >= req) {
                return i; }}
        return -1; }

//BEST FIT
    private int bestFit(int req) {
        int best = -1;
        int bestSize = Integer.MAX_VALUE;

        for (int i = 0; i < memList.size(); i++) {
            MemoryBlock b = memList.get(i);

            if (!b.allocated && b.size >= req && b.size < bestSize) {
                best = i;
                bestSize = b.size; }}
        return best; }

//WORST FIT
    private int worstFit(int req) {
        int worst = -1;
        int worstSize = -1;

        for (int i = 0; i < memList.size(); i++) {
            MemoryBlock b = memList.get(i);

            if (!b.allocated && b.size >= req && b.size > worstSize) {
                worst = i;
                worstSize = b.size; }}
        return worst; }
}