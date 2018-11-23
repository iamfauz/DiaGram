package com.example.ewd.diagram.model.local;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsListResponse {

    @SerializedName("posts")
    @Expose
    private List<Post> posts = null;

    public List<Post> getPosts() {
        return posts;
    }
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}