package com.thanh.ebacsi.repository;

import com.thanh.ebacsi.dto.response.FeedbackResponse;

import com.thanh.ebacsi.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("""
                SELECT f from Feedback f
                inner join f.post p
                where p.postId = :postId
            """)
    List<Feedback> getFeedbackByPostID(Long postId);


    List<Feedback> findByContent(String content);

    @Query("""
                SELECT f from Feedback f where f.feedbackId = :feedbackId
            """)
    List<Feedback> findByFeedbackId(Long feedbackId);

    @Query("""
                SELECT f from Feedback f
                inner join f.user u
                where u.userId = :userId
            """)
    List<FeedbackResponse> getFeedbackByUserID(Long userId);


}
