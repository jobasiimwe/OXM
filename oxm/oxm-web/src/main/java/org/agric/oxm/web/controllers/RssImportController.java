package org.agric.oxm.web.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.Price;
import org.agric.oxm.model.Product;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.feed.Feed;
import org.agric.oxm.model.feed.FeedPrice;
import org.agric.oxm.model.feed.RSSFeedParser;
import org.agric.oxm.model.search.PriceSearchParams;
import org.agric.oxm.model.util.MyDateUtil;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.security.PermissionConstants;
import org.agric.oxm.server.service.ConceptService;
import org.agric.oxm.server.service.PriceService;
import org.agric.oxm.server.service.ProductService;
import org.agric.oxm.server.service.SellingPlaceService;
import org.agric.oxm.web.WebConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("rss")
public class RssImportController {

	private static Logger log = LoggerFactory.getLogger(MyDateUtil.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private SellingPlaceService sellingPlaceService;

	@Autowired
	private ConceptService conceptService;

	@Autowired
	private PriceService priceService;

	@Autowired
	private PriceController priceController;

	@Secured({ PermissionConstants.PERM_ADMIN })
	@RequestMapping(value = { "pull-fit-prices" }, method = RequestMethod.GET)
	public ModelAndView pullFitPrices(ModelMap modelMap) {

		RSSFeedParser parser = new RSSFeedParser(
				"http://mis.infotradeuganda.com/feed/Oxfam");
		// http://infotradeuganda.com/feed/oxfam

		Feed feed = parser.readFeed();
		System.out.println(feed);

		ConceptCategory conceptCategoryUnitsOfMeasure = conceptService
				.getConceptCategoryByName(DefaultConceptCategories.UNITS_OF_MEASURE);

		List<Concept> unitsOfMeasure = conceptService
				.getConceptsByCategory(conceptCategoryUnitsOfMeasure);

		List<Product> products = productService.getAll();
		List<SellingPlace> sellingPlaces = sellingPlaceService.getAll();

		String longResponseText = "", addedStr = "", updatedStr = "", skippedStr = "";
		int processed = 0, added = 0, updated = 0, skipped = 0;

		for (FeedPrice t : feed.getPrices()) {

			processed++;

			try {

				// unitOfMeasure product sellingPlace ;
				// qty, retail, wholeSale date;

				Concept unit = getUnitOfMeasureByName(t.getUnit(),
						unitsOfMeasure, conceptCategoryUnitsOfMeasure);

				if (unit == null) {
					addMissingUnitOfMeasure(t.getUnit(),
							conceptCategoryUnitsOfMeasure);

					unitsOfMeasure = conceptService
							.getConceptsByCategory(conceptCategoryUnitsOfMeasure);

					unit = getUnitOfMeasureByName(t.getUnit(), unitsOfMeasure,
							conceptCategoryUnitsOfMeasure);

					if (unit == null)
						throw new Exception(
								"Failed to find/create unit of measure "
										+ t.getUnit());
				}

				Product product = getProductByName(t.getProduct(), products,
						unit);

				if (product == null) {
					addMissingProduct(t.getProduct(), unit);

					products = productService.getAll();

					product = getProductByName(t.getProduct(), products, unit);

					if (product == null)
						throw new Exception("Failed to find/create product "
								+ t.getProduct());
				}

				SellingPlace market = getSellingPlaceByName(t.getMarket(),
						sellingPlaces);
				if (market == null) {
					addMissingSellingPlace(t.getMarket());

					sellingPlaces = sellingPlaceService.getAll();

					market = getSellingPlaceByName(t.getMarket(), sellingPlaces);

					if (market == null)
						throw new Exception(
								"Failed to find/create selling place (market) "
										+ t.getUnit());
				}

				Double qty = 1.0;
				Double retail = parseDouble("Retail Price", t.getRetailStr());
				if (retail == null)
					throw new Exception("Failed to parse double  retail price "
							+ t.getRetailStr());

				Double wholeSale = parseDouble("wholesale Price",
						t.getWholeSaleStr());
				if (wholeSale == null)
					throw new Exception(
							"Failed to parse double whole sale price "
									+ t.getWholeSaleStr());

				Date date = parseDate("Price Date", t.getDateStr());

				Price newPrice = new Price(product, market, unit, qty, retail,
						wholeSale, date);

				PriceSearchParams priceSearchParams = new PriceSearchParams(
						newPrice.getProduct(), newPrice.getSellingPlace(),
						newPrice.getDate());

				Price existing = priceService
						.searchUniqueWithParams(priceSearchParams);

				if (existing != null) {
					Price.copy(existing, newPrice);
					
					updated++;
					updatedStr += t;
				} else {
					existing = newPrice;
					existing.setId(null);
					
					added++;
					addedStr += t;
				}

				priceService.save(existing);

			} catch (Exception e) {
				skipped++;
				skippedStr += t;

				log.error("Error generating price Object ", e);
				System.out.println("FAILED TO SAVE " + t);
			}
			
		}
		
		longResponseText += "\nProcessed " + processed;
		longResponseText += "\nNew Added " + added;
		longResponseText += "\nUpdated " + updated;
		longResponseText += "\nSkipped " + skipped;

		if (StringUtils.isNotBlank(addedStr))
			longResponseText += "\nNEWLY ADDED\n" + addedStr;
		if (StringUtils.isNotBlank(updatedStr))
			longResponseText += "\nEXISTING-UPDATED\n" + updatedStr;
		if (StringUtils.isNotBlank(skippedStr))
			longResponseText += "\nSKIPPED\n" + skippedStr;

		log.info(longResponseText);

		modelMap.put(WebConstants.LONG_RESPONSE_TEXT, longResponseText);
		
		return priceController.view(modelMap, true);
	}

	@Deprecated
	@Secured({ PermissionConstants.PERM_ADMIN })
	@RequestMapping(value = { "pull-fit-pricesOld" }, method = RequestMethod.GET)
	public ModelAndView pullFitPricesOLD(ModelMap modelMap) {
		RSSFeedParser parser = new RSSFeedParser(
				"http://mis.infotradeuganda.com/feed/Oxfam");
		// http://infotradeuganda.com/feed/oxfam
		Feed feed = parser.readFeed();
		System.out.println(feed);

		ConceptCategory conceptCategoryUnitsOfMeasure = conceptService
				.getConceptCategoryByName(DefaultConceptCategories.UNITS_OF_MEASURE);
		List<Concept> units = conceptService
				.getConceptsByCategory(conceptCategoryUnitsOfMeasure);

		List<Product> products = productService.getAll();
		List<SellingPlace> sellingPlaces = sellingPlaceService.getAll();

		for (FeedPrice t : feed.getPrices()) {

			try {
				Price newPrice = getPrice(t, sellingPlaces, products, units,
						conceptCategoryUnitsOfMeasure);

				PriceSearchParams priceSearchParams = new PriceSearchParams(
						newPrice.getProduct(), newPrice.getSellingPlace(),
						newPrice.getDate());

				Price existing = priceService
						.searchUniqueWithParams(priceSearchParams);

				if (existing != null) {
					Price.copy(existing, newPrice);
				} else {
					existing = newPrice;
					existing.setId(null);
				}

				priceService.save(existing);

			} catch (Exception e) {
				log.error("Error generating price Object ", e);
				System.out.println("FAILED TO SAVE " + t);
			}
		}
		return null;
	}

	private Price getPrice(FeedPrice t, List<SellingPlace> sellingPlaces,
			List<Product> products, List<Concept> unitsOfMeasure,
			ConceptCategory conceptCategoryUnitsOfMeasure) throws Exception {

		// unitOfMeasure product sellingPlace ;
		// qty, retail, wholeSale date;

		Concept unit = getUnitOfMeasureByName(t.getUnit(), unitsOfMeasure,
				conceptCategoryUnitsOfMeasure);

		if (unit == null) {
			addMissingUnitOfMeasure(t.getUnit(), conceptCategoryUnitsOfMeasure);

			unitsOfMeasure = conceptService
					.getConceptsByCategory(conceptCategoryUnitsOfMeasure);

			unit = getUnitOfMeasureByName(t.getUnit(), unitsOfMeasure,
					conceptCategoryUnitsOfMeasure);

			if (unit == null)
				throw new Exception("Failed to find/create unit of measure "
						+ t.getUnit());
		}

		Product product = getProductByName(t.getProduct(), products, unit);

		if (product == null) {
			addMissingProduct(t.getProduct(), unit);

			products = productService.getAll();

			product = getProductByName(t.getProduct(), products, unit);

			if (product == null)
				throw new Exception("Failed to find/create product "
						+ t.getProduct());
		}

		SellingPlace market = getSellingPlaceByName(t.getMarket(),
				sellingPlaces);
		if (market == null) {
			addMissingSellingPlace(t.getMarket());

			sellingPlaces = sellingPlaceService.getAll();

			market = getSellingPlaceByName(t.getMarket(), sellingPlaces);

			if (market == null)
				throw new Exception(
						"Failed to find/create selling place (market) "
								+ t.getUnit());
		}

		Double qty = 1.0;
		Double retail = parseDouble("Retail Price", t.getRetailStr());
		if (retail == null)
			throw new Exception("Failed to parse double  retail price "
					+ t.getRetailStr());

		Double wholeSale = parseDouble("wholesale Price", t.getWholeSaleStr());
		if (wholeSale == null)
			throw new Exception("Failed to parse double whole sale price "
					+ t.getWholeSaleStr());

		Date date = parseDate("Price Date", t.getDateStr());

		Price p = new Price(product, market, unit, qty, retail, wholeSale, date);

		return p;
	}

	private Concept getUnitOfMeasureByName(String name, List<Concept> units,
			ConceptCategory conceptCategoryUnitsOfMeasure) {
		for (Concept t : units) {
			if (name.trim().equalsIgnoreCase(t.getName().trim()))
				return t;
		}

		return null;
	}

	private void addMissingUnitOfMeasure(String name,
			ConceptCategory conceptCategoryUnitsOfMeasure) {

		log.debug("Adding Concept " + name);

		List<ConceptCategory> ccz = new ArrayList<>();

		ccz.add(conceptCategoryUnitsOfMeasure);

		Concept concept = new Concept(name, ccz, "Unit of measure " + name);
		conceptService.save(concept);
	}

	private Product getProductByName(String name, List<Product> products,
			Concept unitOfMeasure) {
		for (Product t : products) {
			if (name.trim().equalsIgnoreCase(t.getName().trim()))
				return t;
		}

		return null;
	}

	private void addMissingProduct(String name, Concept unitOfMeasure) {

		log.debug("Adding Product " + name);

		List<Concept> concepts = new ArrayList<>();
		concepts.add(unitOfMeasure);

		Product t = new Product(name, concepts);
		productService.save(t);
	}

	private SellingPlace getSellingPlaceByName(String name,
			List<SellingPlace> sellingPlaces) {
		for (SellingPlace t : sellingPlaces) {
			if (name.trim().equalsIgnoreCase(t.getName().trim()))
				return t;
		}

		addMissingSellingPlace(name);

		sellingPlaces = sellingPlaceService.getAll();

		return getSellingPlaceByName(name, sellingPlaces);
	}

	private void addMissingSellingPlace(String name) {
		log.debug("Adding Selling Place " + name);
		SellingPlace t = new SellingPlace(name);
		sellingPlaceService.save(t);
	}

	private Double parseDouble(String variableName, String doubleString) {
		Double t = null;
		try {
			t = Double.parseDouble(doubleString);
		} catch (Exception e) {
			log.error("Error parsing " + variableName + " \"" + doubleString
					+ "\"");
			log.error("Error", e);
		}
		return t;
	}

	private Date parseDate(String variableName, String dateString) {
		Date t = null;
		try {
			t = MyDateUtil.parseSqlShortDate(dateString);
		} catch (Exception e) {
			log.error("Error parsing " + variableName + " \"" + dateString
					+ "\"");
			log.error("Error", e);
		}
		return t;
	}
}
