import java.io.*;
import java.util.*;

public class assignment_01 {
    public static void main(String[] args) throws IOException {

        BufferedReader br;
        FileReader fr;
        FileWriter fw;
        BufferedWriter bw;

        int LC = 0;
        String Intstropcode;

        String INPUT_FILE_NAME = "./input/assignment_01/input.txt";
        fr = new FileReader(INPUT_FILE_NAME);
        br = new BufferedReader(fr);

        String OUTPUT_FILE_NAME = "./output/assignment_01/IC.txt";
        File outputFile = new File(OUTPUT_FILE_NAME);
        if (outputFile.getParentFile() != null) {
            outputFile.getParentFile().mkdirs();
        }
        fw = new FileWriter(outputFile);
        bw = new BufferedWriter(fw);

        // Imperative statements
        Hashtable<String, String> is = new Hashtable<>();
        is.put("STOP", "00");
        is.put("ADD", "01");
        is.put("SUB", "02");
        is.put("MULT", "03");
        is.put("MOVER", "04");
        is.put("MOVEM", "05");
        is.put("COMP", "06");
        is.put("BC", "07");
        is.put("DIV", "08");
        is.put("READ", "09");
        is.put("PRINT", "10");

        // Assembler directives
        Hashtable<String, String> ad = new Hashtable<>();
        ad.put("START", "01");
        ad.put("END", "02");
        ad.put("ORIGIN", "03");
        ad.put("EQU", "04");
        ad.put("LTORG", "05");

        // Declaration statements
        Hashtable<String, String> dl = new Hashtable<>();
        dl.put("DC", "01");
        dl.put("DS", "02");

        // Use LinkedHashMap to maintain insertion order
        LinkedHashMap<String, String> symbtab = new LinkedHashMap<>();
        String reg = "";
        String ICode;
        String line;

        bw.write("LC\tOpcode\top1\top2\n");
        bw.write("-".repeat(20) + "\n");

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split("\\s+");
            ICode = "";

            // START directive
            if (parts[0].equals("START")) {
                LC = Integer.parseInt(parts[1]);
                Intstropcode = ad.get("START");
                ICode = "-\tAD," + Intstropcode + "\t-\tC," + LC;
            }

            // END directive
            else if (parts[0].equals("END")) {
                Intstropcode = ad.get("END");
                ICode = "-\tAD," + Intstropcode;
            }

            // Declaration like DC or DS
            else if (parts.length == 3 && dl.containsKey(parts[1])) {
                String label = parts[0];
                String declType = parts[1];
                String value = parts[2];

                Intstropcode = dl.get(declType);
                symbtab.put(label, String.valueOf(LC));
                ICode = LC + "\tDL," + Intstropcode + "\t-\tC," + value;
                LC++;
            }

            // Imperative with Label
            else if (parts.length == 4) {
                String label = parts[0];
                String opcode = parts[1];
                String regCode = parts[2];
                String symbol = parts[3];

                symbtab.putIfAbsent(label, String.valueOf(LC));
                Intstropcode = is.get(opcode);
                reg = getRegisterCode(regCode);
                symbtab.putIfAbsent(symbol, "-"); // Undefined now, maybe defined later
                ICode = LC + "\tIS," + Intstropcode + "\t" + reg + "\tS," + symbol;
                LC++;
            }

            // Imperative without Label
            else if (parts.length == 3) {
                String opcode = parts[0];
                String regCode = parts[1];
                String symbol = parts[2];

                Intstropcode = is.get(opcode);
                reg = getRegisterCode(regCode);
                symbtab.putIfAbsent(symbol, "-");
                ICode = LC + "\tIS," + Intstropcode + "\t" + reg + "\tS," + symbol;
                LC++;
            }

            // Instruction with just opcode like STOP
            else if (parts.length == 1) {
                String opcode = parts[0];

                Intstropcode = is.get(opcode);

                if (Intstropcode != null) {
                    ICode = LC + "\tIS," + Intstropcode;
                    LC++;
                } else {
                    ICode = "Invalid instruction: " + line;
                }
            }

            // Invalid format fallback
            else {
                ICode = "Invalid instruction format: " + line;
            }

            bw.write(ICode + "\n");
        }

        bw.write("-".repeat(20) + "\n");
        br.close();
        bw.close();

        // Print the symbol table
        System.out.println("****** SYMTAB is: ******");
        for (Map.Entry<String, String> entry : symbtab.entrySet()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }

        System.out.println("\nIC.txt has been generated successfully.");
    }

    // Helper function for register codes
    private static String getRegisterCode(String regCode) {
        switch (regCode) {
            case "AREG": return "1";
            case "BREG": return "2";
            case "CREG": return "3";
            default: return "0";
        }
    }
}