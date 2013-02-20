/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Position;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface PositionService {

	void save(Position position);

	void validate(Position position) throws ValidationException;

	List<Position> getPositions();

	Position getPositionById(String id);

	Position getPositionByName(String name);

	void deletePositionsByIds(String[] ids);

}
