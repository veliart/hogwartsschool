package ru.hogwarts.school.dto;

import java.util.Objects;

public class AvatarDTO {

    private Long id;
    private String mediaType;
    private long fileSize;
    private long studentId;

    public AvatarDTO() {
    }

    public AvatarDTO(Long id, String mediaType, long fileSize, long studentId) {
        this.id = id;
        this.mediaType = mediaType;
        this.fileSize = fileSize;
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDTO avatarDTO = (AvatarDTO) o;
        return fileSize == avatarDTO.fileSize && studentId == avatarDTO.studentId && Objects.equals(id, avatarDTO.id) && Objects.equals(mediaType, avatarDTO.mediaType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mediaType, fileSize, studentId);
    }

    @Override
    public String toString() {
        return "AvatarDTO{" +
                "id=" + id +
                ", filePath='" + mediaType + '\'' +
                ", fileSize=" + fileSize +
                ", studentId=" + studentId +
                '}';
    }
}
