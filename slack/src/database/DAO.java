package database;

import model.HasId;

import java.io.Closeable;
import java.util.List;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public interface DAO<T extends HasId> extends Closeable {

	T insert(T obj);

	void delete(T obj);

	T update(T obj);

	T select(String key);

	List<T> selectAll();

	@Override
	void close();

}
