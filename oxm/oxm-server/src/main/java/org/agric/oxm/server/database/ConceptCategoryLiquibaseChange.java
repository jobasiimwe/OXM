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

import org.agric.oxm.model.enums.RecordStatus;
import org.agric.oxm.server.ConceptCategoryAnnotation;
import org.agric.oxm.server.DefaultConceptCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConceptCategoryLiquibaseChange implements CustomTaskChange {

    private Logger log = LoggerFactory
			.getLogger(ConceptCategoryLiquibaseChange.class);

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
    public void execute(Database database) throws CustomChangeException,
			UnsupportedChangeException {

	log.debug("Extracting fields from >> class DefaultConceptCategories");
	Field[] fields = DefaultConceptCategories.class.getFields();
	if (fields != null) {
	    for (Field field : fields) {
		log.debug("Extracting annotation from field >> "
						+ field.getName());
		ConceptCategoryAnnotation annotation = field
						.getAnnotation(ConceptCategoryAnnotation.class);
		if (annotation != null) {
		    try {
			PreparedStatement preConditionStatement = database
								.getConnection()
								.prepareStatement(
										"SELECT 1 FROM concept_categories WHERE id=?");

			preConditionStatement.setString(1, annotation.id());

			String sql = "INSERT INTO concept_categories (id, category_description, category_name, record_status) "
								+ "VALUES (?, ?, ?, ?)";
			PreparedStatement statement = database.getConnection()
								.prepareStatement(sql);
			statement.setString(1, annotation.id());
			statement.setString(2, annotation.description());
			statement.setString(3, (String) field.get(null));
			statement.setInt(4, RecordStatus.ACTIVE.ordinal());

			ResultSet preConditionResult = preConditionStatement
								.executeQuery();
			if (preConditionResult != null
								&& preConditionResult.next()) {
			    int value = preConditionResult.getInt(1);
			    preConditionResult.close();

			    if (value == 1) {
				log.debug("Skipping Field >> " + field.getName()
					+ " because it already exists in database");
			    } else {
				statement.execute();
			    }
			} else {
			    statement.execute();
			}

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
}
