package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloListDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTestSuite {

    private TrelloMapper trelloMapper = new TrelloMapper();

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
}