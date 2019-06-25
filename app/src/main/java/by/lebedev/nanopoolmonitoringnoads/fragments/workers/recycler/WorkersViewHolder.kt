package by.lebedev.nanopoolmonitoringnoads.fragments.workers.recycler

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import by.lebedev.nanopoolmonitoringnoads.R
import by.lebedev.nanopoolmonitoringnoads.dagger.TabIntent
import by.lebedev.nanopoolmonitoringnoads.dagger.provider.DaggerMagicBox
import by.lebedev.nanopoolmonitoringnoads.retrofit.entity.workers.DataWorkers
import javax.inject.Inject


class WorkersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val workerName: TextView
    private val workerNumber: TextView
    private val lastShareTime: TextView
    private val workerHashrate: TextView

    @Inject
    lateinit var tabIntent: TabIntent

    init {

        workerNumber = itemView.findViewById(R.id.workerNumber)
        workerName = itemView.findViewById(R.id.workerName)
        lastShareTime = itemView.findViewById(R.id.lastShareTime)
        workerHashrate = itemView.findViewById(R.id.workerHashrate)

        val component = DaggerMagicBox.builder().build()
        tabIntent = component.provideTabIntent()
    }

    fun bind(workers: DataWorkers, num: Int) {
        workerName.setText(workers.id)
        workerNumber.setText((num + 1).toString())
        lastShareTime.setText(workers.convertLongToTime(workers.lastShare))
        workerHashrate.setText(workers.hashrate.toString().plus(tabIntent.getWorkerHashType(tabIntent.coin)))
        if (workers.hashrate > 0) {
            workerName.setTextColor(Color.parseColor("#90000000"))
            lastShareTime.setTextColor(Color.parseColor("#90000000"))

            workerHashrate.setTextColor(Color.parseColor("#37B110"))
        } else {
            workerName.setTextColor(Color.parseColor("#D41611"))
            lastShareTime.setTextColor(Color.parseColor("#D41611"))
            workerHashrate.setTextColor(Color.parseColor("#D41611"))
        }

    }
}