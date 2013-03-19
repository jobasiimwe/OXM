/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Price;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PriceSearchParameters;

/**
 * @author Job
 * 
 */
public interface PriceService {

	void save(Price price);

	void validate(Price price) throws ValidationException;

	List<Price> getPrices();

	List<Price> searchWithParams(PriceSearchParameters params, Integer pageNo);

	long numberInSearch(PriceSearchParameters params);

	Price getPriceById(String id);

	void deletePricesByIds(String[] ids);

}
