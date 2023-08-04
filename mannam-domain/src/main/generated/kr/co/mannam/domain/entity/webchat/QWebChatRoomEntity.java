package kr.co.mannam.domain.entity.webchat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebChatRoomEntity is a Querydsl query type for WebChatRoomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebChatRoomEntity extends EntityPathBase<WebChatRoomEntity> {

    private static final long serialVersionUID = -75311660L;

    public static final QWebChatRoomEntity webChatRoomEntity = new QWebChatRoomEntity("webChatRoomEntity");

    public final NumberPath<Long> roomId = createNumber("roomId", Long.class);

    public final StringPath roomName = createString("roomName");

    public final NumberPath<Long> userCount = createNumber("userCount", Long.class);

    public final MapPath<String, String, StringPath> userList = this.<String, String, StringPath>createMap("userList", String.class, String.class, StringPath.class);

    public QWebChatRoomEntity(String variable) {
        super(WebChatRoomEntity.class, forVariable(variable));
    }

    public QWebChatRoomEntity(Path<? extends WebChatRoomEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebChatRoomEntity(PathMetadata metadata) {
        super(WebChatRoomEntity.class, metadata);
    }

}

