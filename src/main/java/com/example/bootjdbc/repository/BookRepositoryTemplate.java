package com.example.bootjdbc.repository;

import com.example.bootjdbc.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryTemplate implements BookRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Book> findAllBooks() {
        String sql = "SELECT * FROM books";
        try {
            return jdbcTemplate.query(sql, new BookRowMapper());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve all books",e);
        }

    }

    public Book findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new BookRowMapper(), id);
        }catch (Exception e) {
            throw new RuntimeException("Failed to find book by id: " + id,e);
        }

    }

    public Book save (Book book) {
        String sql = "INSERT INTO books (title,author, publicationYear) VALUES(?,?,?)";
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getAuthor());
                ps.setInt(3,book.getPublicationYear());
                return ps;
            }, keyHolder);
            book.setId(keyHolder.getKey().longValue());
            return book;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save book", e);
        }

    }

    public Book updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, publicationYear = ? WHERE id = ?";
        try {
            jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());
            return book;
        } catch (Exception e) {
            throw new RuntimeException("Failed to updated book with id:" + book.getId(), e);
        }

    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete book with id:" + id, e);
        }

    }


    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("publicationYear")
            );
        }
    }
}
