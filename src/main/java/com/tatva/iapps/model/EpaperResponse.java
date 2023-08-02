package com.tatva.iapps.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for EpaperResponse
 * 
 * @author Mehul Trivedi
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EpaperResponse {

	private String newspaperName;
	private Long height;
	private Long width;
	private Long dpi;
	private LocalDateTime uploadDateTime;
	private String fileName;
	private String responseMessage;

}
