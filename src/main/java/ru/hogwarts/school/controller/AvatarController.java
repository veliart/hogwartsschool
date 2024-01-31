package ru.hogwarts.school.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;

@RestController("student")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping (value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(
            @PathVariable Long id,
            @RequestParam MultipartFile avatarFile) throws IOException {
        if (avatarFile.getSize() > 1024 * 1024) { //Проверка на размер (до 1 Мб)
            return ResponseEntity.badRequest().body("Avatar must been less 1 Mb");
        }
        avatarService.uploadAvatar(id, avatarFile);
        return ResponseEntity.ok().build();
    }
}
