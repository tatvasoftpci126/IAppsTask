package com.tatva.iapps.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for ScreenInfo tag.
 */

@Data
@XmlRootElement(name = "screenInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScreenInfo {

    @XmlAttribute
    private Long width;

    @XmlAttribute
    private Long height;

    @XmlAttribute
    private Long dpi;

}
