package kr.co.mannam.domain.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1341749227L;

    public static final QUser user = new QUser("user");

    public final ListPath<kr.co.mannam.domain.entity.board.BoardEntity, kr.co.mannam.domain.entity.board.QBoardEntity> boardEntitieList = this.<kr.co.mannam.domain.entity.board.BoardEntity, kr.co.mannam.domain.entity.board.QBoardEntity>createList("boardEntitieList", kr.co.mannam.domain.entity.board.BoardEntity.class, kr.co.mannam.domain.entity.board.QBoardEntity.class, PathInits.DIRECT2);

    public final ListPath<kr.co.mannam.domain.entity.board.BookMark, kr.co.mannam.domain.entity.board.QBookMark> bookMarkList = this.<kr.co.mannam.domain.entity.board.BookMark, kr.co.mannam.domain.entity.board.QBookMark>createList("bookMarkList", kr.co.mannam.domain.entity.board.BookMark.class, kr.co.mannam.domain.entity.board.QBookMark.class, PathInits.DIRECT2);

    public final ListPath<kr.co.mannam.domain.entity.board.CommentEntity, kr.co.mannam.domain.entity.board.QCommentEntity> commentEntitieList = this.<kr.co.mannam.domain.entity.board.CommentEntity, kr.co.mannam.domain.entity.board.QCommentEntity>createList("commentEntitieList", kr.co.mannam.domain.entity.board.CommentEntity.class, kr.co.mannam.domain.entity.board.QCommentEntity.class, PathInits.DIRECT2);

    public final DateTimePath<java.sql.Timestamp> createDate = createDateTime("createDate", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final StringPath id = createString("id");

    public final StringPath password = createString("password");

    public final EnumPath<kr.co.mannam.type.member.RoleType> role = createEnum("role", kr.co.mannam.type.member.RoleType.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

