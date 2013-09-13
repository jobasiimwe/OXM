/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Position;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.SingleStringSearchParameters;

/**
 * @author Job
 * 
 */
public interface PositionService {

	void save(Position position);

	void validate(Position position) throws ValidationException;

	List<Position> getPositions();

	List<Position> searchWithParams(SingleStringSearchParameters params,
			Integer pageNo);

	int numberOfPositionsWithSearchParams(SingleStringSearchParameters params);

	Position getPositionById(String id);

	Position getPositionByName(String name);

	void deletePositionsByIds(String[] ids);

}
