package database;

import model.group.Message;

import java.util.List;

/**
 * @author Olivier Pitton <olivier@indexima.com> on 18/12/2020
 */

public class SQLMessageDAO implements DAO<Message> {
	@Override
	public Message insert(Message obj) {
		return null;
	}

	@Override
	public void delete(Message obj) {

	}

	@Override
	public Message update(Message obj) {
		return null;
	}

	@Override
	public Message select(String key) {
		return null;
	}

	@Override
	public List<Message> selectAll() {
		return null;
	}

	@Override
	public void close() {

	}
}
