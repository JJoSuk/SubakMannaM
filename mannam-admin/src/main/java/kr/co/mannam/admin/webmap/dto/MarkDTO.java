package kr.co.mannam.admin.webmap.dto;

import kr.co.mannam.domain.entity.webmap.Mark;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
    @Data
    public class MarkDTO {
        private Long mid;
        private String markname;
        private String markimage;
        private String markimagepath;
        private String markaddress;
        private String markainfo;
        private String category;
        private String tel;
        private String latitude;
        private String longitude;

        public Mark toEntity() {
            return Mark.builder()

                    .markname(this.markname)
                    .markimage(this.markimage)
                    .markimagepath(this.markimagepath)
                    .markaddress(this.markaddress)
                    .markainfo(this.markainfo)
                    .category(this.category)
                    .tel(this.tel)
                    .latitude(this.latitude)
                    .longitude(this.longitude)
                    .build();
        }

        @Builder
        public MarkDTO(String markname, String markimage,String markimagepath, String markaddress, String markainfo, String category, String tel, String latitude, String longitude) {
            this.markname = markname;
            this.markimage = markimage;
            this.markimagepath = markimagepath;
            this.markaddress = markaddress;
            this.markainfo = markainfo;
            this.category = category;
            this.tel = tel;
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

