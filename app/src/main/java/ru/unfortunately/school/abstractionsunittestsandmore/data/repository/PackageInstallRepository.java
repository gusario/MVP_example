package ru.unfortunately.school.abstractionsunittestsandmore.data.repository;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import ru.unfortunately.school.abstractionsunittestsandmore.R;
import ru.unfortunately.school.abstractionsunittestsandmore.data.model.InstalledPackageModel;

public class PackageInstallRepository {

    private final Context mContext;
    private final PackageManager mPackageManager;

    public PackageInstallRepository(@NonNull Context context) {
        mContext = context;
        mPackageManager = context.getPackageManager();
    }

    public List<InstalledPackageModel> getData(boolean isSystem){
        List<InstalledPackageModel> installedPackageModels = new ArrayList<>();

        for(ResolveInfo resolveInfo : getResolveInfosForInstalledPackages(isSystem)){
            InstalledPackageModel installedPackageModel = new InstalledPackageModel(
                    getAppName(resolveInfo),
                    getPackageName(resolveInfo),
                    getAppIcon(resolveInfo),
                    isSystemPackage(resolveInfo)
            );
            installedPackageModels.add(installedPackageModel);
        }
        return installedPackageModels;
    }

    private List<ResolveInfo> getResolveInfosForInstalledPackages(boolean isSystem){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> result = new ArrayList<>();

        List<ResolveInfo> resolveInfoList = mPackageManager.queryIntentActivities(intent, 0);

        for (ResolveInfo resolveInfo: resolveInfoList){
            if(isSystem || !isSystemPackage(resolveInfo)){
                result.add(resolveInfo);
            }
        }
        return result;
    }

    private String getPackageName(@NonNull ResolveInfo resolveInfo){
        ActivityInfo info = resolveInfo.activityInfo;
        return info.applicationInfo.packageName;
    }

    private boolean isSystemPackage(@NonNull ResolveInfo resolveInfo){
        return ((resolveInfo.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private String getAppName(@NonNull ResolveInfo resolveInfo){
        return getAppName(getPackageName(resolveInfo));
    }

    private String getAppName(@NonNull String packageName){
        String appName = "";
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
            appName = (String) mPackageManager.getApplicationLabel(applicationInfo);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }
        return appName;
    }

    private Drawable getAppIcon(@NonNull ResolveInfo resolveInfo){
        return getAppIcon(getPackageName(resolveInfo));
    }

    private Drawable getAppIcon(@NonNull String packageName){
        Drawable drawable;
        try {
            drawable = mPackageManager.getApplicationIcon(packageName);

        }catch (PackageManager.NameNotFoundException e ){
            e.printStackTrace();
            drawable = ContextCompat.getDrawable(mContext, R.mipmap.ic_launcher);
        }
        return drawable;
    }
}
