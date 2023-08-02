package com.tatva.iapps.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request dto for EpaperSearch
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EpaperSearchRequest {

	@Min(value = 0, message = "Page number should not be less then zero")
	private int pageNumber;
	@Min(value = 10, message = "Page size should not be less than 10")
	private int pageSize;
	private String sortBy;
	private int sortOrderBy;
	private String searchText;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;
}
