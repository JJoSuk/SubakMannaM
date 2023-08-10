package kr.co.mannam.domain.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardEntity is a Querydsl query type for BoardEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardEntity extends EntityPathBase<BoardEntity> {

    private static final long serialVersionUID = 1858201453L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardEntity boardEntity = new QBoardEntity("boardEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final EnumPath<kr.co.mannam.type.board.BoardCategory> boardCategory = createEnum("boardCategory", kr.co.mannam.type.board.BoardCategory.class);

    public final StringPath boardContents = createString("boardContents");

    public final NumberPath<Integer> boardHits = createNumber("boardHits", Integer.class);

    public final StringPath boardTitle = createString("boardTitle");

    public final StringPath boardWriter = createString("boardWriter");

    public final ListPath<CommentEntity, QCommentEntity> commentEntitieList = this.<CommentEntity, QCommentEntity>createList("commentEntitieList", CommentEntity.class, QCommentEntity.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final kr.co.mannam.domain.entity.member.QUser user;

    public QBoardEntity(String variable) {
        this(BoardEntity.class, forVariable(variable), INITS);
    }

    public QBoardEntity(Path<? extends BoardEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardEntity(PathMetadata metadata, PathInits inits) {
        this(BoardEntity.class, metadata, inits);
    }

    public QBoardEntity(Class<? extends BoardEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new kr.co.mannam.domain.entity.member.QUser(forProperty("user")) : null;
    }

}

