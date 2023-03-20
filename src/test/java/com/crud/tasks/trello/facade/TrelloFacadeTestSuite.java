package com.crud.tasks.trello.facade;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
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
}