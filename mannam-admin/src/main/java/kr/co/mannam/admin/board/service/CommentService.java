package kr.co.mannam.admin.board.service;

import kr.co.mannam.admin.board.dto.CommentDTO;
import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.board.CommentEntity;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.repository.board.BoardRepository;
import kr.co.mannam.domain.repository.board.CommentRepository;
import kr.co.mannam.domain.repository.member.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(CommentDTO commentDTO) {
        /* 부모엔티티(BoardEntity) */
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        Optional<User> optionalUser = userRepository.findById(commentDTO.getUserId());
        if(optionalBoardEntity.isPresent()){
            if (optionalUser.isPresent()){
                BoardEntity boardEntity = optionalBoardEntity.get();
                User user = optionalUser.get();
                CommentEntity commentEntity = CommentDTO.toSaveEntity(commentDTO, boardEntity, user);
                return commentRepository.save(commentEntity).getId();
            }
            return null;
        }else {
            return null;
        }
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        // select * from comment_table where board_id=? order by id desc;
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDTO> commentDTOList = new ArrayList<>();
//        for(CommentEntity commentEntity : commentEntityList){
//            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntity, boardId);
//            commentDTOList.add(commentDTO);
//        }
        for(int i=0;i<commentEntityList.size();i++){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(commentEntityList.get(i), boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    public CommentDTO findById(Long id) {
        CommentEntity comment = commentRepository.findById(id).orElse(null);
        CommentDTO commentDTO = CommentDTO.toCommentDTO(comment, comment.getBoardEntity().getId());
        return commentDTO;
    }


//     댓글 삭제
    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

//    댓글 수정
    @Transactional
    public  void modify(CommentDTO commentDTO) {
        CommentEntity comment = commentRepository.findById(commentDTO.getId()).orElse(null);
        comment.setCommentContents(commentDTO.getCommentContents());
    }

}
