package kr.co.mannam.admin.webmap.controller;


import kr.co.mannam.admin.member.dto.ResponseDTO;
import kr.co.mannam.admin.webmap.dto.FileDTO;
import kr.co.mannam.admin.webmap.dto.MarkDTO;
import kr.co.mannam.admin.webmap.service.FileService;
import kr.co.mannam.admin.webmap.service.MarkService;
import kr.co.mannam.domain.entity.member.User;
import kr.co.mannam.domain.entity.webmap.Mark;
import kr.co.mannam.domain.repository.webmap.MarkRepository;
import kr.co.mannam.type.board.BoardCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MapController {

    @Autowired
    ResourceLoader resourceLoader;


    @Autowired
    MarkService markService;

    @Autowired
    MarkRepository markRepository;

    @Autowired
    FileService fileService;

    @GetMapping("/kakaomap")
    public String kakaomap() {

        return "user/map/kakaomapmain";
    }


    // 마크 Form 이동
    @GetMapping("/auth/kakaomapRegister")
    public String kakaomapRegister(@RequestParam("y") String y, @RequestParam("x") String x, @RequestParam("tel") String tel, @RequestParam("address_name") String address_name, @RequestParam("place_name") String place_name, Model model) {

        System.out.println("y : " + y);
        System.out.println("tel : " + tel);

        ArrayList<String> list = new ArrayList<String>();
        list.add(y);
        list.add(x);
        list.add(tel);
        list.add(address_name);
        list.add(place_name);

        model.addAttribute("item", new ItemRequest());
        model.addAttribute("list", list);

        return "user/map/kakaomapRegister";
    }

    @Getter
    @Setter
    public class ItemRequest {

        private String markname;
        private String markaddress;
        private String markainfo;
        private String category;
        private String tel;
        private String latitude;
        private String longitude;
        private MultipartFile file;
    }



    @PostMapping("/map/kakaomapRegister")
    public String saveFormRequests(@ModelAttribute("item") ItemRequest itemRequest, HttpServletRequest request,
                                   HttpSession session) throws IOException {



        String markname = itemRequest.getMarkname();
        String markaddress = itemRequest.getMarkaddress();
        String markainfo = itemRequest.getMarkainfo();
        String category = itemRequest.getCategory();
        String tel = itemRequest.getTel();
        String latitude = itemRequest.getLatitude();
        String longitude = itemRequest.getLongitude();
        MarkDTO markDTO = MarkDTO.builder()
                .markname(markname)
                .markaddress(markaddress)
                .markainfo(markainfo)
                .category(category)
                .tel(tel)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        if (itemRequest.getFile() != null) {
            MultipartFile file = itemRequest.getFile();
//            String fullPath = request.getServletContext().getRealPath("")+ File.separator+repo + file.getOriginalFilename();
//            Resource resource = resourceLoader.getResource("classpath:markimage/txt.html");
//            System.out.println(resource.exists());
//            System.out.println(resource.getURL());
//            System.out.println(resource.getURI().getPath());
            String fullPath = "C:/markimage/" + file.getOriginalFilename();

            ClassPathResource resource = new ClassPathResource("static/markimage/");
            BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            System.out.println(br);

            System.out.println(fullPath);
            file.transferTo(new File(fullPath));

//            log.info("file.getOriginalFilename = {}", file.getOriginalFilename());
//            log.info("fullPath = {}", fullPath);

            FileDTO fileDto = FileDTO.builder()
                    .originFileName(file.getOriginalFilename())
//                    .fullPath(request.getServletContext().getRealPath("")+ File.separator+repo + file.getOriginalFilename())
                    .fullPath("C:/markimage/" + file.getOriginalFilename())
                    .build();
            String savedMarkimage = fileService.save(fileDto);
            markDTO.setMarkimage(savedMarkimage);

            System.out.println("markDTO"+markDTO);

            String savedMarkimagepath = fileService.save2(fileDto);
            markDTO.setMarkimagepath(savedMarkimagepath);

            System.out.println("markDTO2"+markDTO);
        }

        User user = (User) session.getAttribute("principal");
        markDTO.setUser(user);
        markService.save(markDTO);

        return "user/map/kakaomapmain";
    }

//    @PostMapping("/auth/kakaomapRegister")
//    public @ResponseBody ResponseDTO<?> insermark(@RequestBody Mark mark) {
//
//
//        markService.insertMark(mark);
//
//        return new ResponseDTO<>(HttpStatus.OK.value(), mark.getMarkname() + "등록 완료!!");
//
//    }


    @GetMapping("/kakaomarkmap/{userid}")
    public String kakaomarkmap(Model model, HttpSession session,  @PathVariable String userid){


        System.out.println("userid = " + userid);

        List<Mark.MarkMapping> list = markService.getMarkUser(userid);
        System.out.println("list = " + list);

        model.addAttribute("list",list);
        model.addAttribute("userid",userid);


//        return "user/map/kakaomarkmap";
        return "user/map/kakaomarkmap";
    }

    // 마커 수정
    @PutMapping("/markupdate")
    public @ResponseBody ResponseDTO<?> updateMark( @RequestBody Mark mark) {

        System.out.println("mark.getMid() = " + mark.getMid());
        System.out.println("mark.getMarkname() = " + mark.getMarkname());
        markService.updateMark(mark);


        return new ResponseDTO<>(HttpStatus.OK.value(),
                mark.getMid() + "번 마크를 수정했습니다!!");
    }

    // 마커 삭제
    @DeleteMapping("/markdelete/{mid}")
    public @ResponseBody ResponseDTO<?> deleteMark(@PathVariable Long mid) {


        markService.deleteMark(mid);


        return new ResponseDTO<>(HttpStatus.OK.value(),
                 "마크를 삭제했습니다!!");
    }

    @GetMapping("/root2")
    public String root() {

        return "user/map/root2";
    }


}
