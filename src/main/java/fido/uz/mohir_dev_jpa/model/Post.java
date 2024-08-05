package fido.uz.mohir_dev_jpa.model;

import lombok.Data;

@Data
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String body;
}
