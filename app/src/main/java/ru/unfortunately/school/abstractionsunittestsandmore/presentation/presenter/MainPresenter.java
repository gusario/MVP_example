package ru.unfortunately.school.abstractionsunittestsandmore.presentation.presenter;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import ru.unfortunately.school.abstractionsunittestsandmore.data.model.InstalledPackageModel;
import ru.unfortunately.school.abstractionsunittestsandmore.data.repository.PackageInstallRepository;
import ru.unfortunately.school.abstractionsunittestsandmore.presentation.view.IMainActivity;
import ru.unfortunately.school.abstractionsunittestsandmore.presentation.view.MainActivity;

public class MainPresenter {

    private WeakReference<IMainActivity> mMainActivityWeakReference;
    private PackageInstallRepository mPackageInstallRepository;

    public MainPresenter(@NonNull IMainActivity mainActivity, @NonNull PackageInstallRepository packageInstallRepository){
        mMainActivityWeakReference = new WeakReference<>(mainActivity);
        mPackageInstallRepository = packageInstallRepository;
    }


    public void loadData(boolean isSystem, final int sortType){
        IMainActivity activity = mMainActivityWeakReference.get();
        if(activity != null){
            activity.showProgress();
        }
        List<InstalledPackageModel> data = mPackageInstallRepository.getData(isSystem);

        if(sortType == MainActivity.SORT_BY_NAME || sortType == MainActivity.SORT_BY_PACKAGE_NAME){
            Collections.sort(data, new Comparator<InstalledPackageModel>() {
                @Override
                public int compare(InstalledPackageModel o1, InstalledPackageModel o2) {
                    return sortType==MainActivity.SORT_BY_NAME ?
                            o1.getAppName().compareTo(o2.getAppName()) :
                            o1.getAppPackageName().compareTo(o2.getAppPackageName());
                }
            });
        }
        if(activity != null){
            activity.hideProgress();
            activity.showData(data);
        }
    }
}
