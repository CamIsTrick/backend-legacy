package springboot.focusing.domain.enums;

import org.springframework.web.multipart.MultipartFile;
import springboot.focusing.exception.ErrorCode;
import springboot.focusing.exception.auth.InvalidImageException;
import springboot.focusing.utils.FileUtils;

import java.io.IOException;

public class ImageFile {
    private MultipartFile value;

    public ImageFile(MultipartFile value) {
        if (value.isEmpty()) {
            throw new InvalidImageException(ErrorCode.NULL_POINT);
        } else {
            validatePngImage(value);
//            validateImageSize(value);
            this.value = value;
        }
    }

    private void validatePngImage(MultipartFile value) {
        try {
            boolean isValid = FileUtils.validImgFile(value.getInputStream());
            if (!isValid) {
                throw new InvalidImageException(ErrorCode.INVALID_IMAGE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidImageException(ErrorCode.INVALID_IMAGE);
        }
    }

//    private void validateImageSize(MultipartFile value) {
//        if (value.getSize() > /*크기 제한*/) {
//            throw new InvalidImageException(ErrorCode.INVALID_IMAGE);
//        }
//    }

    public MultipartFile getValue() {
        return value;
    }
}
