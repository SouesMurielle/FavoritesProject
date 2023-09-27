package com.soues.favoritesproject.controller;

import com.soues.favoritesproject.dto.CategoryDefinition;
import com.soues.favoritesproject.dto.CategoryListItem;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.service.ICategoryService;
import com.soues.favoritesproject.service.IFavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// indique que c'est un controller. Cette classe doit etre géré par Spring et elle exposera des services
@RequestMapping(path = "/api/category")
// sert à indiquer l'adresse de contact, à quelle adresse le service est appelable/joignable c'est le n point du service
// associe toutes les requetes xxxMapping arrivent ici
@CrossOrigin(origins = {"http://localhost:8080", "127.0.0.1:8080"})
// indique les adresses+port où se connecter
public class CategoriesController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IFavoriteService favoriteService;

    @GetMapping
    List<CategoryListItem> findAll() {
        return categoryService.findAll();
    }

    @GetMapping(path = "/{category}")
    List<FavoriteItem> findByCategory(@PathVariable(name = "category") long id) {
        return favoriteService.findByCategory(id);
    }

    @PostMapping
    CategoryListItem save(@RequestBody CategoryDefinition category) {
        return categoryService.save(category);
    }

    @Operation(summary = "Delete a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The category was deleted"),
            @ApiResponse(responseCode = "404", description = "The category was not found"),
            @ApiResponse(responseCode = "500", description = "The category could not be deleted")
    })
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable long id) {
        categoryService.delete(id);
    }

}
