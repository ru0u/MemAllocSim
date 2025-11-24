import java.util.*;

public class MemoryRelease {

    ArrayList<MemoryBlock> memList;

    public MemoryRelease(ArrayList<MemoryBlock> memList) {
        this.memList = memList;
    }

    public void release(String pID) {

        boolean found = false;

        for (int i = 0; i < memList.size(); i++) {
            MemoryBlock block = memList.get(i);

            if (block.allocated && block.processID.equals(pID)) {

                block.allocated = false;
                block.processID = "*";
                block.internalFlag = 0;

                
                System.out.println("Process " + pID + " released.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("No allocated block found for Process " + pID);
            return;
        }

        mergeFreeBlocks();
    }

    private void mergeFreeBlocks() {

        for (int i = 0; i < memList.size() - 1; i++) {

            MemoryBlock current = memList.get(i);
            MemoryBlock next = memList.get(i + 1);

            if (!current.allocated && !next.allocated) {

                current.endblock = next.endblock;
                current.size = current.endblock - current.startblock + 1;

                memList.remove(i + 1);

                i--;
            }
        }
    }
}


