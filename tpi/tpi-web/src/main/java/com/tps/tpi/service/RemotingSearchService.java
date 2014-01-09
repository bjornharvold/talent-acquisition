package com.tps.tpi.service;

import com.tps.tpi.model.objects.dto.SearchDto;
import com.tps.tpi.model.objects.lite.PersonLite;
import com.tps.tpi.exception.DomainException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Dec 2, 2009
 * Time: 3:12:52 AM
 * Responsibility:
 */
public interface RemotingSearchService {

    @Transactional
    SearchDto search(SearchDto dto, Integer index, Integer maxResults) throws DomainException;

    @Transactional
    Integer searchCount(SearchDto dto) throws DomainException;
}
