package models;

import lombok.Data;

import java.util.List;

@Data
public class AddListOfBooksResponse {
    private List<ListOfIsbns> books;
}
