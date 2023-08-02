package com.tatva.iapps.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for epaper request tag.
 */

@Data
@XmlRootElement(name = "epaperRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class EpaperRequest {

    @XmlElement
    private DeviceInfo deviceInfo;

    @XmlElement
    private GetPages getPages;
}
