package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;
import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class BlockTb {
    @Id
    @GeneratedValue
    @Column(name = "block_num")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocker_fk")
    private UserTb blocker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blocked_fk")
    private UserTb blocked;
}
