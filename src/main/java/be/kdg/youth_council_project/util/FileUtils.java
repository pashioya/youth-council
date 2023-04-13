package be.kdg.youth_council_project.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileUtils {
    public static boolean checkImageFileList(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            checkImageFile(file);
        }
        return true;
    }

    public static boolean checkImageFile(MultipartFile file){
        return file.getContentType().startsWith("image/");
    }
}
