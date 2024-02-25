package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException;

    Avatar findStudentAvatar(Long id);

    List<Avatar> getPaginatedAvatars (int pageNumber, int pageSize);
}
