package database;

import model.HasId;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T extends HasId> extends Closeable {

	default T signIn(T obj){
		return null;
	}

	T insert(T obj);

	void delete(T obj);

	T update(T obj);

	T select(String key) throws SQLException;

	List<T> selectAll();

	@Override
	void close();

}
