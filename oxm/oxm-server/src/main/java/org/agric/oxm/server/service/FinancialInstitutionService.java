/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface FinancialInstitutionService {

	void save(FinancialInstitution financialInstitution);

	void validate(FinancialInstitution financialInstitution)
			throws ValidationException;

	List<FinancialInstitution> getFinancialInstitutions();

	FinancialInstitution getFinancialInstitutionById(String id);

	FinancialInstitution getFinancialInstitutionByName(String name);

	void deleteFinancialInstitutionsByIds(String[] ids);

	List<FinancialInstitution> getAnnonymouslyViewableFInstitutions();
}
