package com.marshalchen.ultimateandroid.demo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.marshalchen.ultimateandroid.demo.R;
import com.marshalchen.ultimateandroid.demo.adapter.RepositoryAdapter;
import com.marshalchen.ultimateandroid.demo.model.Repository;
import com.marshalchen.ultimateandroid.demo.model.RepositoryList;
import com.marshalchen.ultimateandroid.demo.presenter.RepositoryPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RepositoryView<List<Repository>> {
    RepositoryPresenter repositoryPresenter;
    @BindView(R.id.mainRecyclerview)
    RecyclerView mainRecyclerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.mainProgressBar)
    ProgressBar mainProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repositoryPresenter = new RepositoryPresenter(this);
        //repositoryPresenter.loadRepositoryInfo();

        repositoryPresenter.loadRepositoryInfo();

        mainRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mainRecyclerview.setLayoutManager(mLayoutManager);

    }

    @Override
    public void setReposotories(RepositoryList<List<Repository>> repositoryList) {
        mAdapter = new RepositoryAdapter(repositoryList.getRepositories());
        mainRecyclerview.setAdapter(mAdapter);
        mainProgressBar.setVisibility(View.GONE);
        mainRecyclerview.setVisibility(View.VISIBLE);

    }



    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgressBar() {
        mainProgressBar.setVisibility(View.VISIBLE);
        mainRecyclerview.setVisibility(View.GONE);
    }
}
