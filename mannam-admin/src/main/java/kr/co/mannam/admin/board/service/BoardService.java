package kr.co.mannam.admin.board.service;

import kr.co.mannam.admin.board.dto.BoardDTO;
import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.board.LikeBoard;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.repository.board.BoardRepository;
import kr.co.mannam.domain.repository.board.CommentRepository;
import kr.co.mannam.domain.repository.board.LikeBoardRepository;
import kr.co.mannam.domain.repository.member.UserRepository;
import kr.co.mannam.type.board.BoardCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// DTO -> Entity (Entity Class)
// Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final LikeBoardRepository likeBoardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public void save(BoardDTO boardDTO) throws IOException {
        BoardEntity boardEntity = boardDTO.toEntity();
        boardRepository.save(boardEntity);
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity: boardEntityList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .id(boardEntity.getId())
                    .boardWriter(boardEntity.getBoardWriter())
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardContents(boardEntity.getBoardContents())
                    .boardHits(boardEntity.getBoardHits())
                    .likeCount(boardEntity.getLikeCount())
                    .boardCreatedTime(boardEntity.getCreatedTime())
                    .boardUpdatedTime(boardEntity.getUpdatedTime())
                    .user(boardEntity.getUser())
                    .boardCategory(boardEntity.getBoardCategory())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.builder()
                    .id(boardEntity.getId())
                    .boardWriter(boardEntity.getBoardWriter())
                    .boardTitle(boardEntity.getBoardTitle())
                    .boardContents(boardEntity.getBoardContents())
                    .boardHits(boardEntity.getBoardHits())
                    .likeCount(boardEntity.getLikeCount())
                    .commentCount(boardEntity.getCommentCount())  // 댓글 수 추가
                    .boardCreatedTime(boardEntity.getCreatedTime())
                    .boardUpdatedTime(boardEntity.getUpdatedTime())
                    .user(boardEntity.getUser())
                    .boardCategory(boardEntity.getBoardCategory())
                    .build();
            return boardDTO;
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardDTO.toEntity();
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable, BoardCategory category) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작



        Page<BoardEntity> boardEntities = null;

        if (category == BoardCategory.Hit) {
            boardEntities = boardRepository.findByHit(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }else {
            boardEntities = boardRepository.findByBoardCategory(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")), category);
        }


//        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
//        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
//        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
//        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
//        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
//        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
//        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
//        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> BoardDTO.builder()
                .id(board.getId())
                .boardWriter(board.getBoardWriter())
                .boardTitle(board.getBoardTitle())
                .boardContents(board.getBoardContents())
                .boardHits(board.getBoardHits())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())  // 댓글 수 추가
                .boardCreatedTime(board.getCreatedTime())
                .boardUpdatedTime(board.getUpdatedTime())
                .user(board.getUser())
                .boardCategory(board.getBoardCategory())
                .build());

        return boardDTOS;
    }

    @Transactional
    public Page<BoardDTO> search(String keyword, Pageable pageable, BoardCategory category, String type) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작

        Page<BoardEntity> boardEntities = null;
        System.out.println("type = " + type);

        if (category == BoardCategory.Hit) {
            if("title".equals(type)) {
                boardEntities =
                        boardRepository.findByBoardTitleContainingAndHit(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
            else if("content".equals(type)) {
                boardEntities =
                        boardRepository.findByBoardContentsContainingAndHit(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
            else if("writer".equals(type)) {
                boardEntities =
                        boardRepository.findByBoardWriterContainingAndHit(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }else{
                boardEntities =
                        boardRepository.findByHit(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
        }else {
            if("title".equals(type)) {
                boardEntities =
                        boardRepository.findByBoardTitleContainingAndBoardCategory(keyword, category, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
                System.out.println("findByBoardTitleContainingAndBoardCategory = " + boardEntities);
            }
            else if("content".equals(type)) {
                boardEntities =
                        boardRepository.findByBoardContentsContainingAndBoardCategory(keyword, category, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
                System.out.println("findByBoardContentsContainingAndBoardCategory = " + boardEntities);
            }
            else if("writer".equals(type)) {
                boardEntities =
                        boardRepository.findByBoardWriterContainingAndBoardCategory(keyword, category, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
                System.out.println("findByBoardWriterContainingAndBoardCategory = " + boardEntities);
            }else{
                boardEntities =
                boardRepository.findByBoardCategory(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")), category);
                System.out.println("findByBoardCategory = " + boardEntities);
            }
        }

        System.out.println("boardEntities222 = " + boardEntities);

//        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
//        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
//        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
//        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
//        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
//        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
//        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
//        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> BoardDTO.builder()
                .id(board.getId())
                .boardWriter(board.getBoardWriter())
                .boardTitle(board.getBoardTitle())
                .boardContents(board.getBoardContents())
                .boardHits(board.getBoardHits())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())  // 댓글 수 추가
                .boardCreatedTime(board.getCreatedTime())
                .boardUpdatedTime(board.getUpdatedTime())
                .user(board.getUser())
                .boardCategory(board.getBoardCategory())
                .build());

        return boardDTOS;

    }


    public boolean findLike(Long boardId, String userId) {
        return likeBoardRepository.existsByBoard_IdAndUser_Id(boardId, userId);
    }

    @Transactional
    public boolean saveLike(Long boardId, String userId) {

        // 로그인한 유저가 해당 게시물을 좋아요 했는지 확인
        if (!findLike(boardId, userId)) {
            // 좋아요 하지 않은 게시물이면 좋아요 추가, true 리턴
            BoardEntity findBoard = boardRepository.findById(boardId).get();
            User findUser = userRepository.findById(userId).get();

            LikeBoard likeBoard = LikeBoard.builder()
                    .board(findBoard)
                    .user(findUser)
                    .build();

            likeBoardRepository.save(likeBoard);
            boardRepository.plusLike(boardId);

            return true;
        } else {
            // 좋아요한 게시물이면 좋아요 삭제, false 리턴
            likeBoardRepository.deleteByBoard_IdAndUser_Id(boardId, userId);
            boardRepository.minusLike(boardId);

            return false;
        }
    }

    public Page<BoardDTO> search22(String keyword, String type,Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작

        Page<BoardEntity> boardEntities = null;
        System.out.println("type = " + type);

        if("title".equals(type)) {
            boardEntities =
                    boardRepository.findByBoardTitleContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            System.out.println("findByBoardTitleContainingAndBoardCategory = " + boardEntities);
        }
        else if("content".equals(type)) {
            boardEntities =
                    boardRepository.findByBoardContentsContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            System.out.println("findByBoardContentsContainingAndBoardCategory = " + boardEntities);
        }
        else if("writer".equals(type)) {
            boardEntities =
                    boardRepository.findByBoardWriterContaining(keyword, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            System.out.println("findByBoardWriterContainingAndBoardCategory = " + boardEntities);
        }


        Page<BoardDTO> boardDTOS = boardEntities.map(board -> BoardDTO.builder()
                .id(board.getId())
                .boardWriter(board.getBoardWriter())
                .boardTitle(board.getBoardTitle())
                .boardContents(board.getBoardContents())
                .boardHits(board.getBoardHits())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())  // 댓글 수 추가
                .boardCreatedTime(board.getCreatedTime())
                .boardUpdatedTime(board.getUpdatedTime())
                .user(board.getUser())
                .boardCategory(board.getBoardCategory())
                .build());

        return boardDTOS;
    }

    @Transactional
    public void updateCommentCount(Long boardId, Long commentCount) {
        boardRepository.updateCommentCount(boardId, commentCount);
    }
}













