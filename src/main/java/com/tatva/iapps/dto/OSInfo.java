package com.tatva.iapps.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for OSInfo tag.
 */

@Data
@XmlRootElement(name = "osInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class OSInfo {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private Double version;
}
