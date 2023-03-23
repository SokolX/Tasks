package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrelloFacadeTestSuite {

    @InjectMocks
    private TrelloFacade trelloFacade;
    @Mock
    private TrelloService trelloService;
    @Mock
    private TrelloValidator trelloValidator;
    @Mock
    private TrelloMapper trelloMapper;

    @Test
    void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDtos = List.of(new TrelloListDto("1", "test-list", false));
        List<TrelloBoardDto> trelloBoardDtos = List.of(new TrelloBoardDto("1", "test", trelloListDtos));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1", "test-list", false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        //When
        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        when(trelloMapper.mapToBoards(trelloBoardDtos)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoardDtos);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        List<TrelloBoardDto> result = trelloFacade.fetchTrelloBoards();

        //Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);

        result.forEach(trelloBoardDto -> {
            assertThat(trelloBoardDto.getId()).isEqualTo("1");
            assertThat(trelloBoardDto.getName()).isEqualTo("test");

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertThat(trelloListDto.getId()).isEqualTo("1");
                assertThat(trelloListDto.getName()).isEqualTo("test-list");
                assertThat(trelloListDto.isClosed()).isFalse();
            });
        });
    }

    @Test
    void shouldFetchEmptyList() {
        // Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "test_list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("1", "test", trelloLists));
        List<TrelloList> mappedTrelloLists = List.of(new TrelloList("1", "test_list", false));
        List<TrelloBoard> mappedTrelloBoards = List.of(new TrelloBoard("1", "test", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(List.of());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(List.of());

        // When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();

        // Then
        assertNotNull(trelloBoardDtos);
        assertEquals(0, trelloBoardDtos.size());
    }

    @Test
    void shouldCreateNewCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("TrelloCardDtoName", "TrelloCardDtoDescription", "TrelloCardDtoPos", "TrelloCardDtoListId");
        TrelloCard trelloCard = new TrelloCard("TrelloCardDtoName", "TrelloCardDtoDescription", "TrelloCardDtoPos", "TrelloCardDtoListId");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("id", "name", "url.com", null);
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloValidator.validateCard(trelloCard)).thenReturn(false);
        when(trelloService.createTrelloCard(trelloMapper.mapToCardDto(trelloCard))).thenReturn(createdTrelloCardDto);

        // When
        CreatedTrelloCardDto result = trelloFacade.createCard(trelloCardDto);

        // Then
        assertEquals("id", result.getId());
        assertEquals("name", result.getName());
        assertEquals("url.com", result.getShortUrl());
    }
}