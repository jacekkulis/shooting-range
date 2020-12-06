package com.politechnika.shootingrange.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.politechnika.shootingrange.activities.ClickedEventActivity;
import com.politechnika.shootingrange.exceptions.UnknownFragmentException;
import com.politechnika.shootingrange.fragments.BaseFragment;
import com.politechnika.shootingrange.fragments.IncomingEventsFragment;
import com.politechnika.shootingrange.fragments.OutdatedEventsFragment;
import com.politechnika.shootingrange.fragments.UserEventsFragment;
import com.politechnika.shootingrange.models.Event;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.politechnika.shootingrange.R;

public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.ViewHolder> {
    private static final String TAG = "EventCardAdapter";

    private ImageLoader imageLoader;
    private Context context;

    private List<Event> eventList;

    private BaseFragment fragment;

    public EventCardAdapter(final List<Event> events, Context context, BaseFragment fragment){
        super();
        Log.d(TAG, "CardAdapter: getting list of events.");
        this.eventList = events;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: inflating.");
        View v;
        if (fragment instanceof IncomingEventsFragment){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_incoming_events, parent, false);
        } else if (fragment instanceof UserEventsFragment){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_events, parent, false);
        } else if (fragment instanceof OutdatedEventsFragment){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_outdated_events, parent, false);
        } else {
            Log.d(TAG, "fragment instanceof nothing");
            v = new View(context);
            try {
                throw new UnknownFragmentException("fragment instanceof nothing");
            } catch (UnknownFragmentException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "onCreateViewHolder: creating ViewHolder.");
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: start");

        //Getting the particular item from the list
        Event event = eventList.get(position);

        Log.d(TAG, "onBindViewHolder: Loading image from url");
        imageLoader = RequestHandler.getInstance(context).getImageLoader();
        imageLoader.get(event.getThumbnailUrl(), ImageLoader.getImageListener(holder.eventThumbnailImage, R.drawable.ic_default_image, android.R.drawable.ic_dialog_alert));

        Log.d(TAG, "onBindViewHolder: Showing data on the views");
        holder.eventThumbnailImage.setImageUrl(event.getThumbnailUrl(), imageLoader);
        holder.eventTitleField.setText(event.getCompetitionName());
        holder.eventDescriptionField.setText(event.getCompetitionDescription());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);
            Date date = sdf.parse(event.getCompetitionDate());
            SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.GERMANY);
            holder.eventDateField.setText(sdfOut.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void clearList() {
        // clear list
        eventList.clear();
        // notify set changed
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        // remove item from list
        eventList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.field_event_title) TextView eventTitleField;
        @BindView(R.id.field_event_description) TextView eventDescriptionField;
        @BindView(R.id.field_event_date) TextView eventDateField;
        @BindView(R.id.image_event_thumbnail) NetworkImageView eventThumbnailImage;

        @BindView(R.id.view_foreground)
        public RelativeLayout viewForeground;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(this);
        }

        public RelativeLayout getViewForeground() {
            return viewForeground;
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "ViewHolder:onClick: getting position of card");
            int position = getAdapterPosition();
            Event event = eventList.get(position);
            Log.d(TAG, "ViewHolder:onClick: creating intent");
            Intent intent = new Intent(view.getContext(), ClickedEventActivity.class);

            Log.d(TAG, String.format("ViewHolder:onClick: putting extra keys to intent: %s", event.toString()));
            intent.putExtra(Event.TAG_CLICKED_EVENT, event);

            Log.d(TAG, "ViewHolder:onClick: starting activity with extra keys.");
            view.getContext().startActivity(intent);
        }
    }
}