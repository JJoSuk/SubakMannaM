package kr.co.mannam.domain.entity.webmap;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMark is a Querydsl query type for Mark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMark extends EntityPathBase<Mark> {

    private static final long serialVersionUID = 1637750975L;

    public static final QMark mark = new QMark("mark");

    public final StringPath category = createString("category");

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    public final StringPath markaddress = createString("markaddress");

    public final StringPath markainfo = createString("markainfo");

    public final StringPath markimage = createString("markimage");

    public final StringPath markimagepath = createString("markimagepath");

    public final StringPath markname = createString("markname");

    public final NumberPath<Long> mid = createNumber("mid", Long.class);

    public final StringPath tel = createString("tel");

    public QMark(String variable) {
        super(Mark.class, forVariable(variable));
    }

    public QMark(Path<? extends Mark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMark(PathMetadata metadata) {
        super(Mark.class, metadata);
    }

}

