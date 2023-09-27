package com.soues.favoritesproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soues.favoritesproject.persistence.entity.Category;
import com.soues.favoritesproject.persistence.entity.Favorite;
import com.soues.favoritesproject.persistence.repository.ICategoryRepository;
import org.junit.jupiter.api.Test ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest ;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Date;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FavoritesProjectApplicationTests {
	@Autowired
	ICategoryRepository repository;

	@Autowired
	private MockMvc mvc;
	@Autowired
	ObjectMapper objectMapper;

@Test
	void testNewFavoriteWith200Status() throws Exception {
		Category category = this.repository.findById(1L).get();
		Favorite favorite = new Favorite();
		favorite.setDate(new Date());
		favorite.setLabel("Test label");
		favorite.setLink("https://www.google.com");
		favorite.setCategory(category);
			mvc.perform(post("/api/1/favorite")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(favorite)))
				.andExpect(status().is2xxSuccessful());
	}
}
