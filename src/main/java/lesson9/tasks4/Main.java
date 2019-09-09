package lesson9.tasks4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите путь");
        String path = reader.readLine();
        Document doc = Jsoup.connect("https://lenta.ru/").get();
        Elements images = doc.getElementsByTag("img");

        images.forEach(e -> {
            try {
                String pathImage = e.absUrl("src");
                URL urlImage = new URL(pathImage);
                String namePicture = pathImage.split("/")[pathImage.split("/").length - 1];
                if (namePicture.contains("?")) {
                    return;
                }

                InputStream is = new BufferedInputStream(urlImage.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1!=(n=is.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                is.close();

                byte[] response = out.toByteArray();
                FileOutputStream fos = new FileOutputStream(path + "\\" + namePicture);
                fos.write(response);
                fos.flush();
                fos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
