package com.tatva.iapps.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tatva.iapps.dto.EpaperSearchRequest;

import lombok.Data;

@Data

public class EpaperSpecification implements Specification<Epaper> {

	private int pageNumber;
    private int pageSize;
    private int sortOrderBy;
    private String searchText;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public EpaperSpecification(EpaperSearchRequest epaperSearchRequest) {
		this.pageNumber = epaperSearchRequest.getPageNumber();
		this.pageSize = epaperSearchRequest.getPageSize();
		this.sortOrderBy = epaperSearchRequest.getSortOrderBy();
		this.searchText = epaperSearchRequest.getSearchText();
		this.startDate = epaperSearchRequest.getStartDate();
		this.endDate = epaperSearchRequest.getEndDate();
	}
    
	@Override
	public Predicate toPredicate(Root<Epaper> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		List<Predicate> predicateList = new ArrayList<>();
		if (Objects.nonNull(searchText) && (!searchText.equalsIgnoreCase("")))
			predicateList.add(criteriaBuilder.equal(root.get("newspaperName"), searchText));
		if (Objects.nonNull(startDate))
			predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("uploadDateTime"), startDate));
		if (Objects.nonNull(endDate))
			predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("uploadDateTime"), endDate));
		if (sortOrderBy != 1)
			query.orderBy(criteriaBuilder.desc(root.get("uploadDateTime")));
		else
			query.orderBy(criteriaBuilder.asc(root.get("uploadDateTime")));
		
		Predicate[] predicates = predicateList.toArray(new Predicate[predicateList.size()]);
 		return criteriaBuilder.and(predicates);
	}

	

}
