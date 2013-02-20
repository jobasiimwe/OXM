/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Price;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface PriceService {

	void save(Price price);

	void validate(Price price) throws ValidationException;

	List<Price> getPrices();

	Price getPriceById(String id);

	void deletePricesByIds(String[] ids);
}
