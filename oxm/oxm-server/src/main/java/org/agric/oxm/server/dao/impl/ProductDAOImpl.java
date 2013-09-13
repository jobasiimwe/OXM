/**
 * 
 */
package org.agric.oxm.server.dao.impl;

import org.agric.oxm.model.Product;
import org.agric.oxm.server.dao.ProductDAO;
import org.springframework.stereotype.Repository;

/**
 * Implements the {@link ProductDAO }
 * 
 * @author Job
 * 
 */
@Repository("productDAO")
public class ProductDAOImpl extends BaseDAOImpl<Product> implements ProductDAO {

}
