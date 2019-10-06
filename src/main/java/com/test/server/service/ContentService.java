package com.test.server.service;

import com.test.server.dto.*;
import com.test.server.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public Page<ContentDTO> getContent(Integer pageNum, Integer pageSize) {
        return contentRepository.selectContent(pageNum, pageSize).map(content -> {
            ContentDTO contentDTO = new ContentDTO();
            contentDTO.setId(content.getId());
            contentDTO.setName(content.getName());
            return contentDTO;
        });

    }
}
