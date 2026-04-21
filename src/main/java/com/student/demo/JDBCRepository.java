package com.student.demo;

import com.student.demo.entity.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public JDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Student> map = (rs, rowNum)->{
        Student st = new Student();
        st.setId(rs.getLong("id"));
        st.setName(rs.getString("name"));
        st.setFileName(rs.getString("fileName"));
        st.setCourse(rs.getString("course"));
        return st;
    };

    public List<Student> findAll(){
        String sql = "SELECT * FROM Student";
        return jdbcTemplate.query(sql,map);
    }

    public Student findById(Long id){
        String sql = "SELECT * FROM Student WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, map, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Student save(Student st){
        String sql = "INSERT INTO Student(name,fileName,course) VALUES (?,?,?)";
        jdbcTemplate.update(sql, st.getName(), st.getFileName(), st.getCourse());
        return st;
    }

    public void deleteUserById(Long id){
        String sql = "DELETE FROM Student WHERE id=?";
        jdbcTemplate.update(sql,id);
    }

    public Student updateById(Long id, Student st){
        String sql = "UPDATE Student SET name=?, fileName=?, course=? WHERE id=?";
        jdbcTemplate.update(sql, st.getName(), st.getFileName(), st.getCourse(), id);
        return findById(id);
    }
}