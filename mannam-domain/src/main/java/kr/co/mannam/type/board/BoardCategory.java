package kr.co.mannam.type.board;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BoardCategory {
    Notice("공지사항"),
    Suggestion("건의사항"),
    Computer("컴퓨터"),
    Cycle("자전거"),
    Car("자동차"),
    Health("헬스"),
    Reading("독서"),
    Movie("영화");

    final private String name;
    BoardCategory(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
