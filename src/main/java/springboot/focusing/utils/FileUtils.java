package springboot.focusing.utils;

import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*
파일 형식 검증 유틸
 */
public class FileUtils {

    private static final Tika tika = new Tika();

    public static boolean validImgFile(InputStream inputStream) {
        try {
            List<String> notValidTypeList = List.of("image/png");
            String mimeType = tika.detect(inputStream);
            return notValidTypeList.stream().anyMatch(notValidType -> notValidType.equalsIgnoreCase(mimeType));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}