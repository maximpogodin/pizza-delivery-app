import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_society.R
import com.example.delivery_society.model.entities.Dish
import com.example.delivery_society.model.entities.OrderDTO

class OrderHistoryAdapter (var list: ArrayList<OrderDTO>, var employeeMode: Boolean, val callback: Callback) : RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView = view.findViewById(R.id.text_title) as TextView
        var cardView: CardView = view.findViewById(R.id.card_view) as CardView
        var textDescription: TextView = view.findViewById(R.id.text_conditions) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.large_card_view, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = list[position]
        viewHolder.textTitle.text = "№ заказа: ${item.order_id}"
        viewHolder.textDescription.text = "Состав заказа:\n"
        var orderItems = item.order_items.iterator()
        orderItems.forEach {
            viewHolder.textDescription.text = "${viewHolder.textDescription.text}${it.dish_name} ${it.dish_size} см (${it.quantity} шт.) по цене (${it.dish_price}) = ${it.dish_price * it.quantity}\n"
        }

        viewHolder.textDescription.text = "${viewHolder.textDescription.text}Сумма заказа: ${item.order_items?.sumOf { (it.quantity * it.dish_price) }}\n${item.status_name}"
        viewHolder.cardView.setOnClickListener { callback.onItemClicked(item) }
        viewHolder.cardView.isEnabled = employeeMode
    }

    interface Callback {
        fun onItemClicked(item: OrderDTO)
    }
}