package com.sga.sol.customdbappender;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;

public class CustomDBAppender extends DBAppenderBase<ILoggingEvent>{
	
	protected static final Method GET_GENERATED_KEYS_METHOD;
	protected String insertSQL;
	
	private CustomDBAppenderResolver customDBAppenderResolver;
	
	static {
        Method getGeneratedKeysMethod;
        try {
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }
	
	public CustomDBAppender() {}
	
	void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException {
		try {
			int i = 0;
			for (Map.Entry<String, String> entry : event.getMDCPropertyMap().entrySet()) {
				i++;
				if (entry.getKey().equals(CustomDBAppenderColumnName.auth_time.toString())) {// timestamp
					stmt.setTimestamp(i, Timestamp.valueOf(entry.getValue()));
				}
				else if (entry.getKey().equals(CustomDBAppenderColumnName.auth_yn.toString())) {// boolean
					if (entry.getValue().equals("false")) {
						stmt.setBoolean(i, false);
					}else {
						stmt.setBoolean(i, true);
					}
				}
				else {
					stmt.setString(i, entry.getValue());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start() {
		if (customDBAppenderResolver == null) {
			customDBAppenderResolver = new CustomDBAppenderResolver();
			insertSQL = CustomDBAppenderBuilder.buildInsertSQL(customDBAppenderResolver);
		}
		super.start();
	}

	@Override
	protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement)
			throws Throwable {
		bindLoggingEventWithInsertStatement(insertStatement, event);
		
		int updateCount = insertStatement.executeUpdate();
		if (updateCount != 1) {
			addWarn("Failed to insert loggingEvent");
		}
	}

	@Override
	protected Method getGeneratedKeysMethod() {
		return GET_GENERATED_KEYS_METHOD;
	}

	@Override
	protected String getInsertSQL() {
		return insertSQL;
	}

	@Override
	protected void secondarySubAppend(ILoggingEvent eventObject, Connection connection, long eventId) throws Throwable {
	}

	
}
