import java.io.*;
import java.util.*;

public class InventoryManager {

    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String fileName = "inventory.txt"; // Default file for inventory
    boolean running = true;

    while (running) {
        System.out.println("\n=== Inventory Manager ===");
        System.out.println("1. View Inventory");
        System.out.println("2. Add Item");
        System.out.println("3. Update Item");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                readInventory(fileName);
                break;
            case 2:
                System.out.print("Enter item name: ");
                String newItem = scanner.nextLine();
                System.out.print("Enter item count: ");
                int newCount = scanner.nextInt();
                addItem(fileName, newItem, newCount);
                break;
            case 3:
                System.out.print("Enter item name to update: ");
                String updateItem = scanner.nextLine();
                System.out.print("Enter new item count: ");
                int updateCount = scanner.nextInt();
                updateItem(fileName, updateItem, updateCount);
                break;
            case 4:
                running = false;
                System.out.println("Exiting Inventory Manager.");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }

    scanner.close();
}


    public static void readInventory(String fileName) {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        System.out.println("\n--- Inventory ---");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    } catch (IOException e) {
        System.out.println("Error reading inventory: " + e.getMessage());
    }
}


    // public static void addItem(String fileName, String itemName, int itemCount) {
    //     // TODO: Add a new item to the inventory
    // }

    public static void addItem(String fileName, String itemName, int itemCount) {
    File file = new File(fileName);
    boolean exists = false;

    try {
        // If file doesn't exist, create it
        if (!file.exists()) {
            file.createNewFile();
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(itemName)) {
                    exists = true;
                    break;
                }
            }
        }

        if (exists) {
            System.out.println("Item already exists. Use update to change the count.");
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(itemName + "," + itemCount);
                writer.newLine();
                System.out.println("Item added successfully.");
            }
        }
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
}


    public static void updateItem(String fileName, String itemName, int itemCount) {
    File tempFile = new File("temp.txt");
    File originalFile = new File(fileName);
    boolean found = false;

    try (
        BufferedReader reader = new BufferedReader(new FileReader(originalFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
    ) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equalsIgnoreCase(itemName)) {
                writer.write(itemName + "," + itemCount);
                found = true;
            } else {
                writer.write(line);
            }
            writer.newLine();
        }

        if (!found) {
            System.out.println("Item not found.");
        } else {
            System.out.println("Item updated.");
        }

        // Replace original file
        if (!originalFile.delete() || !tempFile.renameTo(originalFile)) {
            System.out.println("Error replacing file.");
        }
    } catch (IOException e) {
        System.out.println("Error updating item: " + e.getMessage());
    }
}

}
