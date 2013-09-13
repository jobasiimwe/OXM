/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.LandUse;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 *
 */
public interface LandUseService {

	void save(LandUse landUse);

	void validate(LandUse landUse) throws ValidationException;

	List<LandUse> getLandUses();

	LandUse getLandUseById(String id);

	void deleteLandUsesByIds(String[] ids);
}
