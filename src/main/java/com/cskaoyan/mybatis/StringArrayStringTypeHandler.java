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
            sb.append('"').append(string).append("\", ");
        }
        String sub = sb.toString();
        if(strings.length > 0){
            sub = sb.substring(0, sb.length() - 2);
        }
        sub += "]";
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
            return new String[0];
        }
        String substring = "";
        try {
            substring = s.substring(2, s.length() - 2); //去除头尾的【】
        }catch (StringIndexOutOfBoundsException e){
            return new String[0];
        }
        String[] split = substring.split("\", \"");
        return split;
    }
}
