package com.mobilesolutionworks.android.controller.samples.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.mobilesolutionworks.android.app.controller.HostWorksController
import com.mobilesolutionworks.android.app.controller.WorksControllerManager
import com.mobilesolutionworks.android.controller.samples.databinding.CellDemoItemBinding
import com.mobilesolutionworks.android.controller.samples.ui.databinding.DataBinding
import java.util.*

/**
 * Created by yunarta on 17/3/17.
 */

class MainActivityController(manager: WorksControllerManager) : HostWorksController<MainActivity>(manager) {

    private var mAdapter: DemoItemAdapter? = null

    override fun onCreate(arguments: Bundle?) {
        super.onCreate(arguments)

        mAdapter = DemoItemAdapter()
    }

    fun onItemSelected(item: DemoItem) {
        context.startActivity(item.intent)
    }

    val adapter: RecyclerView.Adapter<*>?
        get() = mAdapter

    private inner class DemoItemAdapter : DataBinding.SingleTypeAdapter<Void, CellDemoItemBinding>() {

        private val mItems: MutableList<DemoItem>

        override fun getItemLayout(): Int = R.layout.cell_demo_item

        init {
            val packageName = context.packageName
            mItems = ArrayList<DemoItem>()

            mItems.add(DemoItem(getString(R.string.demo1_title), getString(R.string.demo1_subtitle), Intent(packageName + ".action.DEMO1")))
            mItems.add(DemoItem(getString(R.string.demo2_title), getString(R.string.demo2_subtitle), Intent(packageName + ".action.DEMO2")))
        }

        override fun onBindViewHolder(holder: DataBinding.ViewHolder<Void, CellDemoItemBinding>, position: Int) {
            val binding = holder.binding
            binding.item = mItems[position]
            binding.controller = this@MainActivityController
        }

        override fun getItemCount(): Int {
            return mItems.size
        }
    }
}
