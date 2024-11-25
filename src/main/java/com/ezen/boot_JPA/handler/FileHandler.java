package com.ezen.boot_JPA.handler;

import com.ezen.boot_JPA.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    private final String UP_DIR = "D:\\umc\\_myProject\\_java\\_fileUpload\\";

    public List<FileDTO> uploadFiles(MultipartFile[] files) {
        List<FileDTO> flist = new ArrayList<>();

        LocalDate date = LocalDate.now();
        // 2024-11-15 => 2024\\11\\15
        String today = date.toString().replace("-", File.separator);

        // D:umc\_myProject\_java\_fileUpload\2024\11\15
        File folders = new File(UP_DIR, today);

        if(!folders.exists()) {
            folders.mkdirs();
        }

        for(MultipartFile file : files) {
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSaveDir(today);
            fileDTO.setFileSize(file.getSize());

            String originalFileName = file.getOriginalFilename();   // getOriginalFilename에 경로가 있을 수 있음
            String onlyFileName = originalFileName
                    .substring(originalFileName.lastIndexOf(File.separator) + 1);
            fileDTO.setFileName(onlyFileName);

            UUID uuid = UUID.randomUUID();
//            String uuidStr = uuid.toString();
            fileDTO.setUuid(uuid.toString());

            String fullFileName = uuid.toString()+"_"+onlyFileName;
            String thumbFileName = uuid.toString()+"_th_"+onlyFileName;

            File storeFile = new File(folders, fullFileName);

            try {
                file.transferTo(storeFile);
                if(isImageFile(storeFile)){
                    fileDTO.setFileType(1);
                    File thumbnail = new File(folders, thumbFileName);
                    Thumbnails.of(storeFile).size(100,100).toFile(thumbnail);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            flist.add(fileDTO);
        }
        return flist;
    }

    private boolean isImageFile(File file) throws IOException {
        String mimeType = new Tika().detect(file);
        return mimeType.startsWith("image");
    }
}
