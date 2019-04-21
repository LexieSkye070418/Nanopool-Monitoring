package by.lebedev.nanopoolmonitoring.activities.course

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import by.lebedev.nanopoolmonitoring.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_coin.view.*

class CoinAdapter(val list: List<CoinCap>) : RecyclerView.Adapter<MyViewHolder>() {

    var mData: List<CoinCap>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_coin, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        list.let {
            val coin = it[position]
            holder.view.tvSimbol.text = coin.symbol
            holder.view.tvName.text = coin.id
            holder.view.tvPriceChange.text = if (coin.percent_change_24h > 0) "+${coin.percent_change_24h}%" else {
                "${coin.percent_change_24h}%"
            }
            holder.view.tvPrice.text = "${coin.price_usd}"


            Picasso.get().load(coin.url.toString())
                .into(holder.view.url)

        }
    }
}