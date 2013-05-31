package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.FinancialInstitution;
import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.FinancialInstitutionDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.FinancialInstitutionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("financialInstitutionService")
@Transactional
public class FinancialInstitutionServiceImpl implements
		FinancialInstitutionService {

	@Autowired
	private FinancialInstitutionDAO financialInstitutionDAO;

	@Secured({ PermissionConstants.ADD_FINANCIAL_INSTITUTION,
			PermissionConstants.EDIT_FINANCIAL_INSTITUTION })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(FinancialInstitution financialInstitution) {
		financialInstitutionDAO.save(financialInstitution);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(FinancialInstitution financialInstitution)
			throws ValidationException {
		if (StringUtils.isBlank(financialInstitution.getName()))
			throw new ValidationException("Name can not be blank");

		FinancialInstitution institution = getFinancialInstitutionByName(financialInstitution
				.getName());
		if (institution != null) {
			if (StringUtils.isEmpty(financialInstitution.getId())
					|| (StringUtils.isNotEmpty(financialInstitution.getId()) && !financialInstitution
							.equals(institution)))
				throw new ValidationException(
						"Finantial Institution with name "
								+ financialInstitution.getName()
								+ " already exists in the database");
		}
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<FinancialInstitution> getFinancialInstitutions() {
		return financialInstitutionDAO
				.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public FinancialInstitution getFinancialInstitutionById(String id) {
		return financialInstitutionDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.VIEW_FINANCIAL_INSTITUTION })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public FinancialInstitution getFinancialInstitutionByName(String name) {
		return financialInstitutionDAO
				.searchUniqueByPropertyEqual("name", name);
	}

	@Secured({ PermissionConstants.DELETE_FINANCIAL_INSTITUTION })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteFinancialInstitutionsByIds(String[] ids) {
		financialInstitutionDAO.removeByIds(ids);
	}

}
