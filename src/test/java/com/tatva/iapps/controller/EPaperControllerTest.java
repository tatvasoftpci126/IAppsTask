package com.tatva.iapps.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import com.tatva.iapps.exception.IAppsException;
import com.tatva.iapps.model.EpaperResponse;
import com.tatva.iapps.service.EpaperService;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EpaperController.class)
public class EPaperControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EpaperService epaperService;

	/**
	 * Success 200 response
	 * @throws Exception
	 */
	@Test
	 void epapers_200() throws Exception {
		when(epaperService.getAllEpapersWithPaginationAndFilter(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/api/epapers?pageNumber=1&pageSize=10")
				.contentType("application/json"))
			    .andExpect(status().isOk());
	  }

	/**
	 *  405 response
	 * Method not allowed
	 * @throws Exception
	 */
	@Test
	 void epapers_405() throws Exception {
		when(epaperService.getAllEpapersWithPaginationAndFilter(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
		mockMvc.perform(post("/api/epapers/")
				.contentType("application/json"))
			    .andExpect(status().is(405));
	  }

	/**
	 * 400 response
	 * @throws Exception
	 */
	@Test
	 void epapers_400() throws Exception {
		when(epaperService.getAllEpapersWithPaginationAndFilter(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/api/epapers/")
				.contentType("application/json"))
			    .andExpect(status().is(400));
	  }

	/**
	 *  400 response
	 * @throws Exception
	 */
	@Test
	 void epapers_400_1() throws Exception {
		when(epaperService.getAllEpapersWithPaginationAndFilter(ArgumentMatchers.any())).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/api/epapers?pageNumber=-1&pageSize=10")
				.contentType("application/json"))
			    .andExpect(status().is(400));
	  }

	/**
	 *  500 response
	 * @throws Exception
	 */
	@Test
	 void epapers_500() throws Exception {
		when(epaperService.getAllEpapersWithPaginationAndFilter(ArgumentMatchers.any())).thenThrow(IAppsException.class);
		mockMvc.perform(get("/api/epapers?pageNumber=1&pageSize=10")
				.contentType("application/json"))
			    .andExpect(status().is(500));
	  }

	/**
	 * Success 200 response 
	 * 
	 * @throws Exception
	 */
	@Test
	void upload_200() throws Exception {
		File file = ResourceUtils.getFile("classpath:xsd/testXML.xml");
		InputStream in = new FileInputStream(file);
		when(epaperService.uploadEpaper(ArgumentMatchers.any())).thenReturn(new EpaperResponse());
		MockMultipartFile sampleFile = new MockMultipartFile("xmlFile", "testXML.xml", "text/xml", in.readAllBytes());

		MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders
				.multipart("/api/epapers/upload");
		mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().is(200));
		in.close();
	}
	
	/**
	 *  400 response 
	 * 
	 * @throws Exception
	 */
	@Test
	void upload_400() throws Exception {
		File file = ResourceUtils.getFile("classpath:xsd/testXML.xml");
		InputStream in = new FileInputStream(file);
		when(epaperService.uploadEpaper(ArgumentMatchers.any())).thenReturn(new EpaperResponse());
		MockMultipartFile sampleFile = new MockMultipartFile("file", "testXML.xml", "text/xml", in.readAllBytes());

		MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders
				.multipart("/api/epapers/upload");
		mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().is(400));
		in.close();
	}
	
	/**
	 * Error 500 response 
	 * 
	 * @throws Exception
	 */
	@Test
	void upload_500() throws Exception {
		File file = ResourceUtils.getFile("classpath:xsd/testXML.xml");
		InputStream in = new FileInputStream(file);
		when(epaperService.uploadEpaper(ArgumentMatchers.any())).thenThrow(new IAppsException());
		MockMultipartFile sampleFile = new MockMultipartFile("xmlFile", "testXML.xml", "text/xml", in.readAllBytes());

		MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders
				.multipart("/api/epapers/upload");
		mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().is(500));
		in.close();
	}

}