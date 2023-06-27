package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.dto.response.PostResponse;
import com.thanh.ebacsi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);

    @Query("SELECT p from Post p ")
    List<PostResponse> getAllPost();

    @Query("""
                SELECT p from Post p
                inner join p.category c
                where c.categoryId = :categoryId
            """)
    List<PostResponse> getPostsByCategoryId(Long categoryId);

    @Query("SELECT p FROM Post p WHERE p.postId=?1")
    public Post getPostsByPostId(Long postId);

    @Query("""
                SELECT p from Post p
                inner join p.users u
                where u.username = :username
            """)
    List<PostResponse> getPostByUsername(String username);

    @Query("SELECT p FROM Post p where concat(p.title, ' ', p.category.cname,' ',p.users.username) like %?1%")
    List<PostResponse> search(String keyword);

    @Query("""
               select p from Post p
               inner join p.users u
               where u.userId = :userId
            """)
    List<PostResponse> findPostByUserId(Long userId);
}
