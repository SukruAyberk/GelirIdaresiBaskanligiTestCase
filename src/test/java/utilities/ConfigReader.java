package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    public static Properties properties;

    static {
        String filePath = "configuration.properties";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            // fis dosya yolunu tanımladığımız configuration.properties dosyasını okudu
            properties = new Properties();
            properties.load(fis); // fis'in okuduğu bilgileri properties'e yükledi
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        /*
            test methodundan yolladığımız string ket değerini alıp
            Properties class'ından getProperty() methodunu kullanarak
            bu key'e value'u bize getirdi
         */

        return properties.getProperty(key);
    }

}
