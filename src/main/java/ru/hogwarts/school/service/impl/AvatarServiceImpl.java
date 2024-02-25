package ru.hogwarts.school.service.impl;


import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


import static java.nio.file.StandardOpenOption.CREATE_NEW;


@Service
@Transactional // Для работы вместе с аннотацией @Lob
public class AvatarServiceImpl implements AvatarService {

    private static final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void uploadAvatar(Long id, MultipartFile avatarFile) throws IOException {
        logger.info("Log info: Method uploadAvatar is invoke.");
        Student student = studentService.getStudentInfo(id);
        Path pathFile = Path.of(avatarsDir, student + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(pathFile.getParent());
        Files.deleteIfExists(pathFile);
        try (InputStream inputStream = avatarFile.getInputStream();
             OutputStream outputStream = Files.newOutputStream(pathFile, CREATE_NEW);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 1024);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream, 1024);
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }

        Avatar avatar = findStudentAvatar(id);
        avatar.setStudent(student);
        avatar.setFilePath(pathFile.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    private String getExtensions(String fileName) {
        logger.info("Log info: Method getExtensions is invoke.");
        return fileName.substring(fileName.lastIndexOf(".") +1 );
    }
    public Avatar findStudentAvatar(Long id)  {
        logger.info("Log info: Method findStudentAvatar is invoke.");
        return avatarRepository.findByStudent_id(id).orElse(new Avatar());
    }

    @Override
    public List<Avatar> getPaginatedAvatars(int pageNumber, int pageSize) {
        logger.info("Log info: Method getPaginatedAvatars is invoke.");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }

}
