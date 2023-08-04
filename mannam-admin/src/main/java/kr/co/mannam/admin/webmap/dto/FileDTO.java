package kr.co.mannam.admin.webmap.dto;

import kr.co.mannam.domain.entity.webmap.File;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FileDTO {
    private Long id;
    private String originFileName;
    private String fullPath;

    public File toEntity() {
        return File.builder()
                .id(this.id)
                .originFileName(this.originFileName)
                .fullPath(this.fullPath)
                .build();
    }

    @Builder
    public FileDTO(Long id, String originFileName, String fullPath) {
        this.id = id;
        this.originFileName = originFileName;
        this.fullPath = fullPath;
    }
}


