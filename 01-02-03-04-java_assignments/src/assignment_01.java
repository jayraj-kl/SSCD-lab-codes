import java.io.*;
import java.util.*;

public class assignment_01 {
    static Map<String, String> IMPERATIVE = Map.of(
        "STOP",  "00",
        "ADD",   "01",
        "SUB",   "02",
        "MULT",  "03",
        "MOVER", "04",
        "MOVEM", "05",
        "COMP",  "06",
        "BC",    "07",
        "DIV",   "08",
        "READ",  "09"
    );
    static Map<String, String> DIRECTIVE = Map.of(
        "START",  "01",
        "END",    "02",
        "ORIGIN", "03",
        "EQU",    "04",
        "LTORG",  "05"
    );
    static Map<String, String> DECLARATION = Map.of(
        "DC", "01",  
        "DS", "02"   
    );
    static Map<String, String> REGISTER = Map.of(
        "AREG", "1",
        "BREG", "2",
        "CREG", "3"
    );

    public static void main(String[] args) throws IOException {
        String inputPath = "./input/assignment_01/input.txt";
        String outputPath = "./output/assignment_01/IC.txt";
        
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
    
        int LC = 0;
        LinkedHashMap<String, String> symbolTable = new LinkedHashMap<>();
        String output;
        
        writer.write("LC\tOpcode\top1\top2\n");
        writer.write("--------------------\n");
        
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] tokens = line.split("\\s+");
            output = "";
            
            // START directive
            if (tokens[0].equals("START")) {
                LC = Integer.parseInt(tokens[1]);
                output = "-\tAD," + DIRECTIVE.get("START") + "\t-\tC," + LC;
            }
            // END directive
            else if (tokens[0].equals("END")) {
                output = "-\tAD," + DIRECTIVE.get("END");
            }
            // Declaration with label (e.g., "X DC 5" or "Y DS 1")
            else if (tokens.length == 3 && DECLARATION.containsKey(tokens[1])) {
                String label = tokens[0];
                String type = tokens[1];
                String value = tokens[2];
                
                symbolTable.put(label, String.valueOf(LC));
                output = LC + "\tDL," + DECLARATION.get(type) + "\t-\tC," + value;
                LC++;
            }
            // Instruction with label (e.g., "LOOP MOVER AREG X")
            else if (tokens.length == 4) {
                String label = tokens[0];
                String opcode = tokens[1];
                String reg = tokens[2];
                String operand = tokens[3];
                
                symbolTable.putIfAbsent(label, String.valueOf(LC));
                symbolTable.putIfAbsent(operand, "-");
                output = LC + "\tIS," + IMPERATIVE.get(opcode) + "\t" + REGISTER.getOrDefault(reg, "0") + "\tS," + operand;
                LC++;
            }
            // Instruction without label (e.g., "MOVER AREG X")
            else if (tokens.length == 3) {
                String opcode = tokens[0];
                String reg = tokens[1];
                String operand = tokens[2];
                
                symbolTable.putIfAbsent(operand, "-");
                output = LC + "\tIS," + IMPERATIVE.get(opcode) + "\t" + REGISTER.getOrDefault(reg, "0") + "\tS," + operand;
                LC++;
            }
            // Single instruction (e.g., "STOP")
            else if (tokens.length == 1 && IMPERATIVE.containsKey(tokens[0])) {
                output = LC + "\tIS," + IMPERATIVE.get(tokens[0]);
                LC++;
            }
            else {
                output = "Invalid: " + line;
            }
            
            writer.write(output + "\n");
        }
        
        writer.write("--------------------\n");
        reader.close();
        writer.close();
        
        System.out.println("****** SYMBOL TABLE ******");
        symbolTable.forEach((name, address) -> 
            System.out.println(name + "\t" + address)
        );
        System.out.println("\nIC.txt generated successfully.");
    }
}