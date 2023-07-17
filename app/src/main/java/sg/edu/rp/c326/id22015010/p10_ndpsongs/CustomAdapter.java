package sg.edu.rp.c326.id22015010.p10_ndpsongs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Song> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Song> data;

    public CustomAdapter(Context context, int layoutResourceId, ArrayList<Song> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        Song song = data.get(position);

        TextView titleTextView = row.findViewById(R.id.textViewTitle);
        TextView singersTextView = row.findViewById(R.id.textViewSingers);
        TextView yearTextView = row.findViewById(R.id.textViewYear);
        TextView starsTextView = row.findViewById(R.id.textViewStars);

        titleTextView.setText(song.getTitle());
        singersTextView.setText(song.getSingers());
        yearTextView.setText(String.valueOf(song.getYear()));
        starsTextView.setText(String.valueOf(song.getStars()));

        return row;
    }
}
