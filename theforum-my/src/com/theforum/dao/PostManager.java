package com.theforum.dao;

import com.theforum.entities.Posts;
import java.util.List;

public interface PostManager {
 
    public List<Posts> loadAllPosts();
 
    public void saveOrUpdatePost(Posts p);
 
    public Posts findPostById(Long id);
 
    public void deletePost(Posts p);

}
