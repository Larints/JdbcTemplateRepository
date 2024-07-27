import com.example.bootjdbc.BootJdbcApplication;
import com.example.bootjdbc.controller.BookStoreController;
import com.example.bootjdbc.service.BookStore;
import com.example.bootjdbc.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.awt.*;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookStoreController.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BootJdbcApplication.class)
public class BookStoreServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookStore bookStoreService;

    private Book book;

    @BeforeEach
    public void setUp() {
        book = Book.builder()
                .id(1L)
                .title("TestBook")
                .author("TestAuthor")
                .publicationYear(2024)
                .build();
    }

    @Test
    public void shouldReturnAllBooks_whenNoFiltersApplied() throws Exception {
        when(bookStoreService.findAllBooks()).thenReturn(List.of(book));
        mockMvc.perform(get("/api/v1/bookstore")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("TestBook"))
                .andExpect(jsonPath("$[0].author").value("TestAuthor"))
                .andExpect(jsonPath("$[0].publicationYear").value(2024));
    }

    @Test
    public void shouldReturnBookById_whenIdExists() throws Exception {
        when(bookStoreService.findById(1L)).thenReturn(book);
        mockMvc.perform(get("/api/v1/bookstore/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("TestBook"))
                .andExpect(jsonPath("$.author").value("TestAuthor"))
                .andExpect(jsonPath("$.publicationYear").value(2024));
    }

    @Test
    public void shouldSaveBook_whenBookIsValid() throws Exception {
        when(bookStoreService.save(any(Book.class))).thenReturn(book);
        mockMvc.perform(post("/api/v1/bookstore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"TestBook\", \"author\": \"TestAuthor\", \"publicationYear\": 2024}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("TestBook"))
                .andExpect(jsonPath("$.author").value("TestAuthor"))
                .andExpect(jsonPath("$.publicationYear").value(2024));
    }

    @Test
    public void shouldUpdateBook_whenBookIsValid() throws Exception {
        when(bookStoreService.updateBook(any(Book.class))).thenReturn(book);
        mockMvc.perform(put("/api/v1/bookstore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"title\": \"UpdatedBook\", \"author\": \"UpdatedAuthor\", \"publicationYear\": 2025}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("TestBook"))
                .andExpect(jsonPath("$.author").value("TestAuthor"))
                .andExpect(jsonPath("$.publicationYear").value(2024));
    }

    @Test
    public void shouldDeleteBook_whenIdExists() throws Exception {
        doNothing().when(bookStoreService).deleteById(1L);
        mockMvc.perform(delete("/api/v1/bookstore/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
