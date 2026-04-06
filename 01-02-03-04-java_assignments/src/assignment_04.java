import java.io.*;
import java.util.*;

public class assignment_04 {
    static Map<Integer, String> MDT = Map.of(
        1, "INCR &A,&B",
        2, "LOAD #1",
        3, "ADD #2",
        4, "MEND"
    );

    static Map<String, Integer> MNT = Map.of(
        "INCR", 1
    );

    static Map<Integer, String> ALA = Map.of(
        1, "&A",
        2, "&B"
    );

    public static void main(String[] args) throws IOException {
        String inputPath = "./input/assignment_04/input.txt";
        String outputPath = "./output/assignment_04/output.txt";
        
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        
        writer.write("--------------------\n");
        System.out.println("\nPass 2 Output:");
        System.out.println("--------------------");
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] tokens = line.split("\\s+");
            String opcode = tokens[0];
            
            if (MNT.containsKey(opcode)) {
                String actualArgs = tokens.length > 1 ? tokens[1] : "";
                String[] actuals = actualArgs.split(",");
                
                Map<Integer, String> actualALA = new HashMap<>();
                for (int i = 0; i < actuals.length; i++) {
                    actualALA.put(i + 1, actuals[i].trim());
                }
                
                // Print ALA with actual arguments
                System.out.println("ALA (Argument List Array):");
                System.out.println("Index\tFormal\tActual");
                for (int idx : new TreeSet<>(ALA.keySet())) {
                    String formal = ALA.get(idx);
                    String actual = actualALA.getOrDefault(idx, "-");
                    System.out.println(idx + "\t" + formal + "\t" + actual);
                }
                
                int mdtIndex = MNT.get(opcode);
                
                int ptr = mdtIndex + 1;
                
                while (MDT.containsKey(ptr)) {
                    String mdtLine = MDT.get(ptr);
                    
                    if (mdtLine.equals("MEND")) break;
                    
                    String expandedLine = mdtLine;
                    for (Map.Entry<Integer, String> entry : actualALA.entrySet()) {
                        expandedLine = expandedLine.replace("#" + entry.getKey(), entry.getValue());
                    }
                    
                    writer.write(expandedLine + "\n");
                    System.out.println(expandedLine);
                    ptr++;
                }
            } else {
                writer.write(line + "\n");
                System.out.println(line);
            }
        }
        writer.write("--------------------\n");
        System.out.println("--------------------");
        reader.close();
        writer.close();
    }
}