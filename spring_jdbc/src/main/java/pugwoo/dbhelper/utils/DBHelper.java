package pugwoo.dbhelper.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import pugwoo.dbhelper.annotation.Column;
import pugwoo.dbhelper.annotation.Table;
import pugwoo.dbhelper.exception.NoColumnAnnotationException;
import pugwoo.dbhelper.exception.NoKeyColumnAnnotationException;
import pugwoo.dbhelper.exception.NoTableAnnotationException;

/**
 * 2015年1月12日 16:41:03 数据库操作封装：增删改查
 */
public class DBHelper {
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 
	 * @param t 值设置在t中
	 * @return 存在返回true，否则返回false
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> boolean getByKey(T t) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		
		Table table = DOInfoReader.getTable(t.getClass());
		if (table == null) {
			throw new NoTableAnnotationException();
		}
		
		List<Field> fields = DOInfoReader.getColumns(t.getClass());
		if (fields.isEmpty()) {
			throw new NoColumnAnnotationException();
		}
		
		int fieldSize = fields.size();
		List<String> keys = new ArrayList<String>();
		List<Object> keyValues = new ArrayList<Object>();
		for(int i = 0; i < fieldSize; i++) {
			Column column = DOInfoReader.getColumnInfo(fields.get(i));
			sql.append(column.value());
			if (i < fieldSize - 1) {
				sql.append(",");
			}
			if(column.isKey()) {
				keys.add(column.value());
				keyValues.add(DOInfoReader.getValue(fields.get(i), t));
			}
		}
		
		if(keys.isEmpty()) {
			throw new NoKeyColumnAnnotationException();
		}
		
		sql.append(" FROM ").append(table.value());
		sql.append(" WHERE ");
		
		int keysSize = keys.size();
		for(int i = 0; i < keysSize; i++) {
			sql.append(keys.get(i)).append("=?");
			if(i < keysSize - 1) {
				sql.append(" AND ");
			}
		}
		
		System.out.println("Exec SQL:" + sql.toString());
		try {
			jdbcTemplate.queryForObject(sql.toString(),
					new AnnotationSupportRowMapper(t.getClass(), t),
					keyValues.toArray());
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
	}
	
//	public <T> T getByKey(Class<?> clazz, Object key) {
//		
//	}
//	
//	public <T> T getByKey(Class<?> clazz, Map<String, Object> keyMap) {
//		
//	}
	
	/**
	 * 查询列表，没有查询条件
	 * 
	 * @param clazz
	 * @param page 从1开始
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> getList(final Class<T> clazz, int page, int pageSize) {
		int offset = (page - 1) * pageSize;
		return _getList(clazz, offset, pageSize);
	}
	
	/**
	 * 查询列表，查询所有记录
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getList(final Class<T> clazz) {
		return _getList(clazz, null, null);
	}

	/**
	 * 查询列表，没有查询条件
	 * 
	 * @param clazz
	 * @param offset 从0开始，null时不生效；当offset不为null时，要求limit存在
	 * @param limit null时不生效
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> List<T> _getList(Class<T> clazz, Integer offset, Integer limit) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		Table table = DOInfoReader.getTable(clazz);
		if (table == null) {
			throw new NoTableAnnotationException();
		}

		List<Field> fields = DOInfoReader.getColumns(clazz);
		if (fields.isEmpty()) {
			throw new NoColumnAnnotationException();
		}

		int fieldSize = fields.size();
		for (int i = 0; i < fieldSize; i++) {
			Column column = DOInfoReader.getColumnInfo(fields.get(i));
			sql.append(column.value());
			if (i < fieldSize - 1) {
				sql.append(",");
			}
		}

		sql.append(" FROM ").append(table.value());
		
		if (limit != null) {
			sql.append(" limit ");
			if(offset != null) {
				sql.append(offset).append(",");
			}
			sql.append(limit);
		}
		
		System.out.println("Exec SQL:" + sql.toString());
		return jdbcTemplate.query(sql.toString(), new AnnotationSupportRowMapper(clazz));
	}
	
	/**
	 * 插入一条记录，返回数据库实际修改条数。<br>
	 * 如果包含了自增id，则自增Id会被设置。
	 * 
	 * @param t
	 * @return
	 */
	public <T> int insert(T t) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO ");
		
		Table table = DOInfoReader.getTable(t.getClass());
		if (table == null) {
			throw new NoTableAnnotationException();
		}
		
		sql.append(table.value()).append(" (");
		
		final List<Field> fields = DOInfoReader.getColumns(t.getClass());
		if (fields.isEmpty()) {
			throw new NoColumnAnnotationException();
		}
		
		List<Object> values = new ArrayList<Object>();
		int fieldSize = fields.size();
		for (int i = 0; i < fieldSize; i++) {
			Column column = DOInfoReader.getColumnInfo(fields.get(i));
			sql.append(column.value());
			if (i < fieldSize - 1) {
				sql.append(",");
			}
			values.add(DOInfoReader.getValue(fields.get(i), t));
		}
		
		sql.append(") VALUES (");
		for (int i = 0; i < fieldSize; i++) {
			sql.append("?");
			if (i < fieldSize - 1) {
				sql.append(",");
			}
		}
		sql.append(")");
		
		System.out.println("Exec sql:" + sql.toString());
		return jdbcTemplate.update(sql.toString(), values.toArray());
	}
	

}
