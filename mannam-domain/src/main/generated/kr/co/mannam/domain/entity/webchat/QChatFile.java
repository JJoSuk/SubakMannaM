package kr.co.mannam.domain.entity.webchat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatFile is a Querydsl query type for ChatFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatFile extends EntityPathBase<ChatFile> {

    private static final long serialVersionUID = -426584470L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChatFile chatFile = new QChatFile("chatFile");

    public final QChatRoom chatRoom;

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath s3Key = createString("s3Key");

    public QChatFile(String variable) {
        this(ChatFile.class, forVariable(variable), INITS);
    }

    public QChatFile(Path<? extends ChatFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChatFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChatFile(PathMetadata metadata, PathInits inits) {
        this(ChatFile.class, metadata, inits);
    }

    public QChatFile(Class<? extends ChatFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.chatRoom = inits.isInitialized("chatRoom") ? new QChatRoom(forProperty("chatRoom")) : null;
    }

}

