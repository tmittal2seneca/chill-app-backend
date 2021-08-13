package com.example.assignment2.controllers;

import com.example.assignment2.CustomizedResponse;
import com.example.assignment2.models.MovieAndTVShow;
import com.example.assignment2.services.MovieAndTVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MovieAndTVShowController {

    @Autowired //dependency injection
    private MovieAndTVShowService service;

    //get all movies
    @GetMapping("/movieandtvshows/movies")
    public ResponseEntity getAllMovies()
    {
        var customizedResponse = new CustomizedResponse("A list of all the movies", service.getAllMovies());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //get all tv-shows
    @GetMapping("/movieandtvshows/tvshows")
    public ResponseEntity getAllTVShows()
    {
        var customizedResponse = new CustomizedResponse("A list of all the TV shows", service.getAllTVShows());
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //get movies based on title
    @GetMapping("/movieandtvshows/fmovies")
    public ResponseEntity getMoviesByTitle(@RequestParam(value="titlename") String title)
    {
        var customizedResponse = new CustomizedResponse
                ("A list of all the Movies based on title",
                        service.getMoviesByTitle(title));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //get tv shows based on title
    @GetMapping("/movieandtvshows/ftvshows")
    public ResponseEntity getTVShowsByTitle(@RequestParam(value="titlename") String title)
    {
        var customizedResponse = new CustomizedResponse
                ("A list of all the TV Shows based on title",
                        service.getTVShowsByTitle(title));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //get all featured movies
    @GetMapping("/movieandtvshows/featured-movies")
    public ResponseEntity getFeaturedMovies(@RequestParam(value="featured") Boolean isFeatured)
    {
        var customizedResponse = new CustomizedResponse
                ("A list of all the featured movies",
                        service.getFeaturedMovies(isFeatured));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //get all featured TV shows
    @GetMapping("/movieandtvshows/featured-tvshows")
    public ResponseEntity getFeaturedTVShows(@RequestParam(value="featured") Boolean isFeatured)
    {
        var customizedResponse = new CustomizedResponse
                ("A list of all the featured TV shows",
                        service.getFeaturedTVShows(isFeatured));
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //return a movie or tv-show based on ID
    @GetMapping("/movieandtvshows/{id}")
    public ResponseEntity getMovieOrTVShow(@PathVariable("id") String id){
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Movie/TV show with id " + id,
                    Collections.singletonList(service.getMovieOrTVShow(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND); //Error 404
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //create a movie or tv show
    @PostMapping(value="/movieandtvshows", consumes = {   //consumes is when data comes in from request body
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity addMovieAndTvShow(@RequestBody MovieAndTVShow movieandtvshow)
    {
        service.addMovieAndTVShow(movieandtvshow);
        return new ResponseEntity(movieandtvshow, HttpStatus.OK);
    }

    //update existing movie or tv show
    @PutMapping(value="/movieandtvshows/{id}", consumes = {   //consumes is when data comes in from request body
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity editMovieOrTvShow(@PathVariable("id") String id,
                                            @RequestBody MovieAndTVShow newMovieOrtvshow)
    {
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Movie/TV Show with ID: " + id + " was updated successfully",
                    Collections.singletonList(service.editMovieOrTvShow(id, newMovieOrtvshow)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND); //Error 404
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }

    //delete a movie or tv show based on ID
    @DeleteMapping("/movieandtvshows/{id}")
    public ResponseEntity deleteAMovieOrTvShow(@PathVariable("id") String id)
    {

        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse("Deleted Movie/TV show with id " + id,
                    Collections.singletonList((service.deleteAMovieOrTvShow(id))));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND); //Error 404
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }
}
