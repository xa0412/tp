package seedu.address.model.person;

public class Friend {
    public enum FriendType {
        ACQUAINTANCE, FRIEND, CLOSE_FRIEND
    }

    private final FriendType friendType;

    public Friend(FriendType friendType) {
        this.friendType = friendType;
    }

    public FriendType getFriendType() {
        return friendType;
    }

    @Override
    public String toString() {
        return friendType.toString();
    }
}
