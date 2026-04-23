package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.CreateDocumentDTO;
import Federation.Agricole.API.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/collectivities")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/{id}/documents")
    public ResponseEntity<String> uploadDocument(@PathVariable String id, @RequestBody CreateDocumentDTO dto) {
        try {
            documentService.registerDocument(id, dto.getType(), dto.getFilePath());
            return ResponseEntity.status(HttpStatus.CREATED).body("Document metadata registered successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }
}
