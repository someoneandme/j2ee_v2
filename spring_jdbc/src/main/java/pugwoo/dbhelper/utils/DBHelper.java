package pugwoo.dbhelper.utils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import pugwoo.dbhelper.annotation.Column;
import pugwoo.dbhelper.annotation.Table;
import pugwoo.dbhelper.exception.NoColumnAnnotationException;
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
	private <T> List<T> _getList(final Class<T> clazz, Integer offset, Integer limit) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");

		Table table = DOInfoReader.getTable(clazz);
		if (table == null) {
			throw new NoTableAnnotationException();
		}

		final List<Field> fields = DOInfoReader.getColumns(clazz);
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
		return jdbcTemplate.query(sql.toString(), new RowMapper<T>() {
			public T mapRow(ResultSet rs, int index) throws SQLException {
				try {
					T t = clazz.newInstance();
					for(Field field : fields) {
						Column column = DOInfoReader.getColumnInfo(field);
						Object value = dbToJavaTypeAutoCast(field.getType(),
								rs.getObject(column.value()));
						DOInfoReader.setValue(field, t, value);
					}
					return t;
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});
	}
	
	/**
	 * 插入一条记录，返回该记录的引用，如果包含了自增id，则自增Id会被设置回来。
	 * 
	 * @param t
	 * @return
	 */
	public <T> T insert(T t) {
		
		return t;
	}
	
	/**
	 * 自动转换数据库到java的类型   TODO 待持续完善
	 * 
	 * @param targetClass
	 * @param value
	 * @return
	 */
	private static Object dbToJavaTypeAutoCast(Class<?> targetClass, Object value) {
		if(targetClass.isInstance(value)) {
			return value;
		}
		if(targetClass == Long.class || targetClass == long.class) {
			if(Integer.class.isInstance(value) || int.class.isInstance(value)) {
				return ((Integer) value).longValue();
			}
		}
		
		return value;
	}

}
