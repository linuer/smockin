package com.smockin.admin.controller;

import com.smockin.admin.dto.FtpMockDTO;
import com.smockin.admin.dto.response.FtpMockResponseDTO;
import com.smockin.admin.dto.response.SimpleMessageResponseDTO;
import com.smockin.admin.exception.RecordNotFoundException;
import com.smockin.admin.exception.ValidationException;
import com.smockin.admin.service.FtpMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgallina.
 */
@Controller
public class FtpController {


    @Autowired
    private FtpMockService ftpMockService;

    @RequestMapping(path="/ftpmock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<SimpleMessageResponseDTO<String>> create(@RequestBody final FtpMockDTO dto) {
        return new ResponseEntity<SimpleMessageResponseDTO<String>>(new SimpleMessageResponseDTO<String>(ftpMockService.createEndpoint(dto)), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/ftpmock/{extId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> update(@PathVariable("extId") final String extId, @RequestBody final FtpMockDTO dto) throws RecordNotFoundException {
        ftpMockService.updateEndpoint(extId, dto);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/ftpmock/{extId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Void> delete(@PathVariable("extId") final String extId) throws RecordNotFoundException, IOException {
        ftpMockService.deleteEndpoint(extId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path="/ftpmock", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<FtpMockResponseDTO>> get() {
        return ResponseEntity.ok(ftpMockService.loadAll());
    }

    @RequestMapping(path="/ftpmock/{extId}/file/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public @ResponseBody ResponseEntity<Void> uploadFile(@PathVariable("extId") final String extId, @RequestParam("file") MultipartFile file)
            throws RecordNotFoundException, ValidationException, IOException {
        ftpMockService.uploadFile(extId, file);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(path="/ftpmock/{extId}/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<List<String>> loadUploadFiles(@PathVariable("extId") final String extId)
            throws RecordNotFoundException, IOException {
        return ResponseEntity.ok(ftpMockService.loadUploadFiles(extId));
    }

    @RequestMapping(path="/ftpmock/{extId}/file", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Void> deleteUploadedFile(@PathVariable("extId") final String extId, @RequestParam("uri") final String uri)
            throws RecordNotFoundException, ValidationException, IOException {
        ftpMockService.deleteUploadedFile(extId, uri);
        return ResponseEntity.noContent().build();
    }

}