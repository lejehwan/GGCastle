package com.sga.sol.customdbappender;

public class CustomDBAppenderBuilder {
	
	static String buildInsertSQL(CustomDBAppenderResolver customDBAppenderResolver) {
		
		StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ");
		sqlBuilder.append(customDBAppenderResolver.getTableName(CustomDBAppenderTableName.data)).append(" (");
		sqlBuilder.append(customDBAppenderResolver.getColumnName(CustomDBAppenderColumnName.auth_time)).append(", ");
		sqlBuilder.append(customDBAppenderResolver.getColumnName(CustomDBAppenderColumnName.parameter)).append(", ");
		sqlBuilder.append(customDBAppenderResolver.getColumnName(CustomDBAppenderColumnName.auth_yn)).append(", ");
		sqlBuilder.append(customDBAppenderResolver.getColumnName(CustomDBAppenderColumnName.user_id)).append(") ");
		
		sqlBuilder.append("VALUES (?,?,?,?)");
		return sqlBuilder.toString();
	}

}
