package com.thanh.ebacsi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.thanh.ebacsi.dto.request.CategoryRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(name = "cname", unique = true, nullable = false)
    private String cname;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @JsonManagedReference
    private List<Post> post;


    public Category(CategoryRequest categoryRequest) {
        this.cname = categoryRequest.getCname();
    }
}
