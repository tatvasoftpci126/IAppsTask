package com.tatva.iapps.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tatva.iapps.dto.EpaperSearchRequest;
import com.tatva.iapps.exception.IAppsException;
import com.tatva.iapps.exception.InvalidXmlException;
import com.tatva.iapps.model.EpaperResponse;

/**
 * Service class for Eapper.
 */
public interface EpaperService {

	/**
	 * Method to get all epapers with filter and pagination.
	 * 
	 * @param epaperSearchRequest
	 * @return List<Epaper> - List of Epapers
	 */
	List<EpaperResponse> getAllEpapersWithPaginationAndFilter(final EpaperSearchRequest epaperSearchRequest) throws IAppsException;

	/**
	 * Method to upload epaper using XML as input and storing into DB.
	 * 
	 * @param file
	 * @return
	 */
	EpaperResponse uploadEpaper(MultipartFile file) throws InvalidXmlException, IAppsException;
}
