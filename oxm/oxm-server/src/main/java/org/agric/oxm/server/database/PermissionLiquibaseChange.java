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
import org.agric.oxm.server.security.PermissionAnnotation;
import org.agric.oxm.server.security.PermissionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermissionLiquibaseChange implements CustomTaskChange {

	private Logger log = LoggerFactory
			.getLogger(PermissionLiquibaseChange.class);

	@Override
	public void execute(Database database) throws CustomChangeException,
			UnsupportedChangeException {
		log
				.debug("Extracting fields from >>");
		Field[] fields = PermissionConstants.class.getFields();
		if (fields != null) {
			for (Field field : fields) {
				log.debug("Extracting annotation from field >> "
						+ field.getName());
				PermissionAnnotation annotation = field
						.getAnnotation(PermissionAnnotation.class);
				if (annotation != null) {
					try {
						PreparedStatement preConditionStatement = database
								.getConnection().prepareStatement(
										"SELECT 1 FROM permissions WHERE id=?");

						preConditionStatement.setString(1, annotation.id());

						String sql = "INSERT INTO permissions (id, description, permission_name, record_status) "
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
								log
										.debug("Skipping Field >> "
												+ field.getName()
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

}
