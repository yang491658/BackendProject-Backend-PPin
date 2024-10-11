package projectppin.ppin.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectppin.ppin.DTO.DocumentDTO;
import projectppin.ppin.Service.DocumentService;
import projectppin.ppin.util.CustomFileUtil;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentController {
    private final CustomFileUtil fileUtil;
    private final DocumentService documentService;

    @PostMapping("/")
    public Map<String, Long> register(DocumentDTO documentDTO, @RequestParam String userId) {
        List<MultipartFile> files = documentDTO.getFiles();
        List<String> uploadFileNames = fileUtil.saveFile(files);
        documentDTO.setUploadFileNames(uploadFileNames);
        Long dno = documentService.register(documentDTO, userId);
        return Map.of("result", dno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFile(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
    }

    @GetMapping("/list")
    public List<DocumentDTO> list() {
        return documentService.getList();
    }

    @GetMapping("/{dno}")
    public DocumentDTO get(@PathVariable(name = "dno") Long dno) {
        return documentService.get(dno);
    }

    @PutMapping("/{dno}")
    public Map<String, String> modify(@PathVariable(name = "dno") Long dno,
                                      DocumentDTO documentDTO,
                                      @RequestParam String userId) {
        documentDTO.setDno(dno);
        // 기존 파일들
        DocumentDTO oldDocumentDTO = documentService.get(dno);
        List<String> oldFileNames = oldDocumentDTO.getUploadFileNames();
        // 새로운 파일들 및 이름
        List<MultipartFile> newFiles = documentDTO.getFiles();
        List<String> newUploadFileNames = fileUtil.saveFile(newFiles);
        // 계속 유지할 파일들
        List<String> uploadFileNames = documentDTO.getUploadFileNames();
        // 유지되는 파일 + 새로운 파일 이름들
        if (newUploadFileNames != null && !newUploadFileNames.isEmpty()) {
            uploadFileNames.addAll(newUploadFileNames);
        }
        documentService.modify(dno, documentDTO, userId);

        // 기존 파일들 중 삭제 처리
        if (oldFileNames != null && !oldFileNames.isEmpty()) {
            log.info(oldFileNames.toString());
            List<String> removeFiles = oldFileNames.stream()
                    .filter(fileName -> !uploadFileNames.contains(fileName))
                    .collect(Collectors.toList());
            log.info(oldFileNames.toString());
            // 실제 파일 삭제
            fileUtil.deleteFiles(removeFiles);
        }
        return Map.of("result", "SUCCESS");
    }

    @DeleteMapping("/{dno}")
    public Map<String, String> remove(@PathVariable(name = "dno") Long dno, @RequestParam String userId) {
        // 삭제 파일 이름
        List<String> oldFileNames = documentService.get(dno).getUploadFileNames();
        documentService.remove(dno, userId);
        fileUtil.deleteFiles(oldFileNames);
        return Map.of("result", "SUCCESS");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Path path = Paths.get(System.getProperty("user.dir"), ".", "upload", fileName).normalize();
        Resource resource = new FileSystemResource(path);
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        String[] parts = resource.getFilename().split("_");
        String downloadFileName = parts[parts.length - 1];
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadFileName + "\"")
                .body(resource);
    }
}
