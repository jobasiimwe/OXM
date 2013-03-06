package org.agric.oxm.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.agric.oxm.model.Concept;
import org.agric.oxm.model.ConceptCategory;
import org.agric.oxm.model.Crop;
import org.agric.oxm.model.District;
import org.agric.oxm.model.Document;
import org.agric.oxm.model.Parish;
import org.agric.oxm.model.Permission;
import org.agric.oxm.model.Publication;
import org.agric.oxm.model.Role;
import org.agric.oxm.model.SellingPlace;
import org.agric.oxm.model.SubCounty;
import org.agric.oxm.model.User;
import org.agric.oxm.model.Village;
import org.agric.oxm.web.propertyeditors.ConceptCategoryPropertyEditor;
import org.agric.oxm.web.propertyeditors.ConceptPropertyEditor;
import org.agric.oxm.web.propertyeditors.CropPropertyEditor;
import org.agric.oxm.web.propertyeditors.DatePropertyEditor;
import org.agric.oxm.web.propertyeditors.DistrictPropertyEditor;
import org.agric.oxm.web.propertyeditors.DocumentPropertyEditor;
import org.agric.oxm.web.propertyeditors.ParishPropertyEditor;
import org.agric.oxm.web.propertyeditors.PermissionPropertyEditor;
import org.agric.oxm.web.propertyeditors.PublicationPropertyEditor;
import org.agric.oxm.web.propertyeditors.RolePropertyEditor;
import org.agric.oxm.web.propertyeditors.SellingPlacePropertyEditor;
import org.agric.oxm.web.propertyeditors.SubCountyPropertyEditor;
import org.agric.oxm.web.propertyeditors.UserPropertyEditor;
import org.agric.oxm.web.propertyeditors.VillagePropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class OXMBindingInitializer implements WebBindingInitializer {

    @Autowired
    private DistrictPropertyEditor districtPropertyEditor;

    @Autowired
    private ConceptPropertyEditor conceptPropertyEditor;

    @Autowired
    private RolePropertyEditor rolePropertyEditor;

    @Autowired
    private PermissionPropertyEditor permissionPropertyEditor;

    @Autowired
    private ConceptCategoryPropertyEditor conceptCategoryPropertyEditor;

    @Autowired
    private SubCountyPropertyEditor subCountyPropertyEditor;

    @Autowired
    private ParishPropertyEditor parishPropertyEditor;

    @Autowired
    private VillagePropertyEditor villagePropertyEditor;

    @Autowired
    private PublicationPropertyEditor publicationPropertyEditor;

    @Autowired
    private DocumentPropertyEditor documentPropertyEditor;
    
    @Autowired
    private UserPropertyEditor userPropertyEditor;
    
    @Autowired
    private SellingPlacePropertyEditor sellingPlacePropertyEditor;
    
    @Autowired
    private CropPropertyEditor cropPropertyEditor;
    
    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {
	binder.registerCustomEditor(District.class, districtPropertyEditor);
	binder.registerCustomEditor(Concept.class, conceptPropertyEditor);
	binder.registerCustomEditor(Date.class, new DatePropertyEditor(new SimpleDateFormat(
		"dd/MM/yyyy")));
	binder.registerCustomEditor(Role.class, rolePropertyEditor);
	binder.registerCustomEditor(Permission.class, permissionPropertyEditor);
	binder.registerCustomEditor(ConceptCategory.class, conceptCategoryPropertyEditor);
	binder.registerCustomEditor(SubCounty.class, subCountyPropertyEditor);
	binder.registerCustomEditor(Parish.class, parishPropertyEditor);
	binder.registerCustomEditor(Village.class, villagePropertyEditor);
	binder.registerCustomEditor(Publication.class, publicationPropertyEditor);
	binder.registerCustomEditor(Document.class, documentPropertyEditor);
	binder.registerCustomEditor(User.class, userPropertyEditor);
	binder.registerCustomEditor(SellingPlace.class, sellingPlacePropertyEditor);
	binder.registerCustomEditor(Crop.class, cropPropertyEditor);
    }
}
