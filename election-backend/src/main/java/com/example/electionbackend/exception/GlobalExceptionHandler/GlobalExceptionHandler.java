package com.example.electionbackend.exception.GlobalExceptionHandler;

import com.example.electionbackend.exception.*;
import com.example.electionbackend.exception.election.ElectionNotFoundException;
import com.example.electionbackend.exception.post.InvalidPostException;
import com.example.electionbackend.exception.municipality.MunicipalityNotFoundException;
import com.example.electionbackend.exception.pollingStation.PollingStationNotFoundException;
import com.example.electionbackend.exception.post.InvalidSearchQueryException;
import com.example.electionbackend.exception.post.PostNotFoundException;
import com.example.electionbackend.exception.votes.PartyVoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler intercepts and processes custom application exceptions.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EmailAlreadyExistsException.
     * <p>
     * Triggered when a user attempts to register with an email address that is
     * already present in the database.
     *
     * @param ex the thrown EmailAlreadyExistsException
     * @return HTTP 400 Bad Request with a descriptive error message
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailExists(EmailAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Handles UsernameAlreadyExistsException.
     * <p>
     * Triggered when a user attempts to register with a username that already exists.
     *
     * @param ex the thrown UsernameAlreadyExistsException
     * @return HTTP 400 Bad Request with the error message
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameExists(UsernameAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Handles UserNotFoundException.
     * <p>
     * Thrown when a requested user cannot be located in the database.
     *
     * @param ex the thrown UserNotFoundException
     * @return HTTP 404 Not Found with an explanation message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles InvalidSeachQueryException.
     *
     * @param ex - the thrown InvalidSearchQueryException
     * @return - HTTP 400 Bad Request with an explanation message
     */
    @ExceptionHandler(InvalidSearchQueryException.class)
    public ResponseEntity<Object> handleInvalidSearchQuery(InvalidSearchQueryException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<Object> handleInvalidPost(InvalidPostException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Object> handlePostNotFound(PostNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles InvalidSortDirectionException.
     *
     * @param ex - the thrown InvalidSortDirectionException
     * @return - HTTP 400 Bad Request with an explanation message
     */
    @ExceptionHandler(InvalidSortPropertyException.class)
    public ResponseEntity<Object> handleInvalidSortProperty(InvalidSortPropertyException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**
     * Handles MunicipalityNotFoundException.
     *
     * @param ex - the thrown MunicipalityNotFoundException
     * @return - HTTP 404 Not Found with an explanation message
     */
    @ExceptionHandler(MunicipalityNotFoundException.class)
    public ResponseEntity<Object> handleMunicipalityNotFound(MunicipalityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles PollingStationNotFoundException.
     *
     * @param ex - the thrown PollingStationNotFoundException
     * @return - HTTP 404 Not Found with an explanation message
     */
    @ExceptionHandler(PollingStationNotFoundException.class)
    public ResponseEntity<Object> handlePollingStationNotFound(PollingStationNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles ElectionNotFoundException.
     *
     * @param ex - the thrown ElectionNotFoundException
     * @return - HTTP 404 Not Found with an explanation message
     */
    @ExceptionHandler(ElectionNotFoundException.class)
    public ResponseEntity<Object> handleElectionNotFound(ElectionNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**
     * Handles PartyVoteNotFoundException.
     *
     * @param ex - the thrown PartyVoteNotFoundException
     * @return - HTTP 404 Not Found with an explanation message
     */
    @ExceptionHandler(PartyVoteNotFoundException.class)
    public ResponseEntity<Object> handlePartyVoteNotFound(PartyVoteNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
