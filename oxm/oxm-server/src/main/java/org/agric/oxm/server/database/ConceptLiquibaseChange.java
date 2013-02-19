package org.agric.oxm.server.database;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import liquibase.FileOpener;
import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.InvalidChangeDefinitionException;
import liquibase.exception.SetupException;
import liquibase.exception.UnsupportedChangeException;

import org.agric.oxm.model.RecordStatus;
import org.agric.oxm.server.ConceptAnnotation;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.agric.oxm.server.DefaultConcepts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConceptLiquibaseChange implements CustomTaskChange {

    private Logger log = LoggerFactory
	    .getLogger(this.getClass());

    @Override
    public String getConfirmationMessage() {
	return null;
    }

    @Override
    public void setFileOpener(FileOpener arg0) {

    }

    @Override
    public void setUp() throws SetupException {

    }

    @Override
    public void validate(Database arg0) throws InvalidChangeDefinitionException {

    }

    @Override
    public void execute(Database database) throws CustomChangeException, UnsupportedChangeException {
	log.debug("Extracting fields from class DefaultConcepts");
	Field[] conceptFields = DefaultConcepts.class.getFields();
	if (conceptFields != null) {
	    for (Field conceptField : conceptFields) {
		log.debug("Extracting annotation from field >>" + conceptField.getName());

		ConceptAnnotation conceptAnnotation = conceptField
			.getAnnotation(ConceptAnnotation.class);
		if (conceptAnnotation != null) {
		    try {
			insertConcept(conceptAnnotation, database, conceptField);

			insertRelationshipBetweenConceptAndConceptCategory(
				database, conceptField, conceptAnnotation);

		    } catch (SQLException e) {
			log.error(this.getClass().getName(), e);
		    } catch (IllegalArgumentException e) {
			log.error(this.getClass().getName(), e);
		    } catch (IllegalAccessException e) {
			log.error(this.getClass().getName(), e);
		    }
		}
	    }
	}
    }

    private void insertRelationshipBetweenConceptAndConceptCategory(Database database,
	    Field conceptField, ConceptAnnotation conceptAnnotation) throws SQLException,
	    IllegalArgumentException, IllegalAccessException {
	String[] conceptCategories = conceptAnnotation.ConceptCategories();

	if (conceptCategories != null) {
	    Field[] conceptCategoryFields = DefaultConceptCategories.class
		    .getFields();

	    for (String conceptCategory : conceptCategories) {
		for (Field conceptCategoryField : conceptCategoryFields) {

		    Object fieldValue = conceptCategoryField.get(null);
		    if (fieldValue != null && fieldValue instanceof String) {
			if (conceptCategory.equalsIgnoreCase((String) fieldValue)) {

			    ConceptCategoryAnnotation conceptCategoryAnnotation = conceptCategoryField
				    .getAnnotation(ConceptCategoryAnnotation.class);
			    if (conceptCategoryAnnotation != null) {
				PreparedStatement preConditionStatement = database
					.getConnection()
					.prepareStatement(
						"SELECT 1 FROM concept_category_link WHERE concept_id=? AND concept_category_id=?");

				preConditionStatement.setString(1, conceptAnnotation.id());
				preConditionStatement.setString(2,
					conceptCategoryAnnotation.id());

				String sql = "INSERT INTO concept_category_link (concept_id, concept_category_id) "
					+ "VALUES (?, ?)";

				PreparedStatement statement = database.getConnection()
					.prepareStatement(sql);
				statement.setString(1, conceptAnnotation.id());
				statement.setString(2, conceptCategoryAnnotation.id());

				ResultSet preConditionResult = preConditionStatement
					.executeQuery();
				if (preConditionResult != null
					&& preConditionResult.next()) {
				    int value = preConditionResult.getInt(1);
				    preConditionResult.close();

				    if (value == 1) {
					log.debug("Skipping Field >> "
						+ conceptField.getName()
						+ " because it already exists in database");
				    } else {
					statement.execute();
				    }
				} else {
				    statement.execute();
				}
			    }
			}
		    }
		}
	    }

	}
    }

    private void insertConcept(ConceptAnnotation conceptAnnotation, Database database,
	    Field conceptField) throws SQLException, IllegalArgumentException,
	    IllegalAccessException {
	PreparedStatement preConditionStatement = database
		.getConnection()
		.prepareStatement(
			"SELECT 1 FROM concepts WHERE id=?");

	preConditionStatement.setString(1, conceptAnnotation.id());

	String sql = "INSERT INTO concepts (id, concept_description, concept_name, record_status) "
		+ "VALUES (?, ?, ?, ?)";

	PreparedStatement statement = database.getConnection()
		.prepareStatement(sql);
	statement.setString(1, conceptAnnotation.id());
	statement.setString(2, conceptAnnotation.description());
	statement.setString(3, (String) conceptField.get(null));
	statement.setInt(4, RecordStatus.ACTIVE.ordinal());

	ResultSet preConditionResult = preConditionStatement
		.executeQuery();
	if (preConditionResult != null
		&& preConditionResult.next()) {
	    int value = preConditionResult.getInt(1);
	    preConditionResult.close();

	    if (value == 1) {
		log.debug("Skipping Field >> " + conceptField.getName()
			+ " because it already exists in database");
	    } else {
		statement.execute();
	    }
	} else {
	    statement.execute();
	}
    }
}
