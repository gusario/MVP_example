package ru.unfortunately.school.abstractionsunittestsandmore.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.unfortunately.school.abstractionsunittestsandmore.R;
import ru.unfortunately.school.abstractionsunittestsandmore.data.model.InstalledPackageModel;

public class PackageInstalledAdapter extends RecyclerView.Adapter<PackageInstalledAdapter.PackageInstalledViewHolder> {

    private List<InstalledPackageModel> mInstalledPackageModelsList;

    public PackageInstalledAdapter(List<InstalledPackageModel> installedPackageModelsList) {
        mInstalledPackageModelsList = new ArrayList<>(installedPackageModelsList);
    }

    @NonNull
    @Override
    public PackageInstalledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PackageInstalledViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.package_installed_new_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PackageInstalledViewHolder holder, int position) {
        holder.bindView(mInstalledPackageModelsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mInstalledPackageModelsList.size();
    }

    static class PackageInstalledViewHolder extends RecyclerView.ViewHolder{

        private TextView mAppTextView;
        private TextView mPackageNameTextView;
        private ImageView mIconImageView;
        private ImageView mSystemIconImageView;

        public PackageInstalledViewHolder(@NonNull View itemView) {
            super(itemView);

            mAppTextView = itemView.findViewById(R.id.app_name_text_view);
            mPackageNameTextView = itemView.findViewById(R.id.app_package_text_view);
            mIconImageView = itemView.findViewById(R.id.app_icon_image_view);
            mSystemIconImageView = itemView.findViewById(R.id.system_icon);
        }

        public void bindView(@NonNull InstalledPackageModel installedPackageModel){
            mAppTextView.setText(installedPackageModel.getAppName());
            mPackageNameTextView.setText(installedPackageModel.getAppPackageName());
            mIconImageView.setImageDrawable(installedPackageModel.getAppIcon());
            if(installedPackageModel.isSystem()){
                mSystemIconImageView.setVisibility(View.VISIBLE);
            }else{
                mSystemIconImageView.setVisibility(View.GONE);
            }
        }
    }
}
