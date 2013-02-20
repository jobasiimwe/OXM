/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 *
 */
public interface CropService {

	void save(Crop crop);

	void validate(Crop crop) throws ValidationException;

	List<Crop> getCrops();

	Crop getCropById(String id);

	void deleteCropsByIds(String[] ids);
}
