package kr.co.mannam.admin.webmap.service;


import kr.co.mannam.admin.webmap.dto.FileDTO;
import kr.co.mannam.domain.repository.webmap.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public String save(FileDTO fileDto) {
        return fileRepository.save(fileDto.toEntity()).getOriginFileName();
    }


    public String save2(FileDTO fileDto) {
        return fileRepository.save(fileDto.toEntity()).getFullPath();
    }
}
