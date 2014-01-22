/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Price;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.PriceSearchParams;

/**
 * @author Job
 * 
 */
public interface PriceService {

	void save(Price price);

	void validate(Price price) throws ValidationException;

	List<Price> getPrices();

	Price searchUniqueWithParams(PriceSearchParams params) throws Exception;

	List<Price> searchWithParams(PriceSearchParams params, Integer pageNo);

	long numberInSearch(PriceSearchParams params);

	Price getPriceById(String id);

	void deletePricesByIds(String[] ids);

	List<Price> getAnnonymouslyViewablePrices();
}
