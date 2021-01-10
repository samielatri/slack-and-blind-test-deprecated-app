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
			System.out.println(getTableName());
			while (rs.next()) {
				results.add(create(rs));
			}
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}
		for(T obj: results){
			System.out.println(obj);
		}

		return results;
	}

	abstract protected T create(ResultSet rs) throws SQLException;

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
