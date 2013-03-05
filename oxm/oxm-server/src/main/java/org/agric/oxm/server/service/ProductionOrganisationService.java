/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.ProducerOrganisation;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.model.search.ProductionOrganisationSearchParameters;

/**
 * @author Job
 * 
 */
public interface ProductionOrganisationService {

	void save(ProducerOrganisation productionOrganisation);

	void validate(ProducerOrganisation productionOrganisation) throws ValidationException;

	List<ProducerOrganisation> getProductionOrganisations();

	List<ProducerOrganisation> getProductionOrganisationsWithParams(ProductionOrganisationSearchParameters params);

	ProducerOrganisation getProductionOrganisationById(String id);

	void deleteProductionOrganisationsByIds(String[] ids);
}
