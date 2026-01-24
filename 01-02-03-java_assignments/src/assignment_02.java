import java.io.*;
import java.util.*;

public class assignment_02 {
    static Map<String, String> SYMBOL_TABLE = Map.of(
        "ONE", "104",
        "TWO", "105",
        "RESULT", "106"
    );

    public static void main(String[] args) throws IOException {
        String inputPath = "./input/assignment_02/IC.txt";
        String outputPath = "./output/assignment_02/MachineCode.txt";
        
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        
        writer.write("LC\tOpcode\tRegister\n");
        writer.write("--------------------\n");

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            
            String[] tokens = line.split("\t");

            String loc = tokens[0];
            String instr = (tokens.length > 1) ? tokens[1] : "-";
            String reg = (tokens.length > 2) ? tokens[2] : "-";
            String operand = (tokens.length > 3) ? tokens[3] : "-";

            if (instr.contains("AD") || instr.contains("DL")) continue;

            if (instr.startsWith("IS")) {
                String opcode = instr.split(",")[1];
                String register = reg.equals("-") ? "0" : reg;
                String memory = "000";
                
                if (operand.startsWith("S,")) {
                        String symName = operand.split(",")[1];
                        memory = SYMBOL_TABLE.get(symName);
                    } else if (operand.startsWith("C,")) {
                        memory = operand.split(",")[1];
                    }
                    writer.write(loc + "\t" + opcode + "\t" + register + "\t" + memory + "\n");
            }
        }
        writer.write("--------------------\n");
        reader.close();
        writer.close();
    }
}