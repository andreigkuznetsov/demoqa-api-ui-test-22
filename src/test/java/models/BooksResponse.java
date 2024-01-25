package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BooksResponse {
    String isbn;
    String title;
    String subTitle;
    String author;
    @JsonProperty("publish_date")
    String publishDate;
    String publisher;
    int pages;
    String description;
    String website;
}
