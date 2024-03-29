package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController("student")
public class AvatarController {
    private final AvatarService avatarService;
    private final AvatarMapper avatarMapper;

    public AvatarController(AvatarService avatarService, AvatarMapper avatarMapper) {
        this.avatarService = avatarService;
        this.avatarMapper = avatarMapper;
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
    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findStudentAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findStudentAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream inputStream = Files.newInputStream(path);
             OutputStream outputStream = response.getOutputStream();
             )
        {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            inputStream.transferTo(outputStream);
        }

    }
    @GetMapping("avatars")
    public List<AvatarDTO> getPaginatedAvatars(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        return avatarService.getPaginatedAvatars(pageNumber, pageSize)
                .stream()
                .map(avatarMapper::matToDTO)
                .collect(Collectors.toList());
    }
}
