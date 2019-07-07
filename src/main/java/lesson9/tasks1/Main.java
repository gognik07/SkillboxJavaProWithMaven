package lesson9.tasks1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class Main {

    private static final long K_BYTE = 1024;
    private static final long M_BYTE = K_BYTE * 1024;
    private static final long G_BYTE = M_BYTE * 1024;


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        File file = new File(reader.readLine());

        long sizeFolder = calculateSize(file);

        System.out.println(convertSize(sizeFolder));
    }

    private static String convertSize(long size) {
        if (size / G_BYTE > 0) {
            return Long.toString(size / G_BYTE) + " гБайт";
        }

        if (size / M_BYTE > 0) {
            return Long.toString(size / M_BYTE) + " Мбайт";
        }

        if (size / K_BYTE > 0) {
            return Long.toString(size / K_BYTE) + " кБайт";
        }


        return Long.toString(size) + " байт";
    }

    private static long calculateSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }

        long sizeFolder = 0;

        for (String fileName : folder.list()) {
            String prefix = folder.getAbsolutePath() + "/";
            File file = new File(prefix + fileName);
            if (file.isFile()) {
                sizeFolder += file.length();
            } else {
                sizeFolder += calculateSize(file);
            }
        }

        return sizeFolder;
    }
}
