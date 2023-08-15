package kr.co.mannam.domain.entity.webchat;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QChatRoom is a Querydsl query type for ChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoom extends EntityPathBase<ChatRoom> {

    private static final long serialVersionUID = -426221111L;

    public static final QChatRoom chatRoom = new QChatRoom("chatRoom");

    public final ListPath<ChatFile, QChatFile> chatFiles = this.<ChatFile, QChatFile>createList("chatFiles", ChatFile.class, QChatFile.class, PathInits.DIRECT2);

    public final ListPath<Chat, QChat> chats = this.<Chat, QChat>createList("chats", Chat.class, QChat.class, PathInits.DIRECT2);

    public final NumberPath<Integer> maxUserCnt = createNumber("maxUserCnt", Integer.class);

    public final StringPath roomId = createString("roomId");

    public final StringPath roomName = createString("roomName");

    public final StringPath roomPwd = createString("roomPwd");

    public final BooleanPath secretChk = createBoolean("secretChk");

    public final NumberPath<Integer> userCount = createNumber("userCount", Integer.class);

    public final ListPath<kr.co.mannam.domain.entity.member.User, kr.co.mannam.domain.entity.member.QUser> users = this.<kr.co.mannam.domain.entity.member.User, kr.co.mannam.domain.entity.member.QUser>createList("users", kr.co.mannam.domain.entity.member.User.class, kr.co.mannam.domain.entity.member.QUser.class, PathInits.DIRECT2);

    public QChatRoom(String variable) {
        super(ChatRoom.class, forVariable(variable));
    }

    public QChatRoom(Path<? extends ChatRoom> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoom(PathMetadata metadata) {
        super(ChatRoom.class, metadata);
    }

}

