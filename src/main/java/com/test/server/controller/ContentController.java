package com.test.server.controller;



import com.test.server.dto.ContentDTO;
import com.test.server.exception.MyServerException;
import com.test.server.service.ContentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/private/content")
public class ContentController {

    @Autowired
    private ContentService contentService;


    @ApiOperation(value = "Метод получения контента")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "Authorization, format - 'Bearer token'",
                    required = true,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = "Bearer ")})
    @GetMapping(path = "page")
    public ResponseEntity<Page<ContentDTO>> getContentPage(@RequestParam(required = false, value = "pageNum", defaultValue = "0") Integer pageNum,
                                                           @RequestParam(required = false, value = "pageSize", defaultValue = "1") Integer pageSize) {
        try {
            Page<ContentDTO> result = contentService.getContent(pageNum, pageSize);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (MyServerException e) {
            throw new ResponseStatusException(e.getHttpStatus(), e.getMessage());
        }
    }

}
