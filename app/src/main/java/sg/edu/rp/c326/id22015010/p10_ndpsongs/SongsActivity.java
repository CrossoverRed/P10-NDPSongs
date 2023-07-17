package sg.edu.rp.c326.id22015010.p10_ndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongsActivity extends AppCompatActivity {
    ListView lv;
    Button backButton;
    DBHelper dbHelper;
    ArrayList<Song> songsList;
    CustomAdapter aa;
    Spinner yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        lv = findViewById(R.id.listview);
        backButton = findViewById(R.id.backbtn);
        songsList = new ArrayList<>();
        yearSpinner = findViewById(R.id.yearSpinner);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Spinner yearSpinner = findViewById(R.id.yearSpinner);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedYear = parent.getItemAtPosition(position).toString();
                filterSongsByYear(Integer.parseInt(selectedYear));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        dbHelper = new DBHelper(this);
        ArrayList<Song> songs = dbHelper.getAllSongs();
        songsList.clear();
        songsList.addAll(songs);
        aa = new CustomAdapter(this, R.layout.row, songsList);
        lv.setAdapter(aa);

        // Set click listener for the ListView items
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song clickedSong = songsList.get(position);

                Intent intent = new Intent(SongsActivity.this, ThirdActivity.class);
                intent.putExtra("id", clickedSong.getId());
                intent.putExtra("title", clickedSong.getTitle());
                intent.putExtra("singers", clickedSong.getSingers());
                intent.putExtra("year", clickedSong.getYear());
                intent.putExtra("stars", clickedSong.getStars());

                startActivity(intent);
            }
        });

        // Add button and spinner functionality
        Button showFiveStarsButton = findViewById(R.id.showFiveStarsButton);
        showFiveStarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSongsWithFiveStars();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbHelper = new DBHelper(this);
        ArrayList<Song> songs = dbHelper.getAllSongs();
        songsList.clear();
        songsList.addAll(songs);
        aa.notifyDataSetChanged();
    }

    private void showSongsWithFiveStars() {
        ArrayList<Song> filteredSongs = new ArrayList<>();
        for (Song song : songsList) {
            if (song.getStars() == 5) {
                filteredSongs.add(song);
            }
        }
        aa.clear();
        aa.addAll(filteredSongs);
    }
    private void filterSongsByYear(int selectedYear) {
        ArrayList<Song> filteredSongs = new ArrayList<>();
        for (Song song : dbHelper.getAllSongs()) {
            if (song.getYear() == selectedYear) {
                filteredSongs.add(song);
            }
        }
        aa.clear();
        aa.addAll(filteredSongs);
        aa.notifyDataSetChanged();
    }
}
