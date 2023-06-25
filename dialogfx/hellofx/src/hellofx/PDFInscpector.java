package hellofx;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class PDFInscpector {
    float numberOfJavascriptObject;

    public PDFInscpector(String file) {
        try (PDDocument document = PDDocument.load(new File(file))) {
            int javascriptObjectsCount = 0;
            String fileName = new File(file).getName();
            PrintWriter writer = new PrintWriter("log.txt", "UTF-8");

            for (COSObject cosObject : document.getDocument().getObjects()) {
                COSBase base = cosObject.getObject();

                if (base instanceof COSDictionary) {
                    COSDictionary dictionary = (COSDictionary) base;
                    if (dictionary.containsKey(COSName.JS)) {
                        javascriptObjectsCount++;
                        System.out.println("JavaScript object found: " + dictionary.getDictionaryObject(COSName.JS));
                        writer.println("filename : " + fileName);
                        writer.println("Number of JavaScript objects : " + javascriptObjectsCount);
                        writer.println("JavaScript objects found : ");
                        writer.println(dictionary.getDictionaryObject(COSName.JS));
                        writer.close();
                    }
                }
            }

            System.out.println("Number of JavaScript objects: " + javascriptObjectsCount);
            numberOfJavascriptObject = javascriptObjectsCount;

            if (numberOfJavascriptObject < 1) {
                writer.println("filename : " + fileName);
                writer.println("Number of JavaScript objects: " + javascriptObjectsCount);
                writer.println("NO JavaScript object found: ");
                writer.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
