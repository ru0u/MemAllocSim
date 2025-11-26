import java.util.Scanner;
import java.util.ArrayList;

public class MemorySimulator {

    private static ArrayList<MemoryBlock> memoryBlocks = new ArrayList<>();
    private static int MAX_MEMORY_SIZE = 0;
    private static Scanner input = new Scanner(System.in);
    
    
    private static MemoryAllocation allocator;
    private static MemoryRelease releaser;

    public static void main(String[] args) {
        
        System.out.println("Memory Allocation Simulator\n-----------------------------------------------");
        displayMenu();
        initializeMemoryFromUser(); 
        
        allocator = new MemoryAllocation(memoryBlocks);
        releaser = new MemoryRelease(memoryBlocks);

        int choice = -1;
        
        while (choice != 5) {
            System.out.print("Enter your choice: ");
            
            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine(); 

                switch (choice) {
                    case 1:
                        handleAllocationRequest();
                        break;
                    case 2:
                        handleReleaseRequest();
                        break;
                    case 3:
                        compactMemory(memoryBlocks, MAX_MEMORY_SIZE);
                        break;
                    case 4:
                        displayMemoryStatus(memoryBlocks);
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                input.nextLine(); 
            }
        }
    }
    
    private static void displayMenu() {
        System.out.println("1. Request (Allocate) memory");
        System.out.println("2. Release memory");
        System.out.println("3. Compact memory");
        System.out.println("4. Display memory status");
        System.out.println("5. Exit");
        System.out.println("-----------------------------------------------");
    }

  private static void initializeMemoryFromUser() {
    while (true) { 
        System.out.print("Enter total memory size: ");
        
        if (input.hasNextInt()) {
            MAX_MEMORY_SIZE = input.nextInt();
            input.nextLine(); 

            if (MAX_MEMORY_SIZE > 0) {
                MemoryBlock firstBlock = new MemoryBlock(1, MAX_MEMORY_SIZE, 0, MAX_MEMORY_SIZE - 1);
                memoryBlocks.add(firstBlock);
                break;
            } else {
                System.out.println("Error: Memory size must be a positive integer.");
            }
        } else {
            System.out.println("Error: Invalid input. Please enter a valid positive number.");
            input.nextLine();
        }
    }
}
    
    private static void handleAllocationRequest() {
        System.out.print("Enter process name: ");
        String pName = input.nextLine();
        System.out.print("Enter size: ");
        
        if (!input.hasNextInt()) {
            System.out.println("Error Invalid size input.");
            input.nextLine();
            return;
        }
        int size = input.nextInt();
        input.nextLine();
        
        System.out.print("Enter strategy (F/B/W): ");
        String strategyLine = input.nextLine();
        if (strategyLine.isEmpty()) {
            System.out.println("Error Strategy cannot be empty.");
            return;
        }
        char strategy = strategyLine.toUpperCase().charAt(0);
        
        if (size <= 0) {
            System.out.println("Error: Size must be positive.");
            return;
        }
        
        allocator.allocate(pName, size, strategy);
    }
    
    private static void handleReleaseRequest() {
        System.out.print("Enter process name to release: ");
        String pName = input.nextLine();
        releaser.release(pName);
    }

  
    public static void compactMemory(ArrayList<MemoryBlock> memList, int maxMemorySize) {
        if (memList.isEmpty()) {
            System.out.println("Memory is empty Nothing to compact.");
            return;
        }

        int currentBaseAddress = 0;
        ArrayList<MemoryBlock> compactedList = new ArrayList<>(); 

        for (MemoryBlock block : memList) {
            if (block.allocated) {
                int blockSize = block.size;
                
                block.startblock = currentBaseAddress;
                block.endblock = currentBaseAddress + blockSize - 1;
                block.internalFlag = 0;
                compactedList.add(block);
                currentBaseAddress += blockSize; 
            }
        }
        
        int freeSpaceSize = maxMemorySize - currentBaseAddress;
        
        if (freeSpaceSize > 0) {
            MemoryBlock freeBlock = new MemoryBlock(999, freeSpaceSize, currentBaseAddress, maxMemorySize - 1);
            
            freeBlock.allocated = false; 
            freeBlock.processID = "*"; 
            freeBlock.internalFlag = 0;
            
            compactedList.add(freeBlock);
        }
        
        memList.clear();
        memList.addAll(compactedList);
        updateBlockNumbers(memList); 
        System.out.println("Memory compacted successfully.");
    }

    public static void displayMemoryStatus(ArrayList<MemoryBlock> memList) {
        System.out.println("\nMemory status:\n-----------------------------------------------");
        for (MemoryBlock block : memList) {
            String status = block.allocated ? "Process " + block.processID : "Unused (Free)"; 
            System.out.printf("Addresses [%d : %d] %s\n", block.startblock, block.endblock, status);
        }
        System.out.println("-----------------------------------------------");
    }

    private static void updateBlockNumbers(ArrayList<MemoryBlock> memList) {
        for (int i = 0; i < memList.size(); i++) {
            memList.get(i).blocknum = i + 1;
        }
    }
}