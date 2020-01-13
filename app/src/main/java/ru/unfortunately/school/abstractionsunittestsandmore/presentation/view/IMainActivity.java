package ru.unfortunately.school.abstractionsunittestsandmore.presentation.view;

import java.util.List;

import androidx.annotation.NonNull;
import ru.unfortunately.school.abstractionsunittestsandmore.data.model.InstalledPackageModel;

public interface IMainActivity {

    void showProgress();
    void hideProgress();
    void showData(@NonNull List<InstalledPackageModel> models);
}
