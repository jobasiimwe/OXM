/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Crop;
import org.agric.oxm.server.dao.CropDAO;
import org.springframework.stereotype.Repository;

/**
 * @author Job
 *
 */
@Repository("CropDAO")
public class HibernateCropDAOImpl extends BaseDAOImpl<Crop> implements CropDAO {

}
