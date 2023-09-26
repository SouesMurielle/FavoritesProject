package com.soues.favoritesproject.controller;

import com.soues.favoritesproject.dto.FavoriteDefinition;
import com.soues.favoritesproject.dto.FavoriteItem;
import com.soues.favoritesproject.dto.SortParam;
import com.soues.favoritesproject.service.IFavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = {"http://localhost:8080", "127.0.0.1:8080"})
public class FavoritesController {

    private final IFavoriteService favoriteService;

    public FavoritesController(IFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @GetMapping(path = "/favorite")
    List<FavoriteItem> findAll() {
        return favoriteService.findAll();
    }

    @GetMapping(path = "/{id}")
    FavoriteItem findOne(@PathVariable long id) {
        return favoriteService.findOne(id);
    }


    @GetMapping(path = "/date/{sortParam}")
    List<FavoriteItem> findAllOrderByDate(@PathVariable(name = "sortParam") SortParam sortParam) {
        return favoriteService.findAllByOrderByDate(sortParam);
    }

    @PostMapping(path = "/{categoryId}/favorite")
    FavoriteItem save(@RequestBody FavoriteDefinition favorite, @PathVariable(name = "categoryId") long categoryId) {
        return favoriteService.save(favorite, categoryId);
    }

    @DeleteMapping(path = "/favorite/{ids}")
    @ResponseStatus(code = HttpStatus.OK)
    void delete(@PathVariable String ids) {
        favoriteService.deleteMultiple(Arrays.stream(ids.split("-")).map(Long::valueOf).toList());
    }

}
