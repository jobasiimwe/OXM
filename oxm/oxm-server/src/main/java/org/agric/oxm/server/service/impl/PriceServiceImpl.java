/**
 * 
 */
package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Price;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.PriceDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Job
 * 
 */
@Service("priceService")
@Transactional
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceDAO priceDAO;

	@Secured({ PermissionConstants.ADD_PRICE, PermissionConstants.EDIT_PRICE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Price price) {
		priceDAO.save(price);
	}

	@Secured({ PermissionConstants.VIEW_PRICE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Price price) throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_PRICE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Price> getPrices() {
		return priceDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_PRICE })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Price getPriceById(String id) {
		return priceDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_PRICE })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deletePricesByIds(String[] ids) {
		priceDAO.removeByIds(ids);
	}

}
