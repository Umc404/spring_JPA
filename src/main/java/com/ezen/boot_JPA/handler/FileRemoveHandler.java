package com.ezen.boot_JPA.handler;

import com.ezen.boot_JPA.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Slf4j
public class FileRemoveHandler {
    private final String BASE_PATH = "D:\\umc\\_myProject\\_java\\_fileUpload\\";

    public boolean deleteFile(FileDTO fileDTO){
        File delFile = new File(BASE_PATH, fileDTO.getSaveDir());
        boolean isDel = false;

        // 파일명 생성
        String delete = fileDTO.getUuid()+"_"+fileDTO.getFileName();

        try{
            File deleteFile = new File(delFile, delete);    //원본 파일 경로
            log.info(">>> deleteFeil > {}", deleteFile);

            if(deleteFile.exists()){                        //원본 파일 삭제
                isDel = deleteFile.delete();
                log.info(">>> deleteFile Deleted? {}", isDel);
            }

            // 파일이 이미지 타입일 경우 썸네일 파일 삭제
            if(fileDTO.getFileType() > 0){                  //파일이 이미지일 경우
                String deleteThumb = fileDTO.getUuid()+"_th_"+fileDTO.getFileName();
                File deleteThumbFile = new File(delFile, deleteThumb);
                log.info(">>> deleteThumbFile > {}", deleteThumbFile);

                if(deleteThumbFile.exists()){               //썸네일 파일 삭제
                    boolean isThumbDel = deleteThumbFile.delete();
                    log.info(">>> deletThumbFile Delete? {}", isThumbDel);
                    isDel = isDel && isThumbDel;            //썸네일 파일 삭제 여부도 반영
                }
            }

        } catch (Exception e) {
            log.info("파일 삭제 중 오류 발생", e);
            throw new RuntimeException(e);
        }
        return isDel;
    }

}
