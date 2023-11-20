package edu.tcu.cs.easybites.system.exception;

import edu.tcu.cs.easybites.recipe.Recipe;
import edu.tcu.cs.easybites.recipe.RecipeNotFoundException;
import edu.tcu.cs.easybites.system.Result;
import edu.tcu.cs.easybites.system.StatusCode;
import jdk.jshell.Snippet;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleRecipeNotFoundException(RecipeNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

}
