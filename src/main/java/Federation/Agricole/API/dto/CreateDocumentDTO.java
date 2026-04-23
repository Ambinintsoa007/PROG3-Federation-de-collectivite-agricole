package Federation.Agricole.API.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class CreateDocumentDTO {
    private String type;
    private String filePath;
}