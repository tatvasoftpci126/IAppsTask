package com.tatva.iapps.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.tatva.iapps.dto.EpaperRequest;
import com.tatva.iapps.dto.EpaperSearchRequest;
import com.tatva.iapps.entity.Epaper;
import com.tatva.iapps.entity.EpaperSpecification;
import com.tatva.iapps.exception.IAppsException;
import com.tatva.iapps.exception.InvalidXmlException;
import com.tatva.iapps.mapper.EpaperMapper;
import com.tatva.iapps.model.EpaperResponse;
import com.tatva.iapps.repository.EpaperRepository;
import com.tatva.iapps.utility.XMLUtility;

/**
 * Service impl class for EpaperService.
 * 
 * @author Mehul Trivedi
 */
@Service
public class EpaperServiceImpl implements EpaperService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private EpaperRepository epaperRepository;

	@Override
	public List<EpaperResponse> getAllEpapersWithPaginationAndFilter(final EpaperSearchRequest epaperSearchRequest) throws IAppsException {
		try {
			
			// Specification class for Epaper
			EpaperSpecification epaperSpecification = new EpaperSpecification(epaperSearchRequest);
			
			// Create a page request for pagination
			PageRequest pageRequest = PageRequest.of(epaperSearchRequest.getPageNumber(),
					epaperSearchRequest.getPageSize());
			List<Epaper> epaperList = epaperRepository.findAll(epaperSpecification, pageRequest).getContent();
			return epaperList.stream().map(EpaperMapper::mapEntityToModel).collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
			throw new IAppsException(e.getMessage());
		}
	}

	@Override
	public EpaperResponse uploadEpaper(MultipartFile file) throws InvalidXmlException,IAppsException {
		try {

			//Check file type is XML or not.
			boolean isXmlTypeFile = validateIsXmlFile(file);
			
			if (!isXmlTypeFile) {
				throw new InvalidXmlException("Error while building epaper request from XML");
			}
			
			Schema schema = XMLUtility.initializeSchema("xsd/EpaperRequest.xsd");

			// Validate XML using XSD
			Validator validator = XMLUtility.initializeValidator(schema);
			validator.validate(new StreamSource(file.getInputStream()));
			
			// Build Epaper Request model from XML file
			EpaperRequest epaperRequest = buildEpaperRequestFromXML(schema, file);

			if (Objects.isNull(epaperRequest)) {
				throw new InvalidXmlException("Error while building epaper request from XML");
			}
			// Map model to entity
			Epaper epaper = EpaperMapper.mapModelToEntity(epaperRequest, file.getOriginalFilename());

			epaper = epaperRepository.save(epaper);
			return EpaperMapper.mapEntityToModel(epaper);

		} catch (SAXException ex) {
			throw new InvalidXmlException("Error while building epaper request from XML");
		} catch (IOException e) {
			throw new IAppsException("IO Exception occured");
		}
	}

	/**
	 * Method to build the epaper request from xml file.
	 * @param schema
	 * @param file
	 * @return EpaperRequest
	 */
	private EpaperRequest buildEpaperRequestFromXML(Schema schema, MultipartFile file) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(EpaperRequest.class);
			Unmarshaller un = context.createUnmarshaller();
			un.setSchema(schema);
			return (EpaperRequest) un.unmarshal(file.getInputStream());
		} catch (JAXBException e) {
			LOGGER.error("JAXBException Error : " + e.getMessage());
			return null;
		} catch (IOException e) {
			LOGGER.error("IOException Error : " + e.getMessage());
			return null;
		}
	}

	/**
	 * Method used to validate xml file.
	 * 
	 * @param file
	 * @return boolean
	 */
	private boolean validateIsXmlFile(MultipartFile file) {
		if (file.getContentType().contains("text/xml") || file.getContentType().contains("application/xml")) {
			return true;
		}
		return false;
	}
}
