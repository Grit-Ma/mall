package com.cskaoyan.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringArrayStringTypeHandler implements TypeHandler<String[]> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, String[] strings, JdbcType jdbcType) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(String string : strings){
            sb.append(string).append(",");
        }
        String sub = sb.substring(0, sb.length() - 1);
        sb.append("]");
        preparedStatement.setString(index,sub);
    }

    @Override
    public String[] getResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return transString(string);
    }

    @Override
    public String[] getResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        String[] strings = transString(string);
        return strings;
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        String[] strings = transString(string);
        return strings;
    }

    public String[] transString(String s){
        if(s == null || "[]".equals(s)){
            return new String[1];
        }
        String substring = s.substring(2, s.length() - 2); //去除头尾的【】
        String[] split = substring.split("\", \"");
        return split;
    }
}
