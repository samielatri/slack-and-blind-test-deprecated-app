package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public abstract class AbstractSQLDAO<T> implements DAO<T> {

	protected final Connection connection = ConnectionBuilder.createConnection();

	@Override
	public List<T> selectAll() {
		List<T> results = new ArrayList<>();
		try(Statement st = connection.createStatement();
		    ResultSet rs = st.executeQuery("SELECT * FROM " + getTableName())) {
			while (rs.next()) {
				results.add(create(rs));
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return results;
	}

	abstract protected T create(ResultSet rs);

	abstract protected String getTableName();

	@Override
	public void close() {
		try {
			connection.close();
		} catch (SQLException throwables) {
		}
	}
}
