package src.database;

import model.HasId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MemoryDAO<T extends HasId> implements DAO<T> {

	private final Map<String, T> listT = new HashMap<>();

	/**
	 *
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public T insert(T obj) {
		System.out.println("Memory...");
		listT.put(obj.getId(), obj);
		return obj;
	}

	/**
	 *
	 * @param obj
	 */
	@Override
	public void delete(T obj) {
		listT.remove(obj.getId());
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public T update(T obj) {
		return insert(obj);
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	@Override
	public T select(String key) {
		return listT.get(key);
	}

	/**
	 *
	 * @return
	 */
	@Override
	public List<T> selectAll() {
		return new ArrayList<>(listT.values());
	}

	/**
	 *
	 */
	@Override
	public void close() {
		listT.clear();
	}
}
