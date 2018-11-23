package com.example.ewd.diagram.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ewd.diagram.R;
import com.example.ewd.diagram.model.local.Comment;
import com.example.ewd.diagram.model.local.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentAdapterViewHolder> {

    //Data store
    private List<Comment> mCommentList;

    private Context context;

    private int[] colors = {R.color.colorLightGreen, R.color.colorAmber};


    public CommentAdapter(Context context) {


        this.context = context;

    }


    //ViewHolder Class for normal
    public class CommentAdapterViewHolder extends RecyclerView.ViewHolder {

        //Views
        public @BindView(R.id.username)
        TextView userNameTextView;
        public @BindView(R.id.body)
        TextView bodyTextView;
       // public @BindView(R.id.date)
        //TextView dateTextView;
        @BindView(R.id.user_type_image_view)
        ImageView userTypeImageView;


        public Comment comment;

        public CommentAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);


        }

    }

    @NonNull
    @Override
    public CommentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.comment_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new CommentAdapterViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapterViewHolder holder, int position) {

        Comment comment = mCommentList.get(position);
        holder.comment = comment;
        holder.bodyTextView.setText(comment.getBody());

        //Circular Icon
        int colorIndex;
        String userTypeChar;
        colorIndex = comment.getUserType().equals("patient") ? 0 : 1;
        userTypeChar = comment.getUserType().equals("patient")? "P" : "D";

        TextDrawable drawable = TextDrawable.builder()
                .buildRoundRect(userTypeChar,
                        context.getResources().getColor(colors[colorIndex]), 70);
        holder.userTypeImageView.setImageDrawable(drawable);

    }

    @Override
    public int getItemCount() {

        if (mCommentList == null)
            return 0;
        else
            return mCommentList.size();
    }


    public void setCommentsData(List<Comment> commentList) {

        mCommentList = commentList;
        notifyDataSetChanged();
    }

    public List<Comment> getComments() {
        return mCommentList;
    }
}

