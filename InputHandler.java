import java.util.Scanner;
import java.util.ArrayList;

public class InputHandler {
     Scanner input = new Scanner(System.in);
    public ArrayList<MemoryBlock> memList = new ArrayList<>();

    public void initializeMemory() {

        System.out.println("Enter total memory size (MAX): ");
        int max = input.nextInt();

        // blocknum = 1
        // size = max
        // start = 0
        // end = max-1
        MemoryBlock firstBlock = new MemoryBlock(1, max, 0, max - 1);

        memList.add(firstBlock);

        System.out.println("\nInitial memory state:\n");
        displayMemory();
    }//enfd ini

    public void displayMemory() {
        System.out.println("\nCurrent memory blocks:\n");

        for (int i = 0; i < memList.size(); i++) {
            memList.get(i).printBlock();
        }
    }
    
}
