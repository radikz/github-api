package id.radikz.githubapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import id.radikz.githubapi.DetailFragment;
import id.radikz.githubapi.R;
import id.radikz.githubapi.model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private List<User> users;
    private Context context;


    public UserAdapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView releaseDate;
        TextView login;
        TextView rating;
        TextView genres;
        ImageView avatar;


        public MyViewHolder(View itemView) {
            super(itemView);
//            releaseDate = itemView.findViewById(R.id.item_movie_release_date);
            login = itemView.findViewById(R.id.user_name);
            avatar = itemView.findViewById(R.id.user_avatar);
//            rating = itemView.findViewById(R.id.item_movie_rating);
//            genres = itemView.findViewById(R.id.item_movie_genre);
        }

        public void bind(final User user) {
            login.setText(user.getLogin());

            Glide.with(context).load(user.getAvatarUrl()).asBitmap().centerCrop().into(new BitmapImageViewTarget(avatar) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    avatar.setImageDrawable(circularBitmapDrawable);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, user.getLogin(), Toast.LENGTH_SHORT).show();
                    // Begin the transaction
                    String message = user.getLogin();
//                    Bundle data = new Bundle();
//                    data.putString(DetailFragment.KEY_ACTIVITY, message);
                    DetailFragment fragmentList = new DetailFragment();
//                    fragmentList.setArguments(data);

//                    Intent moveWithObjectIntent = new Intent(context, fragmentList.class);
//                    moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person);
//                    startActivity(moveWithObjectIntent);

//                    MyFragment fragment = new MyFragment(); //Your Fragment
//                    User user1 = new Car(); // Your Object
//                    user.lo = "Honda"
//                    car.color = "Brown";
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(DetailFragment.KEY_ACTIVITY, user);  // Key, value
                    fragmentList.setArguments(bundle);

                    FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, fragmentList);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }

}
