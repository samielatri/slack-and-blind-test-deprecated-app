package database;

import model.group.Message;

import java.util.List;

public class SQLMessageDAO implements DAO<Message> {
	/**
	 *
 	 * @param obj
	 * @return
	 */
	@Override
	public Message insert(Message obj) {
		return null;
	}

	/**
	 *
	 * @param obj
	 */
	@Override
	public void delete(Message obj) {

	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public Message update(Message obj) {
		return null;
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Message select(String key) {
		return null;
	}

	/**
	 *
	 * @return
	 */
	@Override
	public List<Message> selectAll() {
		return null;
	}

	/**
	 *
	 */
	@Override
	public void close() {

	}
}
