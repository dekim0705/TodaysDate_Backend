package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class FolderTb {
    @Id
    @GeneratedValue
    @Column(name = "folder_num")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTb user;

    @OneToMany(mappedBy = "folder")
    private List<BookmarkTb> bookmarks;
}
