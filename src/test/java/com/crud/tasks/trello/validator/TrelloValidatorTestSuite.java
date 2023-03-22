package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrelloValidatorTestSuite {

    private final TrelloValidator trelloValidator = new TrelloValidator();

    @Test
    void shouldValidateCard() {
        //Given & When & then
        assertTrue(trelloValidator.validateCard(new TrelloCard("test", "test", "testPos", "testList")));
        assertTrue(trelloValidator.validateCard(new TrelloCard("atest", "test", "testPos", "testList")));
        assertFalse(trelloValidator.validateCard(new TrelloCard("TEST", "test", "testPos", "testList")));
        assertFalse(trelloValidator.validateCard(new TrelloCard("TESTED", "test", "testPos", "testList")));
    }

    @Test
    void shouldReturnFilteredTrelloBoardList() {
        //Given
        List<TrelloBoard> trelloBoards = List.of(
                new TrelloBoard("1", "test", null),
                new TrelloBoard("2", "TEST", null),
                new TrelloBoard("3", "teST", null),
                new TrelloBoard("4", "TESTTEST", null),
                new TrelloBoard("5", "atest", null),
                new TrelloBoard("6", "tested", null),
                new TrelloBoard("7", "seven", null)
        );

        //When
        List<TrelloBoard> results = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(4, results.size());
        assertEquals("TESTTEST", results.get(0).getName());
        assertEquals("atest", results.get(1).getName());
        assertEquals("tested", results.get(2).getName());
        assertEquals("seven", results.get(3).getName());
    }

    @Test
    void shouldReturnEmptyFilteredTrelloBoardList() {
        //Given
        List<TrelloBoard> trelloBoards = List.of(
                new TrelloBoard("1", "test", null),
                new TrelloBoard("2", "TEST", null)
        );

        //When
        List<TrelloBoard> results = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(0, results.size());
    }
}