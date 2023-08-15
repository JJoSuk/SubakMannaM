package kr.co.mannam.domain.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeBoard is a Querydsl query type for LikeBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeBoard extends EntityPathBase<LikeBoard> {

    private static final long serialVersionUID = -467971181L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeBoard likeBoard = new QLikeBoard("likeBoard");

    public final QBoardEntity board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final kr.co.mannam.domain.entity.member.QUser user;

    public QLikeBoard(String variable) {
        this(LikeBoard.class, forVariable(variable), INITS);
    }

    public QLikeBoard(Path<? extends LikeBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeBoard(PathMetadata metadata, PathInits inits) {
        this(LikeBoard.class, metadata, inits);
    }

    public QLikeBoard(Class<? extends LikeBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoardEntity(forProperty("board"), inits.get("board")) : null;
        this.user = inits.isInitialized("user") ? new kr.co.mannam.domain.entity.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

