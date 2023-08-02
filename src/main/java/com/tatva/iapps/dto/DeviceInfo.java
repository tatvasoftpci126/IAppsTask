package com.tatva.iapps.dto;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * Model class for DeviceInfo tag.
 */

@Data
@XmlRootElement(name = "deviceInfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceInfo {

    @XmlElement
    private ScreenInfo screenInfo;

    @XmlElement
    private OSInfo osInfo;

    @XmlElement
    private AppInfo appInfo;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String id;
}
