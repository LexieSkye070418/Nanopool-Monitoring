package by.lebedev.nanopoolmonitoring.fragments.workers.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import by.lebedev.nanopoolmonitoring.retrofit.entity.workers.DataWorkers

class WorkersAdapter(
    private val workers: ArrayList<DataWorkers>) : RecyclerView.Adapter<WorkersViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): WorkersViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_worker, viewGroup, false)
        val holder = WorkersViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(workersViewHolder: WorkersViewHolder, id: Int) {

        workersViewHolder.bind(workers.get(id),id)
    }

    override fun getItemCount(): Int {
        return workers.size
    }
}