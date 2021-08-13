package com.example.assignment2.services;

import com.example.assignment2.models.MovieAndTVShow;
import com.example.assignment2.models.MovieAndTVShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class MovieAndTVShowService {

    @Autowired  //dependency injection
    private MovieAndTVShowRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate; //goes beyond CRUD operations to perform advance queries.

    //get all movies
    public List<MovieAndTVShow> getAllMovies(){
        Query query = new Query();
        query.addCriteria(Criteria.where("isMovie").is(true));
        List<MovieAndTVShow> movies = mongoTemplate.find(query, MovieAndTVShow.class);
        return movies;
    }

    //get all tv-shows
    public List<MovieAndTVShow> getAllTVShows(){
        Query query = new Query();
        query.addCriteria(Criteria.where("isMovie").is(false));
        List<MovieAndTVShow> tvShows = mongoTemplate.find(query, MovieAndTVShow.class);
        return tvShows;
    }

    //get movies by title
    public List<MovieAndTVShow> getMoviesByTitle(String title){
        Pattern titlePattern = Pattern.compile("(?i)^.*" + title.toLowerCase() + ".*$(?-i).*");
        Query query = new Query();
        //query.addCriteria(Criteria.where("name").is("true"));
       query.addCriteria(Criteria.where("name").regex(titlePattern)).
       addCriteria(Criteria.where("isMovie").is(true));
        List<MovieAndTVShow> movieAndTVShows = mongoTemplate.find(query, MovieAndTVShow.class);
        return movieAndTVShows;
    }

    //get movies by title
    public List<MovieAndTVShow> getTVShowsByTitle(String title){
        Pattern titlePattern = Pattern.compile("(?i)^.*" + title.toLowerCase() + ".*$(?-i).*");
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(titlePattern)).
                addCriteria(Criteria.where("isMovie").is(false));
        List<MovieAndTVShow> movieAndTVShows = mongoTemplate.find(query, MovieAndTVShow.class);
        return movieAndTVShows;
    }

    //get all featured movies
    public List<MovieAndTVShow> getFeaturedMovies(Boolean isFeatured){
        Query query = new Query();
        query.addCriteria(Criteria.where("isFeatured").is(isFeatured)).
                addCriteria(Criteria.where("isMovie").is(true)).
                limit(6);
        List<MovieAndTVShow> featuredMovies = mongoTemplate.find(query, MovieAndTVShow.class);
        return featuredMovies;
    }

    //get all featured tv shows
    public List<MovieAndTVShow> getFeaturedTVShows(Boolean isFeatured){
        Query query = new Query();
        query.addCriteria(Criteria.where("isFeatured").is(isFeatured)).
                addCriteria(Criteria.where("isMovie").is(false)).
                limit(6);
        List<MovieAndTVShow> featuredTVShows = mongoTemplate.find(query, MovieAndTVShow.class);
        return featuredTVShows;
    }

    //return a movie or tv-show based on ID
    public Optional<MovieAndTVShow> getMovieOrTVShow(String id) throws Exception {
        //validation
        Optional<MovieAndTVShow> movieOrTVshow = repository.findById(id);
        if(!movieOrTVshow.isPresent())
        {
            throw new Exception("No movie or TV show found with id: " + id);
        }
        return movieOrTVshow;
    }

    //create a movie or tv show
    public void addMovieAndTVShow(MovieAndTVShow movieandtvshow){
        repository.insert(movieandtvshow);
    }

    public MovieAndTVShow editMovieOrTvShow(String id, MovieAndTVShow newMovieOrtvshow) throws Exception {
        // get the resource based on the ID
        Optional<MovieAndTVShow> movieOrtvshow = repository.findById(id);
        // validate the id
        if(!movieOrtvshow.isPresent())
        {
            throw new Exception("No movie or TV show found with id: " + id);
        }
        //update the found resource with the new data
        movieOrtvshow.get().setName(newMovieOrtvshow.getName());  // with optional type, always use get() in order to get access to setter methods
        movieOrtvshow.get().setPrice(newMovieOrtvshow.getPrice());
        movieOrtvshow.get().setSynopsis(newMovieOrtvshow.getSynopsis());
        movieOrtvshow.get().setMovie(newMovieOrtvshow.isMovie());
        movieOrtvshow.get().setSmallPosterImgPath(newMovieOrtvshow.getSmallPosterImgPath());
        movieOrtvshow.get().setLargePosterImgPath(newMovieOrtvshow.getLargePosterImgPath());
        movieOrtvshow.get().setRentPrice(newMovieOrtvshow.getRentPrice());
        movieOrtvshow.get().setOutrightPurchasePrice(newMovieOrtvshow.getOutrightPurchasePrice());
        movieOrtvshow.get().setFeatured(newMovieOrtvshow.isFeatured());

        //commit the updated by saving it
        MovieAndTVShow updatedMovieorTVShow = repository.save(movieOrtvshow.get());
        return updatedMovieorTVShow;
    }

    public Optional<MovieAndTVShow> deleteAMovieOrTvShow(String id) throws Exception
    {
        Optional<MovieAndTVShow> movieOrTVshow = repository.findById(id);
        if(!movieOrTVshow.isPresent())
        {
            throw new Exception("No movie or TV show found with id: " + id);
        }
        repository.deleteById(id);
        return movieOrTVshow;
    }
}
