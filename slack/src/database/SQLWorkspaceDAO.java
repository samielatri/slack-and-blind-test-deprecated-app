package database;

import model.group.Workspace;

import java.sql.ResultSet;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class SQLWorkspaceDAO extends AbstractSQLDAO<Workspace> {


	@Override
	public Workspace insert(Workspace obj) {
		return null;
	}

	@Override
	public void delete(Workspace obj) {

	}

	@Override
	public Workspace update(Workspace obj) {
		return null;
	}

	@Override
	public Workspace select(String key) {
		return null;
	}

	@Override
	protected Workspace create(ResultSet rs) {
		return null;
	}

	@Override
	protected String getTableName() {
		return null;
	}
}
