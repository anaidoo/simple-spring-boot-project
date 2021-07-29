package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;

@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

	private static final String BOOK_NAME = "Emma";
	private static final LocalDate PUBLISH_DATE = LocalDate.of(1815, Month.DECEMBER, 23);
	private static final int ID = 1;
	private static final String AUTHOR_NAME = "Jane Austen";
	private static final String ISBN = "ISBN";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private AuthorRepository authorRepository;

	@Test
	@DisplayName("Given an id, when a get book by id is called, a book is returned.")
	void testGetBookById() throws Exception {
		Book book = createBook();
		when(bookRepository.findById(ID)).thenReturn(Optional.of(book));
		mockMvc.perform(get("/book/" + ID)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("name", is(BOOK_NAME)))
				.andExpect(jsonPath("publishDate", is(PUBLISH_DATE.toString())))
				.andExpect(jsonPath("isbn", is(ISBN)))
				.andExpect(jsonPath("authors[0].name", is(AUTHOR_NAME)));
	}

	@Test
	@DisplayName("Given an id, when a get book by id is called, no book is found")
	void testGetBookByIdEmpty() throws Exception {
		when(bookRepository.findById(ID)).thenReturn(Optional.empty());
		mockMvc.perform(get("/book/" + ID)).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("When get all books, a book is returned")
	void testGetBooks() throws Exception {
		Book book = createBook();
		when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
		mockMvc.perform(get("/books")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(BOOK_NAME)))
				.andExpect(jsonPath("$[0].publishDate", is(PUBLISH_DATE.toString())))
				.andExpect(jsonPath("$[0].isbn", is(ISBN)))
				.andExpect(jsonPath("$[0].authors[0].name", is(AUTHOR_NAME)));
	}
	
	@Test
	@DisplayName("Given an id, when a get author by id is called, an author is returned.")
	void testGetAuthorById() throws Exception {
		Author author = createAuthor();
		when(authorRepository.findById(ID)).thenReturn(Optional.of(author));
		mockMvc.perform(get("/author/" + ID)).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("name", is(AUTHOR_NAME)))
			.andExpect(jsonPath("books[0].publishDate", is(PUBLISH_DATE.toString())))
			.andExpect(jsonPath("books[0].isbn", is(ISBN)))
			.andExpect(jsonPath("books[0].name", is(BOOK_NAME)));
	}

	@Test
	@DisplayName("Given an id, when a get author by id is called, no author is found")
	void testGetAuthorByIdEmpty() throws Exception {
		when(authorRepository.findById(ID)).thenReturn(Optional.empty());
		mockMvc.perform(get("/author/" + ID)).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("When get all authors, an author is returned")
	void testGetAuthors() throws Exception {
		Author author = createAuthor();
		when(authorRepository.findAll()).thenReturn(Arrays.asList(author));
		mockMvc.perform(get("/authors")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(AUTHOR_NAME)))
				.andExpect(jsonPath("$[0].books[0].publishDate", is(PUBLISH_DATE.toString())))
				.andExpect(jsonPath("$[0].books[0].isbn", is(ISBN)))
				.andExpect(jsonPath("$[0].books[0].name", is(BOOK_NAME)));
	}

	private Book createBook() {
		return Book.builder()
				.id(ID)
				.name(BOOK_NAME)
				.publishDate(PUBLISH_DATE)
				.isbn(ISBN)
				.author(Author.builder().name(AUTHOR_NAME).build())
				.build();
	}

	private Author createAuthor() {
		return Author.builder()
				.id(ID)
				.name(AUTHOR_NAME)
				.book(Book.builder().id(ID).name(BOOK_NAME)
						.publishDate(PUBLISH_DATE).isbn(ISBN)
						.build())
				.build();
	}

}
