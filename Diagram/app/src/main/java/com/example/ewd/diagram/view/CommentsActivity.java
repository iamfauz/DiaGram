package com.example.ewd.diagram.view;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.Comment;
import com.example.ewd.diagram.model.local.Post;
import com.example.ewd.diagram.model.remote.retrofit.ApiService;
import com.example.ewd.diagram.model.remote.retrofit.RetrofitClientInstance;
import com.example.ewd.diagram.utils.CommentAdapter;
import com.example.ewd.diagram.utils.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener  {

    @BindView(R.id.recycler_view_comments)
    public RecyclerView mRecyclerView;

    @BindView(R.id.swipe_to_refresh)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.add_comment_layout)
    public LinearLayout addCommentLayout;

    @BindView(R.id.add_comment)
    public Button addCommentButton;

    @BindView(R.id.comment)
    public EditText commentEditText;

    private CommentAdapter mCommentAdapter;

    private String postId;
    private String userType;

    //For testing
    List<Comment> commentsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        postId = intent.getStringExtra("postId");
        userType = intent.getStringExtra("userType");

        setupAddCommentLayout();
        setupAddPostButton();
        initRecyclerView();


        mSwipeRefreshLayout.setOnRefreshListener(this);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeRefreshLayout.post(new Runnable() {

            @Override
            public void run() {

                mSwipeRefreshLayout.setRefreshing(true);
                // Fetching data from server
                loadDataToRecyclerView();
            }
        });


    }



    /**
     * Method to initialize recyclerView
     */
    public void initRecyclerView() {

        //RecyclerViewDefinition
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        //Improving performance
        mRecyclerView.setHasFixedSize(true);

        mCommentAdapter = new CommentAdapter(this);
        mRecyclerView.setAdapter(mCommentAdapter);

    }


    public void loadDataToRecyclerView() {

        //TODO - Fetch actual data from server , once API call is ready

        mSwipeRefreshLayout.setRefreshing(false);

        //Testing
        Comment comment1 = new Comment();
        comment1.setUserType("doctor");
        comment1.setBody("It looks like like you have a serious problem. It's very common, So should be fine");

        Comment comment2 = new Comment();
        comment2.setUserType("patient");
        comment2.setBody("Thank you soo much for your advice doctor.");

        Comment comment3 = new Comment();
        comment3.setUserType("patient");
        comment3.setBody("No worries.");

        commentsList.add(comment1);
        commentsList.add(comment2);
        commentsList.add(comment3);

        mCommentAdapter.setCommentsData(commentsList);


    }

    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {

        // Fetching data from server
        loadDataToRecyclerView();
    }

    /**
     * This method sets up the AddComment LinearLAyout
     */
    public void setupAddCommentLayout(){

        if(userType.equals("doctor")){
            addCommentLayout.setVisibility(View.GONE);
        }else{
            addCommentLayout.setVisibility(View.VISIBLE);

        }

    }

    public void setupAddPostButton(){


        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO - Make POST API call to add comment

                //TESTING FRONT END
                String comment = commentEditText.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    return;
                }

                Comment newComment = new Comment();
                newComment.setUserType("patient");
                newComment.setBody(comment);

                commentsList.add(newComment);
                mCommentAdapter.setCommentsData(commentsList);
                commentEditText.setText("");

                //loadDataToRecyclerView();


            }
        });

    }


}
