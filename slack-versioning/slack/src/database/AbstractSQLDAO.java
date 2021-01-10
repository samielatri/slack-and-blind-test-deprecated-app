package database;

import model.HasId;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractSQLDAO<T extends HasId> implements DAO<T> {

	protected final Connection connection = DBConnection.createConnection();

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
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
	}
}
