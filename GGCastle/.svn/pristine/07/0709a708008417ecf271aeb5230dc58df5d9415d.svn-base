package com.sga.sol.customdbappender;

import ch.qos.logback.classic.db.names.DBNameResolver;

public class CustomDBAppenderResolver implements DBNameResolver{

	private String tableNamePrefix = "";
	
	public String getTableNamePrefix() {
		return tableNamePrefix;
	}
	
	public void setTableNamePrefix(String tableNamePrefix) {
		this.tableNamePrefix = tableNamePrefix;
	}
	
	@Override
	public <N extends Enum<?>> String getTableName(N tableName) {
		if (tableName.name().toLowerCase().equals("data")) {
			return tableNamePrefix + "data";
		}
		throw new IllegalArgumentException(tableName + " is an unknown table name");
	}

	@Override
	public <N extends Enum<?>> String getColumnName(N columnName) {
		return columnName.name().toLowerCase();
	}
	
	

}
