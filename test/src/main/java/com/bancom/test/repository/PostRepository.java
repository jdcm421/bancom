package com.bancom.test.repository;

import com.bancom.test.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    
    @Query("SELECT p FROM Post p WHERE p.usuario.id=:id ORDER BY p.createdAt ASC")
    public List<Post> findByUser(Long id);
}
