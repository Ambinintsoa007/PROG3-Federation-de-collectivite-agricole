package Federation.Agricole.API.service;

import Federation.Agricole.API.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void registerDocument(String collectivityId, String type, String path) {

        if (!type.equals("STATUTES") && !type.equals("REPORT")) {
            throw new RuntimeException("Validation error: Document type must be 'STATUTES' or 'REPORT'.");
        }


        String documentId = "DOC-" + UUID.randomUUID().toString().substring(0, 8);

        documentRepository.save(documentId, collectivityId, type.toUpperCase(), path);
    }
}
