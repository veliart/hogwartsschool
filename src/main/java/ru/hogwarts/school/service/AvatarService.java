package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException;
}
