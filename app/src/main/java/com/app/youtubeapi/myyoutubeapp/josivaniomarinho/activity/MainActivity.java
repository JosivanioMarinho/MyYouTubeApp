package com.app.youtubeapi.myyoutubeapp.josivaniomarinho.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.R;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.adapter.Adapter;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.api.YouTubeService;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.helper.RecyclerItemClickListener;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.helper.RetrofitConfig;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.helper.YouTubeConfig;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.model.Item;
import com.app.youtubeapi.myyoutubeapp.josivaniomarinho.model.Resultado;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerVideos;

    private MaterialSearchView searchView;

    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;

    private Adapter adapter;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configurar Toobar
        Toolbar toobar = findViewById(R.id.toobar);
        setSupportActionBar(toobar);
        toobar.setTitle("MyYouTube");
        toobar.setTitleTextColor(getResources().getColor(R.color.colorBackground));

       searchView = findViewById(R.id.searchView);

       //Configura retrofit
        retrofit = RetrofitConfig.getRetrofit();

        recyclerVideos = findViewById(R.id.recyclerVideos);

        //Recupera videos
        recuperarVideos("");

        //Configura métodos do SearcgView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperarVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recuperarVideos("");
            }
        });
    }

    public void recuperarVideos(String pesquisa){

        String q = pesquisa.replaceAll(" ", "+");
        YouTubeService youTubeService = retrofit.create( YouTubeService.class );

        youTubeService.recuperarVideos(
                "snippet", "date", "20",
                YouTubeConfig.CHAVE_YOUTUBE_API, YouTubeConfig.CANAL_ID, q
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado", "resultado: " + response.toString());
                if (response.isSuccessful()){

                    resultado = response.body();
                    videos = resultado.items;
                    configuraRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });

    }

    public void configuraRecyclerView(){

        adapter = new Adapter(videos, this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerVideos.setAdapter(adapter);

        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                Item video = videos.get(position);
                                String idVideos = video.id.videoId;

                                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                                i.putExtra("idVideo", idVideos);
                                startActivity(i);

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.menuPesquisa);
        searchView.setMenuItem(menuItem);

        return super.onCreateOptionsMenu(menu);
    }
}
