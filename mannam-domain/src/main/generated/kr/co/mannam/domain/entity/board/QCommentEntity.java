package kr.co.mannam.domain.entity.board;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentEntity is a Querydsl query type for CommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentEntity extends EntityPathBase<CommentEntity> {

    private static final long serialVersionUID = 243169062L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentEntity commentEntity = new QCommentEntity("commentEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBoardEntity boardEntity;

    public final StringPath commentContents = createString("commentContents");

    public final StringPath commentWriter = createString("commentWriter");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedTime = _super.updatedTime;

    public final kr.co.mannam.domain.entity.member.QUser user;

    public QCommentEntity(String variable) {
        this(CommentEntity.class, forVariable(variable), INITS);
    }

    public QCommentEntity(Path<? extends CommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentEntity(PathMetadata metadata, PathInits inits) {
        this(CommentEntity.class, metadata, inits);
    }

    public QCommentEntity(Class<? extends CommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardEntity = inits.isInitialized("boardEntity") ? new QBoardEntity(forProperty("boardEntity"), inits.get("boardEntity")) : null;
        this.user = inits.isInitialized("user") ? new kr.co.mannam.domain.entity.member.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

