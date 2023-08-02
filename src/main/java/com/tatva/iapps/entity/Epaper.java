package com.tatva.iapps.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

/**
 * Entity class for EpaperRequest.
 */

@Entity
@Table(name = "epaper")
@Data
@Builder
public class Epaper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String newspaperName;

    @Column
    private Long height;

    @Column
    private Long width;

    @Column
    private Long dpi;

    @Column
    private LocalDateTime uploadDateTime;

    @Column
    private String fileName;
}
