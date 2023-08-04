package kr.co.mannam.admin.board.controller;

import kr.co.mannam.admin.board.dto.CommentDTO;
import kr.co.mannam.admin.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO) {
        Long saveResult = commentService.save(commentDTO);
        if (saveResult != null) {
            // 작성 성공 시 댓글목록을 가져와서 리턴
            // 댓글목록 : 해당 게시글의 댓글 전체
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        CommentDTO commentDTO = commentService.findById(id);
        commentService.delete(id);

        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);

//        if (deleteResult != null) {
//
//        } else {
//            return new ResponseEntity<>("해당 댓글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
//        }
    }

    @GetMapping("/modify/{id}")
    public ResponseEntity modifyForm(@PathVariable Long id) {
        CommentDTO commentDTO = commentService.findById(id);
        List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @PatchMapping("/modify")
    public ResponseEntity modify(@ModelAttribute CommentDTO commentDTO) {
        CommentDTO findDTO = commentService.findById(commentDTO.getId());
        commentDTO.setCommentContents(commentDTO.getCommentContents());
        commentService.modify(commentDTO);

        List<CommentDTO> commentDTOList = commentService.findAll(findDTO.getBoardId());
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }
}
