package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

public class Parsing {
    String downloadDir = System.getProperty("user.dir") + "\\src\\images";
    ArrayList <String> names = new ArrayList<>();

    public void downloadImages() throws IOException {
        Document doc = Jsoup.connect("https://www.winxclub.com").get();
        Elements images = doc.getElementsByTag("img");
        for (Element image : images) {
            String strImageURL = image.attr("abs:src");
            downloadImage(strImageURL);
        }
    }

    public void downloadImage(String strImageURL) {
        String strImageName_n =
                strImageURL.substring(strImageURL.lastIndexOf("/") + 1);
        String[] name = strImageName_n.split("\\?");
        String strImageName = name[0];
        if ((strImageURL!=null)&&(strImageName!="")&&(!names.contains(strImageName))&&(strImageName.charAt(strImageName.length() - 1)=='g')) {
            System.out.println("Saving: " + strImageName + ", from: " + strImageURL);
            try {
                URL urlImage = new URL(strImageURL);
                InputStream in = urlImage.openStream();

                byte[] buffer = new byte[4194304];
                int n = -1;

                OutputStream os =
                        new FileOutputStream(downloadDir + "/" + strImageName);

                //write bytes to the output stream
                while ((n = in.read(buffer)) != -1) {
                    os.write(buffer, 0, n);
                }

                //close the stream
                os.close();

                System.out.println("Image saved");

            } catch (IOException e) {
                e.printStackTrace();
            }
            names.add(strImageName);
        }
    }

}
