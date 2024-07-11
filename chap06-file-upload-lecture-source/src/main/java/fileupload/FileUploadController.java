package fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j  //로그
public class FileUploadController {

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/single-file")
    public String singleFileUpload(@RequestParam MultipartFile singleFile,
                                   @RequestParam String singleFileDescription,
                                   Model model) throws IOException {
        log.info("singleFileUpload : ====>{}", singleFile);
        log.info("singleFileDescription : ====>{}", singleFileDescription);

        //파일 저장 공간 지정

        //인텔리제이 root폴더지정
        Resource resource = resourceLoader.getResource("classpath:static/img/single");

        //classpath: :클래스의 resources까지의 경로로 가져다 준다.
        String filePath = null;

        //폴더가 있을떄, 없을떄에 따라 저장경로를 지정해줘야한다
        if(!resource.exists()){
            String root = "src/main/resources/static/img/single";
            File file = new File(root);
            file.mkdir();

            filePath = file.getAbsolutePath(); //절대경로
            log.info("폴더생성성공, 경로 :{}", filePath);
        }else{
            filePath = resourceLoader.getResource("classpath:static/img/single").getFile().getAbsolutePath();
            log.info("폴더존재함, 경로:{}", filePath);
        }

        //기존 파일명
        String originFileName = singleFile.getOriginalFilename();
        log.info("originFileName :{}", originFileName); //이미지.png

        //확장자
        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        log.info("ext : {}",ext); //exe : .png

        //저장할파일명
        String savedName = UUID.randomUUID().toString().replace("-","")+ext;
        log.info("savedName :{}", savedName); //savedName ; 78418a94c99d4ceebb38c9c26d476baf

        //파일을 저장하는것에 있어서는 try catch처리가 필요하다
        try {
            //파일저장
            //업로드된 파일을 서버의 특정경로에 savedName으로 저장
            singleFile.transferTo(new File(filePath + "/" + savedName));

            model.addAttribute("message", "파일업로드 성공");
            model.addAttribute("img", "static/img/single" + savedName);
        }catch (Exception e){
            e.printStackTrace();
            new File(filePath + "/" + savedName).delete();
            model.addAttribute("message","파일업로드 실패");
        }
        return "result";
    }

    @PostMapping("/multi-file")
    public String multiFileUpload(@RequestParam List<MultipartFile> multiFiles,
                                  @RequestParam String multiFileDescription, Model model) throws IOException {
        log.info("multifiles :{}", multiFiles);
        log.info("multifileDescription :{}", multiFileDescription);

        //파일 저장할 경로설정
        Resource resource = resourceLoader.getResource("classpath:static/img/multi");

        String filePath = null;

        if(!resource.exists()){
            String root = "src/main/resources/static/img/multi";
            File file = new File(root);
            file.mkdirs();

            filePath = file.getAbsolutePath();
        }else{
            filePath = resourceLoader.getResource("classpath:static/img/multi").getFile().getAbsolutePath();
        }
        log.info("💛multi :{}",filePath);

        List<FileDTO> files = new ArrayList<>();
        List<String> saveFiles = new ArrayList<>();

        try{

            for(MultipartFile file : multiFiles){
                String originFileName = file.getOriginalFilename();
                String ext = originFileName.substring(originFileName.lastIndexOf("."));
                String savedName = UUID.randomUUID().toString().replace("-", "")+ext;

                //파일에 대한 정보를 추출
                files.add(new FileDTO(originFileName, savedName, filePath, multiFileDescription));

                //파일을저장
                file.transferTo(new File(filePath+"/"+savedName));
                saveFiles.add("static/img/multi/" + savedName);
            }
            model.addAttribute("message", "파일업로드 성공");
            model.addAttribute("imgs", saveFiles);

        }catch (Exception e){
            e.printStackTrace();

            //실패시 이전에 저장된 파일삭제
            for(FileDTO file : files){
                new File(filePath + "/" + file.getSaveName()).delete();
            }
            model.addAttribute("message", "파일업로드 실패");
        }
        return "result";
    }

}
