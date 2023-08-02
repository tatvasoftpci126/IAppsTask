package com.tatva.iapps.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for AppInfo tag.
 */

@Data
@XmlRootElement(name = "appInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppInfo {

    @XmlElement
    private String newspaperName;

    @XmlElement
    private Double version;
}
