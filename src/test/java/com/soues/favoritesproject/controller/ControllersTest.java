package com.soues.favoritesproject.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soues.favoritesproject.dto.CategoryDefinition;
import com.soues.favoritesproject.dto.CategoryListItem;
import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.persistence.entity.Category;
import com.soues.favoritesproject.persistence.entity.Favorite;
import com.soues.favoritesproject.persistence.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTest {

    @Autowired
    ICategoryRepository repository;

    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testCategoryWith200Status() throws Exception {
        int rand = (int)(Math.random() * 1000) + 1;
        String categoryLabel = "Test category label"+rand;

        CategoryListItem category = createCategoryTest(categoryLabel);
        assertEquals(categoryLabel, category.getLabel());

        categoryLabel = "Test category updated label"+rand;
        assertNotEquals(categoryLabel, category.getLabel());
        MvcResult mvcResult = mvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CategoryDefinition(category.getId(), categoryLabel))))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        category = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CategoryListItem.class);
        assertEquals(categoryLabel, category.getLabel());

        List<CategoryListItem> categoriesWithTest = findCategories();
        CategoryListItem finalCategory = category;
        assertFalse(categoriesWithTest.stream().filter(c -> c.getId() == finalCategory.getId()).toList().isEmpty());

        mvc.perform(delete("/api/category/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().is2xxSuccessful());

        assertTrue(categoriesWithTest.size() > findCategories().size());

    }

    @Test
    void testFavoriteWith200Status() throws Exception {

        String favoriteLink = "https://www.google.com";
        CategoryListItem category = createCategoryTest("test category favorite label");

        FavoriteItem favorite = createFavoriteTest(favoriteLink, category);
        assertEquals(favoriteLink, favorite.getLink());
        assertTrue(favorite.isValidity());

        favoriteLink = "google.com";
        assertNotEquals(favoriteLink, favorite.getLabel());
        MvcResult mvcResult = mvc.perform(post("/api/"+category.getId()+"/favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new FavoriteDefinition(favorite.getId(),null,favoriteLink))))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        favorite = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FavoriteItem.class);
        assertEquals(favoriteLink, favorite.getLink());
        assertFalse(favorite.isValidity());

        List<FavoriteItem> favoriteItemsWithTest = findFavorites();
        FavoriteItem finalFavorite = favorite;
        assertFalse(favoriteItemsWithTest.stream().filter(f -> f.getId().equals(finalFavorite.getId())).toList().isEmpty());

        mvc.perform(delete("/api/favorite/"+favorite.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().is2xxSuccessful());

        assertTrue(favoriteItemsWithTest.size() > findFavorites().size());

        mvc.perform(delete("/api/category/"+category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().is2xxSuccessful());

    }

    private CategoryListItem createCategoryTest(String categoryLabel) throws Exception{
        MvcResult mvcResult = mvc.perform(post("/api/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CategoryDefinition(null,categoryLabel))))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CategoryListItem.class);

    }

    private FavoriteItem createFavoriteTest(String favoriteLink, CategoryListItem category) throws Exception{
        MvcResult mvcResult = mvc.perform(post("/api/"+category.getId()+"/favorite")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new FavoriteDefinition(null,null,favoriteLink))))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), FavoriteItem.class);
    }

    private List<CategoryListItem> findCategories() throws  Exception{
        MvcResult mvcResult = mvc.perform(get("/api/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<CategoryListItem>>(){});

    }

    private List<FavoriteItem> findFavorites() throws  Exception{
        MvcResult mvcResult = mvc.perform(get("/api/favorite")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<FavoriteItem>>(){});

    }

}
