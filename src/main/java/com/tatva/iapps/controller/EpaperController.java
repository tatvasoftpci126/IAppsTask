package com.tatva.iapps.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tatva.iapps.dto.EpaperSearchRequest;
import com.tatva.iapps.exception.IAppsException;
import com.tatva.iapps.exception.InvalidXmlException;
import com.tatva.iapps.model.EpaperResponse;
import com.tatva.iapps.service.EpaperService;

/**
 * RestController for Epapar.
 */
@RestController
@RequestMapping("/api/epapers")
@Validated
public class EpaperController {

    @Autowired
    private EpaperService epaperService;

    @GetMapping
    public List<EpaperResponse> getAllEpapersWithPaginationAndFilter(
    		@Valid EpaperSearchRequest epaperSearchRequest
    		) throws IAppsException {
        return epaperService.getAllEpapersWithPaginationAndFilter(epaperSearchRequest);
    }
    
    @PostMapping("/upload")
    public EpaperResponse uploadEpaper(@RequestParam("xmlFile") MultipartFile file) throws InvalidXmlException, IAppsException {
        return epaperService.uploadEpaper(file);
    }
}
