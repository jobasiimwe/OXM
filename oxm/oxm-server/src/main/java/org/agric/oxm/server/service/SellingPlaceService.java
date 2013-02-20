/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface SellingPlaceService {

	void save(SellingPlace sellingPlace);

	void validate(SellingPlace sellingPlace) throws ValidationException;

	List<SellingPlace> getSellingPlaces();

	SellingPlace getSellingPlaceById(String id);

	void deleteSellingPlacesByIds(String[] ids);

}
