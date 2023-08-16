package kr.co.mannam.admin.board.controller;


import kr.co.mannam.admin.board.dto.BoardDTO;
import kr.co.mannam.admin.board.dto.CommentDTO;
import kr.co.mannam.admin.board.service.BoardService;
import kr.co.mannam.admin.board.service.CommentService;
import kr.co.mannam.admin.webmap.service.MarkService;
import kr.co.mannam.domain.entity.board.BoardEntity;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.entity.webmap.Mark;
import kr.co.mannam.domain.repository.board.BoardRepository;
import kr.co.mannam.type.board.BoardCategory;
import kr.co.mannam.type.member.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final CommentService commentService;
    private final MarkService markService;

    @GetMapping("/save/{category}/{userid}")
    public String saveForm(@PathVariable("category") BoardCategory category, Model model,@PathVariable("userid") String userid) {
        System.out.println("userid = " + userid);

        List<Mark.MarkMapping> list = markService.getMarkUser(userid);
        System.out.println("list = " + list);
        model.addAttribute("list",list);


        return "user/board/save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO,
                       @PageableDefault(page=1) Pageable pageable,
                       Model model,
                       HttpSession session) throws IOException {
        User user = (User) session.getAttribute("principal");
        boardDTO.setUser(user);
        boardService.save(boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "redirect:/board/paging/"+ boardDTO.getBoardCategory();
    }

    //////////// 게시판 더미 데이터용 save 메소드 ///////////////
//    @PostMapping("/save")
//    public String dummyData(@PageableDefault(page=1) Pageable pageable,
//                            Model model,
//                            HttpSession session) throws IOException {
//        for(int i=1;i<=100;i++){
//            BoardDTO boardDTO = BoardDTO.builder()
//                    .id((long) i)
//                    .boardWriter("아무개"+i)
//                    .boardTitle("테스트 제목 - "+i)
//                    .boardContents("테스트 내용입니다~~~~ ["+i+"]")
//                    .user((User)session.getAttribute("principal"))
//                    .build();
//            boardService.save(boardDTO);
//        }
//        model.addAttribute("page", pageable);
//        return "redirect:/board/paging";
//    }
    

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable,
                           HttpSession session) {
        /*
            해당 게시글의 조회수를 하나 올리고
            게시글 데이터를 가져와서 detail.html에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);

        /* 좋아요 여부 가져오기 */
        User principal = (User) (session.getAttribute("principal"));
        String userId = String.valueOf(principal.getId());
        boolean like = boardService.findLike(id, userId);
        model.addAttribute("like", like);

        /* 댓글 목록 가져오기 */
        List<CommentDTO> commentDTOList = commentService.findAll(id);
        model.addAttribute("commentList", commentDTOList);

        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "user/board/detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, @PageableDefault(page=1) Pageable pageable, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());
        return "user/board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO,
                         @PageableDefault(page=1) Pageable pageable,
                         Model model,
                         HttpSession session) {
        User user = (User) session.getAttribute("principal");
        boardDTO.setUser(user);

        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        model.addAttribute("page", pageable.getPageNumber());
        return "redirect:/board/" + boardDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         @PageableDefault(page=1) Pageable pageable,
                         Model model) {
        BoardCategory category = boardService.findById(id).getBoardCategory();
        boardService.delete(id);
        model.addAttribute("page", pageable.getPageNumber());
        return "redirect:/board/paging/"+category;
    }

    // /board/paging?page=1
//    @GetMapping("/paging/{category}")
//    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model,
//                         @PathVariable BoardCategory category) {
//        Page<BoardDTO> boardList = boardService.paging(pageable, category);
//        int blockLimit = 5;
//        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
//        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
//
//        // page 갯수 20개
//        // 현재 사용자가 3페이지
//        // 1 2 3
//        // 현재 사용자가 7페이지
//        // 7 8 9
//        // 보여지는 페이지 갯수 3개
//        // 총 페이지 갯수 8개
//
//        model.addAttribute("boardList", boardList);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
////        for (BoardCategory bc : BoardCategory.values()) {
////            System.out.println("bc = " + bc);
////            System.out.println("bc.getName() = " + bc.getName());
////        }
//
//        return "user/board/paging";
//
//    }

    @GetMapping("/paging/{category}")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model,
                         @PathVariable BoardCategory category, String keyword, String type,
                         HttpSession session) {
//        System.out.println("type = " + type);

        Page<BoardDTO> boardList;
        int blockLimit = 5;
        int startPage, endPage;


            if (keyword == null) {
                boardList = boardService.paging(pageable, category);
            } else {
                boardList = boardService.search(keyword, pageable, category, type);
                model.addAttribute("type", type);
                model.addAttribute("keyword", keyword);
            }


        startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        User user = (User)session.getAttribute("principal");
        if (user.getRole() == RoleType.ADMIN) {
            model.addAttribute("adminKey", RoleType.ADMIN);
        }

        return "user/board/paging";

    }

    // 통합 검색
    @GetMapping("/search")
    public String search(@PageableDefault(page = 1) Pageable pageable, Model model, String keyword, String type) {
//        System.out.println("type = " + type);
        List<BoardCategory> categorylist = boardRepository.findDistinctBoardCategory();
        System.out.println("categorylist = " + categorylist);
        model.addAttribute("category", categorylist);
        Page<BoardDTO> boardList;
        int blockLimit = 5;
        int startPage, endPage;



            boardList = boardService.search22(keyword, type, pageable);
            model.addAttribute("type", type);
            model.addAttribute("keyword", keyword);

        startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        endPage = Math.min(startPage + blockLimit - 1, boardList.getTotalPages());


        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "user/board/Integration";

    }

    // 게시글 좋아요
    @PostMapping("/like/{boardId}")
    @ResponseBody
    public boolean like(@PathVariable Long boardId, HttpSession session) {
        User principal = (User)(session.getAttribute("principal"));
        String userId = principal.getId();

        boolean result = boardService.saveLike(boardId, userId);
        return result;
    }

}









