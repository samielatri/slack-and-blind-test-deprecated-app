package database;

import model.group.WorkspaceChannel;

import java.sql.ResultSet;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class SQLWorkspaceChannelDAO extends AbstractSQLDAO<WorkspaceChannel> {

	@Override
	protected WorkspaceChannel create(ResultSet rs) {
		return null;
	}

	@Override
	protected String getTableName() {
		return null;
	}

	@Override
	public WorkspaceChannel insert(WorkspaceChannel obj) {
		return null;
	}

	@Override
	public void delete(WorkspaceChannel obj) {

	}

	@Override
	public WorkspaceChannel update(WorkspaceChannel obj) {
		return null;
	}

	@Override
	public WorkspaceChannel select(String key) {
		return null;
	}
}
