package com.mobilesolutionworks.android.controller.samples.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.mobilesolutionworks.android.controller.HostWorksController;
import com.mobilesolutionworks.android.controller.samples.R;
import com.mobilesolutionworks.android.controller.samples.databinding.CellDemoItemBinding;
import com.mobilesolutionworks.android.controller.samples.ui.databinding.DataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunarta on 17/3/17.
 */

public class MainActivityController extends HostWorksController<MainActivity> {

    private DemoItemAdapter mAdapter;

    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);

        mAdapter = new DemoItemAdapter();
    }

    public void onItemSelected(DemoItem item) {
        getContext().startActivity(item.intent);
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    private class DemoItemAdapter extends DataBinding.SingleTypeAdapter<Void, CellDemoItemBinding> {

        private final List<DemoItem> mItems;

        public DemoItemAdapter() {
            String packageName = getContext().getPackageName();
            mItems = new ArrayList<>();

            mItems.add(new DemoItem(getString(R.string.demo1_title), getString(R.string.demo1_subtitle), new Intent(packageName + ".action.DEMO1")));
            mItems.add(new DemoItem(getString(R.string.demo2_title), getString(R.string.demo2_subtitle), new Intent(packageName + ".action.DEMO2")));
        }

        @Override
        public void onBindViewHolder(DataBinding.ViewHolder<Void, CellDemoItemBinding> holder, int position) {
            CellDemoItemBinding binding = holder.getBinding();
            binding.setItem(mItems.get(position));
            binding.setController(MainActivityController.this);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        protected int getItemLayout() {
            return R.layout.cell_demo_item;
        }
    }
}
