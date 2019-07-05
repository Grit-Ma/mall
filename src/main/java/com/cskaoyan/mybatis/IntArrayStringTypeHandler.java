package com.cskaoyan.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntArrayStringTypeHandler implements TypeHandler<int[]> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, int[] role_ids, JdbcType jdbcType) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int value:role_ids){
            sb.append(value).append(",");
        }
        String sub = sb.substring(0, sb.length() - 1);
        preparedStatement.setString(index,sub+"]");
    }

    @Override
    public int[] getResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return transString(string);
    }

    @Override
    public int[] getResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        int[] ints = transString(string);
        return ints;
    }

    @Override
    public int[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        int[] ints = transString(string);
        return ints;
    }

    public int[] transString(String s){
        String substring = s.substring(1, s.length() - 1); //去除头尾的【】
        String[] split = substring.split(",");
        int[] ints = new int[split.length];
        for (int i = 0; i <split.length ; i++) {
            ints[i]=Integer.parseInt(split[i]);
        }
        return ints;
    }
}
