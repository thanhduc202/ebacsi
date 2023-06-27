package com.thanh.ebacsi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tag")
public class Tag {
    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    @Column(name = "tname",unique = true, nullable = false)
    private String tname;

    @ManyToMany(mappedBy = "tag")
    private Set<Post> posts = new HashSet<>();


}
