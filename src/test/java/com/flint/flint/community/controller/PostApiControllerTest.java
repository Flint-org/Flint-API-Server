package com.flint.flint.community.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.flint.flint.community.domain.board.Board;
import com.flint.flint.community.dto.request.PostRequest;
import com.flint.flint.community.repository.BoardRepository;
import com.flint.flint.community.spec.BoardType;
import com.flint.flint.custom_member.WithMockCustomMember;
import com.flint.flint.member.domain.main.Member;
import com.flint.flint.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/posts";

    @Test
    @DisplayName("학교 인증을 받지 않은 회원은 게시글 생성 시 예외가 발생한다.")
    @WithMockCustomMember(role = "ROLE_ANAUTHUSER")
    void createPostWithoutCredential() throws Exception {
        Board board = Board.builder()
                .boardType(BoardType.GENERAL)
                .generalBoardName("자유게시판")
                .build();

        Board savedBoard = boardRepository.save(board);
        List<String> fileNames = new ArrayList<>();
        fileNames.add("abcd.jpg");
        fileNames.add("efgh.jpg");
        fileNames.add("ijkl.jpg");

        PostRequest postRequest = PostRequest.builder()
                .title("제목입니다")
                .contents("게시글을 생성하는 테스트 코드입니다")
                .fileNames(fileNames)
                .boardId(savedBoard.getId())
                .build();

        String json = convertDtoToJson(postRequest);

        this.mockMvc.perform(post(BASE_URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("학교 인증을 받은 회원은 게시글 생성에 성공한다.")
    @WithMockCustomMember
    void createPost() throws Exception {
        Board board = Board.builder()
                .boardType(BoardType.GENERAL)
                .generalBoardName("자유게시판")
                .build();

        Board savedBoard = boardRepository.save(board);

        Member member = Member.builder()
                .name("테스터")
                .email("test@test.com")
                .providerName("kakao")
                .providerId("test")
                .build();

        memberRepository.save(member);

        List<String> fileNames = new ArrayList<>();
        fileNames.add("abcd.jpg");
        fileNames.add("efgh.jpg");
        fileNames.add("ijkl.jpg");

        PostRequest postRequest = PostRequest.builder()
                .title("제목입니다")
                .contents("게시글을 생성하는 테스트 코드입니다")
                .fileNames(fileNames)
                .boardId(savedBoard.getId())
                .build();

        String json = convertDtoToJson(postRequest);

        this.mockMvc.perform(post(BASE_URL)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String convertDtoToJson(Object obj) throws JsonProcessingException {
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(obj);
    }
}