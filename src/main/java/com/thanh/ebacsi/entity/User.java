package com.thanh.ebacsi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.thanh.ebacsi.dto.request.UserInfoRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enable", columnDefinition = "bit default true")
    private Boolean enable;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @JsonManagedReference
    private Set<Role> role;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonManagedReference
    private List<Post>  post;


    public User(UserInfoRequest userInfoRequest) {
        this.username = userInfoRequest.getUsername();
        this.password = userInfoRequest.getPassword();
        this.enable = userInfoRequest.getEnable();
    }

}
