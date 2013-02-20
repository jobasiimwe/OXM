package org.agric.oxm.server.service.impl;

import java.util.List;

import org.agric.oxm.model.Producer;
import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.model.exception.ValidationException;
import org.agric.oxm.server.dao.ProducerDAO;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Job
 * 
 */
@Transactional
@Service("producerService")
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private ProducerDAO producerDAO;

	@Secured({ PermissionConstants.ADD_PRODUCER,
			PermissionConstants.EDIT_PRODUCER })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Producer producer) {
		producerDAO.save(producer);
	}

	@Secured({ PermissionConstants.VIEW_PRODUCER })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public void validate(Producer producer) throws ValidationException {

	}

	@Secured({ PermissionConstants.VIEW_PRODUCER })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Producer> getProducers() {
		return producerDAO.searchByRecordStatus(RecordStatus.ACTIVE);
	}

	@Secured({ PermissionConstants.VIEW_PRODUCER })
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Producer getProducerById(String id) {
		return producerDAO.searchUniqueByPropertyEqual("id", id);
	}

	@Secured({ PermissionConstants.DELETE_PRODUCER })
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deleteProducersByIds(String[] ids) {
		producerDAO.removeByIds(ids);
	}

}
