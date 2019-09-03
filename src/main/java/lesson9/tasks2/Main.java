package lesson9.tasks2;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Откуда копировать?");
        String sourcePath = reader.readLine();
        System.out.println("Куда копировать?");
        String destinationPath = reader.readLine();

        copyFiles(sourcePath, destinationPath, "");
        System.out.println("Закончил!");
    }

    private static void copyFiles(String sourcePath, String destinationPath, String suffix) throws IOException {
        File sourceFile = new File(sourcePath + suffix);
        copyFile(sourcePath, destinationPath, suffix);
        if (sourceFile.isFile()) {
            return;
        }

        for (String fileName : sourceFile.list()) {
            String newSuffix = suffix + "/" + fileName;
            copyFiles(sourcePath, destinationPath, newSuffix);
        }
    }

    private static void copyFile(String sourcePath, String destinationPath, String suffix) throws IOException {
        File sourceFile = new File(sourcePath + suffix);
        File destinationFile = new File(destinationPath + "/" + suffix);
        if (sourceFile.isFile()) {
            copyFile(sourceFile, destinationFile);
        } else {            
            destinationFile.mkdir();
        }
    }

    private static void copyFile(File sourceFile, File destinationFile) throws IOException {
        FileInputStream is = new FileInputStream(sourceFile);
        FileOutputStream os = new FileOutputStream(destinationFile);
        for (;;) {
            int code = is.read();
            if (code < 0) {
                break;
            }
            os.write(code);
        }
        is.close();
        os.flush();
        os.close();
    }

}
