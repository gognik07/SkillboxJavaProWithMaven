package lesson9.tasks4;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
                if (namePicture.contains("?") || !namePicture.contains(".")) {
                    return;
                }

                BufferedImage bufferedImage = ImageIO.read(urlImage);
                ImageIO.write(bufferedImage, namePicture.split("\\.")[1], new File(path + "\\" + namePicture));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
