package projectppin.ppin.Service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectppin.ppin.DTO.DocumentDTO;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class DocumentServiceTest {
    private static final Logger log = LoggerFactory.getLogger(DocumentServiceTest.class);
    @Autowired
    private DocumentService documentService;

    @Test
    @DisplayName("문서 등록 테스트")
    public void test1() {
        for (int i = 1; i <= 3; i++) {
            String userId = "admin";
            DocumentDTO documentDTO = DocumentDTO.builder()
                    .title("테스트0" + i)
                    .writer(userId)
                    .content("문서 등록 테스트")
                    .build();
            documentDTO.setUploadFileNames(
                    List.of(UUID.randomUUID() + "_" + "TEST.pdf",
                            UUID.randomUUID() + "_" + "Test01.png"));
            documentService.register(documentDTO, userId);
        }
    }

    @Test
    @DisplayName("문서 삭제 테스트")
    public void test2() {
        String userId = "admin";
        Long dno = 1L;
        documentService.remove(dno, userId);
    }

    @Test
    @DisplayName("문서 조회 테스트")
    public void test3() {
        List<DocumentDTO> result = documentService.getList();
        result.forEach((documentDTO -> log.info(documentDTO.toString())));
    }

    @Test
    @DisplayName("문서 조회 테스트 2")
    public void test4() {
        for (int i = 1; i <= 3; i++) {
            DocumentDTO documentDTO = documentService.get((long) i);
            log.info(documentDTO.toString());
        }
    }

    @Test
    @DisplayName("문서 수정 테스트")
    public void test5() {
        Long dno = 2L;
        String userId = "admin";
        DocumentDTO documentDTO = DocumentDTO.builder()
                .title("수정된 제목")
                .writer(userId)
                .content("문서 수정 테스트")
                .build();
        documentDTO.setUploadFileNames(
                List.of(UUID.randomUUID() + "_" + "TEST.pdf"));
        documentService.modify(dno, documentDTO, userId);
    }
}