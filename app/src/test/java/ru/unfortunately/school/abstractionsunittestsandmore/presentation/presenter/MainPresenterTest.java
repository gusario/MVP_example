package ru.unfortunately.school.abstractionsunittestsandmore.presentation.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.unfortunately.school.abstractionsunittestsandmore.data.model.InstalledPackageModel;
import ru.unfortunately.school.abstractionsunittestsandmore.data.repository.PackageInstallRepository;
import ru.unfortunately.school.abstractionsunittestsandmore.presentation.view.IMainActivity;
import ru.unfortunately.school.abstractionsunittestsandmore.presentation.view.MainActivity;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    private IMainActivity mMainActivity;

    @Mock
    private PackageInstallRepository mPackageInstallRepository;

    private MainPresenter mMainPresenter;


    @Before
    public void setUp() throws Exception{
        mMainPresenter = new MainPresenter(mMainActivity, mPackageInstallRepository);

    }

    @Test
    public void testLoadData() {
        List<InstalledPackageModel> modelList = createTestData();
        when(mPackageInstallRepository.getData(anyBoolean())).thenReturn(modelList);
        mMainPresenter.loadData(true, 0);
        verify(mMainActivity).showProgress();
        verify(mMainActivity).showData(modelList);
    }

    @Test
    public void testDataSort(){
        List<InstalledPackageModel> modelList = createTestData();
        when(mPackageInstallRepository.getData(true)).thenReturn(new ArrayList<>(modelList));
        mMainPresenter.loadData(true, MainActivity.SORT_BY_NAME);
        verify(mMainActivity).showData(sortByName(modelList));
    }

    @Test
    public void testCorrectIsBoolean(){
        mMainPresenter.loadData(true, 0);
        verify(mPackageInstallRepository).getData(true);
        mMainPresenter.loadData(false, 0);
        verify(mPackageInstallRepository).getData(false);
    }

    private List<InstalledPackageModel> sortByName(List<InstalledPackageModel> modelList){
        List<InstalledPackageModel> result = new ArrayList<>(modelList);
        Collections.sort(result, new Comparator<InstalledPackageModel>() {
            @Override
            public int compare(InstalledPackageModel o1, InstalledPackageModel o2) {
                return o1.getAppName().compareTo(o2.getAppName());
            }
        });
        return result;
    }

    private List<InstalledPackageModel> createTestData(){
        List<InstalledPackageModel> testData = new ArrayList<>();
        testData.add(new InstalledPackageModel("Sberbank", "Package", null, false));
        testData.add(new InstalledPackageModel("Test", "TPackage", null, true));
        testData.add(new InstalledPackageModel("Test2", "NewPackage", null, true));
        return testData;
    }
}