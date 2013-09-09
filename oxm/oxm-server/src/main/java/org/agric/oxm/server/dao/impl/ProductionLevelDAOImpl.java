package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.ProductionLevel;
import org.agric.oxm.server.dao.ProductionLevelDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 * 
 */
@Repository("productionLevelDAO")
public class ProductionLevelDAOImpl extends
		BaseDAOImpl<ProductionLevel> implements ProductionLevelDAO {

}
