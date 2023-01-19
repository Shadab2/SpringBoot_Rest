package com.shadab.expensetrackerapi.repository;

import com.shadab.expensetrackerapi.domain.Category;
import com.shadab.expensetrackerapi.exception.EtBadRequestException;
import com.shadab.expensetrackerapi.exception.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CategoryRepositoryImp implements CategoryRepository{

    private static final String SQL_FIND_ALL = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? GROUP BY C.CATEGORY_ID";
    private static final String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";
    private static final String SQL_CREATE = "INSERT INTO ET_CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) VALUES(NEXTVAL('ET_CATEGORIES_SEQ'), ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ET_CATEGORIES SET TITLE = ?, DESCRIPTION = ? " +
            "WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM ET_CATEGORIES WHERE USER_ID = ? AND CATEGORY_ID = ?";
    private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM ET_TRANSACTIONS WHERE CATEGORY_ID = ?";


    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
       return jdbcTemplate.query(SQL_FIND_ALL,new Object[]{userId},categoryRoMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtBadRequestException {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId,categoryId},categoryRoMapper);
        }catch (Exception e){
            throw  new EtBadRequestException("Category Not Found");
        }
    }

    @Override
    public Integer create(Integer userId, String title, String descpription) throws EtBadRequestException {
        if(title.length()==0) throw new EtBadRequestException("Invalid  title");
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,userId);
                ps.setString(2,title);
                ps.setString(3,descpription);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
        }catch(Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        try{
            jdbcTemplate.update(SQL_UPDATE,new Object[]{category.getTitle(),category.getDescription(),userId,categoryId});
        }catch (Exception e){
            throw  new EtBadRequestException("Invalid Request");
        }
    }

    private void removeAllCatTransactions(Integer categoryId) {
        jdbcTemplate.update(SQL_DELETE_ALL_TRANSACTIONS, new Object[]{categoryId});
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
        removeAllCatTransactions(categoryId);
        int count = jdbcTemplate.update(SQL_DELETE_CATEGORY,new Object[]{userId,categoryId});
        if(count==0) throw new EtResourceNotFoundException("No such category");
    }

    private RowMapper<Category> categoryRoMapper = ((rs, rowNum) -> {
        return new Category(
                rs.getInt("CATEGORY_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                rs.getDouble("TOTAL_EXPENSE")
        );
    });
}
