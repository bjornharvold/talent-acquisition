package com.tps.tpi.service;

import com.tps.tpi.exception.DomainException;
import com.tps.tpi.model.objects.entity.Source;

/**
 * User: Bjorn Harvold
 * Date: Dec 12, 2009
 * Time: 1:24:07 AM
 * Responsibility:
 */
public interface SourcingService {
    Source saveSource(Source source) throws DomainException;

    Source getSource(String id) throws DomainException;

    Source saveSourceFromGTE(Source source) throws DomainException;
}
