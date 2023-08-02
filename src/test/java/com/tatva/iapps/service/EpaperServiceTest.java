package com.tatva.iapps.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
 * Unit tests for EpaperService.
 */

@ExtendWith(MockitoExtension.class)
public class EpaperServiceTest {
	
	MockedStatic<XMLUtility> mockedXMLUtility;
	MockedStatic<JAXBContext> mockedJAXBContext;
	MockedStatic<EpaperMapper> mockedEpaperMapper;

    @InjectMocks
    private EpaperServiceImpl epaperService;

    @Mock
    private EpaperRepository epaperRepository;
    
    @Mock
    private Page<Epaper> pageEpaper;
    
    @Mock
    private MultipartFile file;
    
    @Mock
    private Schema schema;
    
    @Mock
    private Validator validator;
    
    @Mock
    private JAXBContext context;
    
    @Mock
    private Unmarshaller unmashaller;
    
    
    @Mock
    private InputStream stream;
    
    @Mock
    private XMLUtility xmlUtility;
    
    @BeforeEach
    void beforeEach() {
    	mockedXMLUtility = Mockito.mockStatic(XMLUtility.class);
    	mockedJAXBContext = Mockito.mockStatic(JAXBContext.class);
    	mockedEpaperMapper = Mockito.mockStatic(EpaperMapper.class);
    }
    
    @AfterEach
    void afterEach() {
    	mockedXMLUtility.close();
    	mockedJAXBContext.close();
    	mockedEpaperMapper.close();
    }

    @Test
    void testGetAllEpaperListWithPaginationAndFilter() throws IAppsException {
        EpaperSearchRequest epaperSearchRequest = new EpaperSearchRequest();
        epaperSearchRequest.setPageNumber(0);
        epaperSearchRequest.setPageSize(1);
        epaperSearchRequest.setSearchText("abc");
        epaperSearchRequest.setStartDate(LocalDateTime.now().minusWeeks(1));
        epaperSearchRequest.setEndDate(LocalDateTime.now());
        
        EpaperResponse epaperResponse = EpaperResponse.builder().newspaperName("abc").uploadDateTime(LocalDateTime.now().minusDays(1)).build();
        
        EpaperSpecification epaperSpecification = new EpaperSpecification(epaperSearchRequest);
        
        Epaper epaper = Epaper.builder().newspaperName("abc").uploadDateTime(LocalDateTime.now().minusDays(1)).build();

        List<Epaper> epaperList = new ArrayList<>();
        epaperList.add(epaper);
        
        PageRequest pageRequest = PageRequest.of(epaperSearchRequest.getPageNumber(),
				epaperSearchRequest.getPageSize());
        Mockito.when(pageEpaper.getContent()).thenReturn(epaperList);
        Mockito.when(EpaperMapper.mapEntityToModel(epaper)).thenReturn(epaperResponse);
        Mockito.when(epaperRepository.findAll(epaperSpecification, pageRequest)).thenReturn(pageEpaper);
        List<EpaperResponse> result = epaperService.getAllEpapersWithPaginationAndFilter(epaperSearchRequest);
        assertEquals(result.size(), 1);
    }
    
    @Test
    public void testUploadEpaper() throws IOException, SAXException, JAXBException, InvalidXmlException, IAppsException {
    	
    	EpaperRequest epaperRequest = new EpaperRequest();
    	
    	 Epaper epaper = Epaper.builder().fileName("test").build();
    	// mock the static method of the Utils class
        Mockito.when(JAXBContext.newInstance(EpaperRequest.class)).thenReturn(context);
        Mockito.when(context.createUnmarshaller()).thenReturn(unmashaller);
        Mockito.when(XMLUtility.initializeSchema("xsd/EpaperRequest.xsd")).thenReturn(schema);
        Mockito.when(XMLUtility.initializeValidator(schema)).thenReturn(validator);
    	Mockito.when(file.getInputStream()).thenReturn(stream);
    	Mockito.when(file.getContentType()).thenReturn("text/xml");
    	Mockito.when(file.getOriginalFilename()).thenReturn("test");
    	Mockito.when(EpaperMapper.mapModelToEntity(epaperRequest, "test")).thenReturn(epaper);
    	doNothing().when(validator).validate(Mockito.any(StreamSource.class));
    	Mockito.when(unmashaller.unmarshal(stream)).thenReturn(epaperRequest);
    	Mockito.when(epaperRepository.save(epaper)).thenReturn(epaper);
    	Mockito.when(EpaperMapper.mapEntityToModel(epaper)).thenReturn(EpaperResponse.builder().fileName("test").build());
    	EpaperResponse result = epaperService.uploadEpaper(file);
    	assertEquals(result.getFileName(), "test");
    }
    
    @Test
    public void testUploadEpaperWhenFileTypeIsNotXML() throws IOException, SAXException, JAXBException, InvalidXmlException, IAppsException {
    	
    	Mockito.when(file.getContentType()).thenReturn("text/csv");
    	assertThrows(InvalidXmlException.class,()->epaperService.uploadEpaper(file));
    }
    
    @Test
    public void testUploadEpaperWhenXMLIsValidButCanNotUnmarshal() throws IOException, SAXException, JAXBException, InvalidXmlException, IAppsException {
    	
    	// mock the static method of the Utils class
        Mockito.when(JAXBContext.newInstance(EpaperRequest.class)).thenReturn(context);
        Mockito.when(context.createUnmarshaller()).thenReturn(unmashaller);
        Mockito.when(XMLUtility.initializeSchema("xsd/EpaperRequest.xsd")).thenReturn(schema);
        Mockito.when(XMLUtility.initializeValidator(schema)).thenReturn(validator);
    	Mockito.when(file.getInputStream()).thenReturn(stream);
    	Mockito.when(file.getContentType()).thenReturn("text/xml");
    	doNothing().when(validator).validate(Mockito.any(StreamSource.class));
    	Mockito.when(unmashaller.unmarshal(stream)).thenReturn(null);
    	assertThrows(InvalidXmlException.class,()->epaperService.uploadEpaper(file));
    }
    
    @Test
    public void testUploadEpaperWhenXMLIsNotValid() throws IOException, SAXException, JAXBException, InvalidXmlException, IAppsException {
    	
    	// mock the static method of the Utils class
        Mockito.when(JAXBContext.newInstance(EpaperRequest.class)).thenReturn(context);
        Mockito.when(XMLUtility.initializeSchema("xsd/EpaperRequest.xsd")).thenReturn(schema);
        Mockito.when(XMLUtility.initializeValidator(schema)).thenReturn(validator);
    	Mockito.when(file.getInputStream()).thenReturn(stream);
    	Mockito.when(file.getContentType()).thenReturn("text/xml");
    	doThrow(SAXException.class).when(validator).validate(Mockito.any(StreamSource.class));
    	assertThrows(InvalidXmlException.class,()->epaperService.uploadEpaper(file));
    }
   

}
