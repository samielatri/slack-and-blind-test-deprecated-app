package database;

import model.HasId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public final class MemoryDAO<T extends HasId> implements DAO<T> {

	private final Map<String, T> workspaces = new HashMap<>();

	@Override
	public T insert(T obj) {
		workspaces.put(obj.getId(), obj);
		return obj;
	}

	@Override
	public void delete(T obj) {
		workspaces.remove(obj.getId());
	}

	@Override
	public T update(T obj) {
		return insert(obj);
	}

	@Override
	public T select(String key) {
		return workspaces.get(key);
	}

	@Override
	public List<T> selectAll() {
		return new ArrayList<>(workspaces.values());
	}

	@Override
	public void close() {
		workspaces.clear();
	}
}
