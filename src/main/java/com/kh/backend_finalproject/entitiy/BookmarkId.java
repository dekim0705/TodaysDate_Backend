package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class BookmarkId implements Serializable {
    private int user;
    private int post;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkId id = (BookmarkId) o;
        return user == id.user && post == id.post;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
}
