package com.jux.familyspace.component;

import org.springframework.http.MediaType;

public class MediaTypeDetector {

    public static MediaType detectImageMediaType(byte[] image) {
        if (image == null || image.length < 4) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }

        if (image[0] == (byte) 0xFF && image[1] == (byte) 0xD8) {
            return MediaType.IMAGE_JPEG;
        } else if (image[0] == (byte) 0x89 && image[1] == (byte) 0x50 && image[2] == (byte) 0x4E && image[3] == (byte) 0x47) {
            return MediaType.IMAGE_PNG;
        } else if (image[0] == (byte) 0x52 && image[1] == (byte) 0x49 && image[2] == (byte) 0x46 && image[3] == (byte) 0x46) {
            String fileSignature = new String(image, 8, 4);
            if ("WEBP".equals(fileSignature)) {
                return MediaType.valueOf("image/webp");
            }
        }

        return MediaType.APPLICATION_OCTET_STREAM;
    }

}
