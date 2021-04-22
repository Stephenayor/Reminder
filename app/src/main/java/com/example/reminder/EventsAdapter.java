package com.example.reminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.reminder.Database.EventsModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    private List<EventsModel> eventsModelList;
    private LayoutInflater layoutInflater;
    private Context context;
    private ItemClickListener itemClickListener;

    public EventsAdapter(List<EventsModel> modelList, Context context, ItemClickListener clickListener){
        this.eventsModelList = modelList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.itemClickListener = clickListener;
    }

    @NonNull
    @Override
    public EventsAdapter.EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.events_list_item,parent,false);
        return new EventsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventsViewHolder holder, int position) {
            EventsModel eventsModel = eventsModelList.get(position);
            String eventsName = eventsModel.getEventName();
            String eventsTime = eventsModel.getTimeSelected();
            String eventsDate = eventsModel.getDateSelected();

            holder.eventsNameView.setText(eventsName);
            holder.eventsTimeView.setText(eventsTime);
            holder.eventsDateView.setText(eventsDate);

    }

    @Override
    public int getItemCount() {
        if (eventsModelList==null) {
            return 0;
        }
        return eventsModelList.size();
    }

    public List<EventsModel> getEvents(){
        return eventsModelList;
    }

    public void setEvents(List<EventsModel> eventsModel) {
        eventsModelList = eventsModel;
        notifyDataSetChanged();
    }

    public interface ItemClickListener{
        void onItemClickListener(int eventId);
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView eventsNameView;
        private TextView eventsTimeView;
        private TextView eventsDateView;

        public EventsViewHolder(View itemView) {
            super(itemView);

            eventsNameView = itemView.findViewById(R.id.eventName);
            eventsTimeView = itemView.findViewById(R.id.eventTime);
            eventsDateView = itemView.findViewById(R.id.eventDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int eventId = eventsModelList.get(getAdapterPosition()).getId();
            itemClickListener.onItemClickListener(eventId);
        }
    }
}
