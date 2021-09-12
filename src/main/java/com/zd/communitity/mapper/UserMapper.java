package com.zd.communitity.mapper;
import com.zd.communitity.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    /**
     * 当#{}中不是类的时候，mybatis不会自动加载，需要@param注入
     * 只有当是类的时候才会自动注入进去
     * @param token
     * @return
     */
    @Select("select * from user where token =#{token}")
    User findByToken(@Param("token") String token);
}
