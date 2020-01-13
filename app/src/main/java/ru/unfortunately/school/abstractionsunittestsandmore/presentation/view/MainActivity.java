package ru.unfortunately.school.abstractionsunittestsandmore.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.abstractionsunittestsandmore.R;
import ru.unfortunately.school.abstractionsunittestsandmore.data.model.InstalledPackageModel;
import ru.unfortunately.school.abstractionsunittestsandmore.data.repository.PackageInstallRepository;
import ru.unfortunately.school.abstractionsunittestsandmore.presentation.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements IMainActivity{

    private static final String TAG = "SOMETAG!!!!";
    private RecyclerView mRecyclerView;
    private FrameLayout mProgressFrameLayout;
    private MainPresenter mMainPresenter;
    private Button mLoadDataButton;
    private Spinner mSortTypeSpinner;
    private CheckBox mSystemAppCheckbox;

    public static final int NOT_SORT = 0;
    public static final int SORT_BY_NAME = 1;
    public static final int SORT_BY_PACKAGE_NAME = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        providePresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void providePresenter() {
        PackageInstallRepository packageInstallRepository =
                new PackageInstallRepository(this);
        mMainPresenter = new MainPresenter(this, packageInstallRepository);
    }

    private void initViews() {
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressFrameLayout = findViewById(R.id.progress_frame_layout);
        mRecyclerView.setLayoutManager(layoutManager);
        mSystemAppCheckbox = findViewById(R.id.chk_box_show_system);
        initLoadButton();
        initSortTypesSpinner();
    }

    private void initSortTypesSpinner(){
        mSortTypeSpinner = findViewById(R.id.spinner_sorts);
        List<String> items = new ArrayList<>();
        items.add(getString(R.string.dont_sort));
        items.add(getString(R.string.sort_by_name));
        items.add(getString(R.string.sort_by_package_name));
        SpinnerSortAdapter adapter = new SpinnerSortAdapter(items);
        mSortTypeSpinner.setAdapter(adapter);
    }

    private void initLoadButton() {
        mLoadDataButton = findViewById(R.id.btn_load_data);
        mLoadDataButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPresenter.loadData(
                        mSystemAppCheckbox.isChecked(),
                        mSortTypeSpinner.getSelectedItemPosition());
                mLoadDataButton.setText(R.string.refresh);
            }
        });
    }

    @Override
    public void showProgress() {
        mProgressFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void showData(@NonNull List<InstalledPackageModel> models) {
        PackageInstalledAdapter adapter = new PackageInstalledAdapter(models);
        mRecyclerView.setAdapter(adapter);
    }
}
