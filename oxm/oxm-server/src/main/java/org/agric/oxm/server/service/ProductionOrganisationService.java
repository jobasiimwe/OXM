/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.ProductionOrganisation;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProductionOrganisationSearchParameters;

/**
 * @author Job
 * 
 */
public interface ProductionOrganisationService {

	void save(ProductionOrganisation productionOrganisation);

	void validate(ProductionOrganisation productionOrganisation) throws ValidationException;

	List<ProductionOrganisation> getProductionOrganisations();

	List<ProductionOrganisation> getProductionOrganisationsWithParams(ProductionOrganisationSearchParameters params);

	ProductionOrganisation getProductionOrganisationById(String id);

	void deleteProductionOrganisationsByIds(String[] ids);
}
