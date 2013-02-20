/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Producer;
import org.agric.oxm.model.exception.ValidationException;

/**
 * @author Job
 * 
 */
public interface ProducerService {

	void save(Producer producer);

	void validate(Producer producer) throws ValidationException;

	List<Producer> getProducers();

	Producer getProducerById(String id);

	void deleteProducersByIds(String[] ids);
}
