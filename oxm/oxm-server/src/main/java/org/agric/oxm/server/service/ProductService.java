/**
 * 
 */
package org.agric.oxm.server.service;

import java.util.List;

import org.agric.oxm.model.Crop;
import org.agric.oxm.model.Product;
import org.agric.oxm.model.search.ProductSearchParameters;

/**
 * @author Job
 * 
 */
public interface ProductService extends
		BaseServiceWithSearch<Product, ProductSearchParameters> {

	List<Product> getBy(Crop crop);

	List<Product> getAnnonymouslyViewableProducts();
}
