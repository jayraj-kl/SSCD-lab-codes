import java.io.*;
import java.util.*;

public class assignment_03 {
    static class MNTEntry {
        String name;
        int mdtIndex;

        MNTEntry(String name, int mdtIndex) {
            this.name = name;
            this.mdtIndex = mdtIndex;
        }
    }

    static class MDTEntry {
        String instruction;

        MDTEntry(String instruction) {
            this.instruction = instruction;
        }
    }

    static class ALAEntry {
        int index;
        String argument;

        ALAEntry(int index, String argument) {
            this.index = index;
            this.argument = argument;
        }
    }

    public static void main(String[] args) throws IOException {
        String inputPath = "./input/assignment_03/input.txt";
        String outputPath = "./output/assignment_03/output.txt";
        
        BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
        
        List<MNTEntry> mnt = new ArrayList<>();
        List<MDTEntry> mdt = new ArrayList<>();
        List<ALAEntry> alaList = new ArrayList<>();
        List<ALAEntry> alaAll = new ArrayList<>();  //

        boolean isMacro = false;
        String macroName = "";
        int mdtIndex = 1;
        int alaIndex = 1;
        int alaAllIndex = 1;                    //

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.equalsIgnoreCase("MACRO")) {
                isMacro = true;
                macroName = "";
                alaList.clear();
                continue;
            }

            if (isMacro) {
                if (line.equalsIgnoreCase("MEND")) {
                    mdt.add(new MDTEntry("MEND"));
                    isMacro = false;
                    continue;
                }
                if (macroName.isEmpty()) {
                    String[] parts = line.split("\\s+");
                    macroName = parts[0];
                    String[] argsArr = parts[1].split(",");

                    while (argsArr.length > 0) {
                        alaList.add(new ALAEntry(alaIndex, argsArr[0]));
                        alaAll.add(new ALAEntry(alaAllIndex, argsArr[0]));          //
                        argsArr = Arrays.copyOfRange(argsArr, 1, argsArr.length);
                        alaIndex++;
                        alaAllIndex++;                                             //
                    }

                    mnt.add(new MNTEntry(macroName, mdtIndex));
                    mdt.add(new MDTEntry(line));
                    mdtIndex++;
                } else {
                    String[] parts = line.split("\\s+");
                    String[] operands = parts[1].split(",");

                    for (ALAEntry alaEntry : alaList) {
                        if (operands[1].equals(alaEntry.argument)) {
                            line = parts[0] + " " + operands[0] + ",#" + alaEntry.index;
                            break;
                        }
                    }
                    mdt.add(new MDTEntry(line));
                    mdtIndex++;
                }
            } else {
                if (!line.equalsIgnoreCase("END"))
                    writer.write(line + "\n");
            }
        }
        writer.write("END\n");
        writer.write("--------------------\n");
        reader.close();
        writer.close();
        
        System.out.println("****** PASS 1 OUTPUT ******");
        
        System.out.println("MNT (Macro Name Table):");
        System.out.printf("%-10s %-15s %-10s%n", "Index", "Macro Name", "MDT Index");
        for (int i = 0; i < mnt.size(); i++) {
            System.out.printf("%-10d %-15s %-10d%n", i + 1, mnt.get(i).name, mnt.get(i).mdtIndex);
        }

        System.out.println("\nMDT (Macro Definition Table):");
        for (int i = 0; i < mdt.size(); i++) {
            System.out.printf("%-10d %-30s%n", i + 1, mdt.get(i).instruction);
        }

        System.out.println("\nALA (Argument List Array):");
        for (int i = 0; i < alaAll.size(); i++) {
            System.out.printf("%-10d %-20s%n", i + 1, alaAll.get(i).argument);
            
        }
    }
}