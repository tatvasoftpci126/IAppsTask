package com.tatva.iapps.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Model class for GetPages tag.
 */

@Data
@XmlRootElement(name = "getPages")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPages {

    @XmlAttribute
    private Long editionDefId;

    @XmlAttribute
    private Date publicationDate;
}
