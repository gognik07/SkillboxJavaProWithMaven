package lesson9.tasks3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PATH_MOVEMENT_LIST = "src/main/resources/movementList.csv";
    private static final int COUNT_RECORDS = 8;

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(PATH_MOVEMENT_LIST));
        lines.remove(0);
        double debet = 0;
        double credit = 0;
        Map<String, Double> expenses = new HashMap<>();
        for (String line : lines) {
            String[] records = null;
            if (line.contains("\"")) {
                records = parseLine(line);
            } else {
                records = line.split(",");
            }
            if (records.length != COUNT_RECORDS) {
                System.out.println("Неверный формат строки");
                continue;
            }
            double moneyPlus = Double.parseDouble(records[6]);

            if (records[7].contains(",")) {
                records[7] = records[7].replace(",", ".");
            }
            double moneyMinus = Double.parseDouble(records[7]);
            if (moneyPlus == 0 && moneyMinus > 0) {
                credit += moneyMinus;
                addCategoryCredit(expenses, records[5], moneyMinus);
            }
            if (moneyMinus == 0 && moneyPlus > 0) {
                debet += moneyPlus;
            }
        }
        System.out.printf("Приход: %,.2f руб.\n", debet);
        System.out.printf("Расход: %,.2f руб.\n", credit);
        System.out.println();
        System.out.println("РАСХОД ПО КАТЕГОРИЯМ:");
        for (Map.Entry<String, Double> pair : expenses.entrySet()) {
            System.out.printf(pair.getKey() + " : " + "%,.2f руб.\n", pair.getValue());
        }
    }

    private static void addCategoryCredit(Map<String, Double> expenses, String operation, Double moneyMinus) {
        String accountingEntry = operation.split("\\s\\s+")[1];

        if (!accountingEntry.contains("\\")) {
            accountingEntry = operation.split("\\s\\s+")[2];
        }
        String[] entries = accountingEntry.contains("\\")
                ? accountingEntry.split("\\\\")
                : accountingEntry.split("/");
        String goal = entries[entries.length - 1];
        expenses.put(goal, expenses.containsKey(goal) ? expenses.get(goal) + moneyMinus : moneyMinus);
    }

    private static String[] parseLine(String line) {
        boolean isQuoteStart = false;
        String[] result = new String[8];
        int i = 0;
        String item = "";
        for (int j = 0; j < line.length(); j++) {
            char currentChar = line.charAt(j);
            if (currentChar == ',' && !isQuoteStart) {
                result[i++] = item;
                item = "";
                continue;
            }
            if (currentChar == '"') {
                isQuoteStart = !isQuoteStart;
                continue;
            }
            item +=currentChar;
        }
        result[i] = item;
        return result;
    }

}
