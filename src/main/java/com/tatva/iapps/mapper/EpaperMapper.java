package com.tatva.iapps.mapper;

import java.time.LocalDateTime;

import com.tatva.iapps.dto.EpaperRequest;
import com.tatva.iapps.entity.Epaper;
import com.tatva.iapps.model.EpaperResponse;

public class EpaperMapper {

	public static Epaper mapModelToEntity(final EpaperRequest epaperRequest, final String fileName) {
		Epaper epaper = Epaper.builder().fileName(fileName)
				.newspaperName(epaperRequest.getDeviceInfo().getAppInfo().getNewspaperName())
				.height(epaperRequest.getDeviceInfo().getScreenInfo().getHeight())
				.width(epaperRequest.getDeviceInfo().getScreenInfo().getWidth())
				.dpi(epaperRequest.getDeviceInfo().getScreenInfo().getDpi()).uploadDateTime(LocalDateTime.now())
				.build();
		return epaper;
	}

	public static EpaperResponse mapEntityToModel(final Epaper epaper) {
		return EpaperResponse.builder().newspaperName(epaper.getNewspaperName()).height(epaper.getHeight())
				.dpi(epaper.getDpi()).fileName(epaper.getFileName()).uploadDateTime(epaper.getUploadDateTime())
				.width(epaper.getWidth()).build();
	}
}
