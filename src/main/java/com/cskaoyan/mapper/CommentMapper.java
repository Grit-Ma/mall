package com.cskaoyan.mapper;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.CommentExample;
import com.cskaoyan.bean.vo.CommentVo;
import com.cskaoyan.bean.vo.GoodsCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    HashMap<String, Object> getCommentCount(int valueId, int type);

    List<GoodsCommentVo> selectCommentByValueIdAndTypeAndShowType(int valueId, int type, int hasPic);

    CommentVo selectCommentVoByValueId(int valueId);
}