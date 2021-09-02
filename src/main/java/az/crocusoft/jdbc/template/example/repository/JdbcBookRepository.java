package az.crocusoft.jdbc.template.example.repository;

import az.crocusoft.jdbc.template.example.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public int save(Book book) {
        return jdbcTemplate.update("insert into books (name,price) values (?,?)",
                book.getName(), book.getPrice());
    }

    @Override
    public int update(Book book) {
        return jdbcTemplate.update("update books set price = ? where id = ?",
                book.getPrice(), book.getId());
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("delete books where id = ?", id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("select * from books",
                (rs, rowNum) ->
                        new Book(
                                rs.getLong("id")
                                , rs.getString("name")
                                , rs.getBigDecimal("price")
                        ));
    }

    @Override
    public List<Book> findByNameAndPrice(String name, BigDecimal price) {
        return jdbcTemplate.query("select * from books where name = ? and price = ?",
                new Object[]{name,price},
                (rs, rowNum) ->
                        new Book(
                                rs.getLong("id")
                                , rs.getString("name")
                                , rs.getBigDecimal("price")
                        ));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jdbcTemplate.queryForObject("select * from books where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Book(
                                rs.getLong("id")
                                , rs.getString("name")
                                , rs.getBigDecimal("price"))));
    }

    @Override
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject("select name from books where id = ?",
                new Object[]{id},
                String.class);
    }

//    public void runJdbc(){
//        jdbcTemplate.execute("DROP TABLE books if EXISTS ");
//        jdbcTemplate.execute("create table books(id Number, name varchar2(20), price Number(15,2))");
//
//        List<Book> books = Arrays.asList(
//                Book.builder()
//                        .name("test")
//                        .price(new BigDecimal("12.12"))
//                        .build(),
//                Book.builder()
//                        .name("adas")
//                        .price(new BigDecimal("12.12"))
//                        .build()
//        );
//    }
}
