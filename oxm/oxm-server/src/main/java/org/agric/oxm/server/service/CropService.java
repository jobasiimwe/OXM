/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.CropSearchParameters;

/**
 * @author Job
 * 
 */
public interface CropService {

	void save(Crop crop);

	void validate(Crop crop) throws ValidationException;

	List<Crop> getCrops();

	Crop getCropById(String id);

	Crop getCropByName(String name);

	void deleteCropsByIds(String[] ids);

	List<Crop> searchWithParams(CropSearchParameters params, int pageNo);

	int numberOfCropsWithSearchParams(CropSearchParameters params);
}
