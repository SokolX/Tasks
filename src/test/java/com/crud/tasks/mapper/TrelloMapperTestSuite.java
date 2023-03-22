package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTestSuite {

    private final TrelloMapper trelloMapper = new TrelloMapper();

    @Test
    void shouldReturnBoardsFromMap() {
        //Given
        List<TrelloListDto> trelloListDtos = List.of(
                new TrelloListDto("1", "first", false),
                new TrelloListDto("2", "second", false),
                new TrelloListDto("3", "third", false)
        );
        List<TrelloBoardDto> trelloBoardDtos = List.of(new TrelloBoardDto("1", "boards", trelloListDtos));

        //When
        List<TrelloBoard> results = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        assertThat(results).isNotNull();
        assertEquals(1, results.size());
        assertEquals("boards", results.get(0).getName());
        assertEquals(3, results.get(0).getLists().size());
        assertEquals("third", results.get(0).getLists().get(2).getName());
    }

    @Test
    void shouldReturnBoardsDtoFromMap() {
        //Given
        TrelloList trelloList1 = new TrelloList("1", "first", false);
        TrelloList trelloList2 = new TrelloList("1", "second", false);
        List<TrelloBoard> trelloBoards = List.of(
                new TrelloBoard("1", "boards", List.of(trelloList1, trelloList2)),
                new TrelloBoard("2", "boards2", List.of(trelloList1, trelloList2))
        );

        //When
        List<TrelloBoardDto> results = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        assertThat(results).isNotNull();
        assertEquals(2, results.size());
        assertEquals("boards", results.get(0).getName());
        assertEquals(2, results.get(0).getLists().size());
        assertEquals("second", results.get(0).getLists().get(1).getName());
    }

    @Test
    void shouldReturnListFromMap() {
        //Given
        List<TrelloListDto> trelloListDtos = List.of(
                new TrelloListDto("1", "first", false),
                new TrelloListDto("2", "second", false),
                new TrelloListDto("3", "third", false)
        );

        //When
        List<TrelloList> results = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertThat(results).isNotNull();
        assertEquals(3, results.size());
        assertEquals("2", results.get(1).getId());
        assertEquals("second", results.get(1).getName());
        assertFalse(results.get(0).isClosed());
    }

    @Test
    void shouldReturnListDtoFromMap() {
        //Given
        List<TrelloList> trelloLists = List.of(
                new TrelloList("1", "first", false),
                new TrelloList("2", "second", false),
                new TrelloList("3", "third", true)
        );

        //When
        List<TrelloListDto> results = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertThat(results).isNotNull();
        assertEquals(3, results.size());
        assertEquals("3", results.get(2).getId());
        assertEquals("third", results.get(2).getName());
        assertTrue(results.get(2).isClosed());
    }

    @Test
    void shouldReturnCardDtoFromCard() {
        //Given
        TrelloCard trelloCard = new TrelloCard("trelloCard1", "trello card 1 description", "pos1", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("trelloCard1", trelloCardDto.getName());
        assertEquals("trello card 1 description", trelloCardDto.getDescription());
        assertEquals("pos1", trelloCardDto.getPos());
        assertEquals("1", trelloCardDto.getListId());
    }

    @Test
    void shouldReturnCardFromCardDto() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("trelloCard1", "trello card 1 description", "pos1", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("trelloCard1", trelloCard.getName());
        assertEquals("trello card 1 description", trelloCard.getDescription());
        assertEquals("pos1", trelloCard.getPos());
        assertEquals("1", trelloCard.getListId());
    }
}