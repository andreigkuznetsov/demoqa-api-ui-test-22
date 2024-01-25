package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddListOfBooksRequest {
    private String userId;
    private List<ListOfIsbns> collectionOfIsbns;
    private String isbn;
}
