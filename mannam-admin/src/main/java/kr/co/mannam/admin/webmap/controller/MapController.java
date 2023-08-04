package kr.co.mannam.admin.webmap.controller;


import kr.co.mannam.admin.webmap.dto.FileDTO;
import kr.co.mannam.admin.webmap.dto.MarkDTO;
import kr.co.mannam.admin.webmap.service.FileService;
import kr.co.mannam.admin.webmap.service.MarkService;
import kr.co.mannam.domain.entity.webmap.Mark;
import kr.co.mannam.domain.repository.webmap.MarkRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public String saveFormRequests(@ModelAttribute("item") ItemRequest itemRequest, HttpServletRequest request) throws IOException {



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

//        markService.save(markDTO);

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


    @GetMapping("/kakaomarkmap")
    public String kakaomarkmap(Model model){

        List<Mark> list = markService.getMark();
        System.out.println("list = " + list);

        model.addAttribute("list",list);


//        return "user/map/kakaomarkmap";
        return "user/map/kakaomarkmap";
    }

    @GetMapping("/markupdate/{mid}")
    public String markupdate(@PathVariable Long mid, Model model){

        model.addAttribute("list", markService.getMark2(mid));


        return "user/map/markupdate";
    }

    // 마커 수정
    @PostMapping("/markupdate")
    public String updatePost( String markname, int mid, @ModelAttribute("item") ItemRequest itemRequest, HttpServletRequest request) throws IOException {

        System.out.println("markname = " + markname);
        System.out.println("mid = " + mid);

        List<Mark> marks = markRepository.findAll();


        marks.get(mid-1).setMarkname(markname);
        markRepository.save(marks.get(mid-1)); // Update


        return "redirect:/kakaomarkmap";
    }

    @GetMapping("/root2")
    public String root() {

        return "user/map/root2";
    }


}
