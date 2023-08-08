package kr.co.mannam.domain.entity.webmap;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMark is a Querydsl query type for Mark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMark extends EntityPathBase<Mark> {

    private static final long serialVersionUID = 1637750975L;

    private static final PathInits INITS = PathInits.DIRECT2;

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

    public final kr.co.mannam.domain.entity.member.QUser user;

    public QMark(String variable) {
        this(Mark.class, forVariable(variable), INITS);
    }

    public QMark(Path<? extends Mark> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMark(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMark(PathMetadata metadata, PathInits inits) {
        this(Mark.class, metadata, inits);
    }

    public QMark(Class<? extends Mark> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new kr.co.mannam.domain.entity.member.QUser(forProperty("user")) : null;
    }

}

