/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.ProductionLevel;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 *
 */
public interface ProductionLevelService {

	void save(ProductionLevel productionLevel);

	void validate(ProductionLevel productionLevel) throws ValidationException;

	List<ProductionLevel> getProductionLevels();

	ProductionLevel getProductionLevelById(String id);

	void deleteProductionLevelsByIds(String[] ids);
}
