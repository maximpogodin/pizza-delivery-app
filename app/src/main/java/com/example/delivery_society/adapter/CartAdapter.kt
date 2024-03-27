import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_society.R
import com.example.delivery_society.api.ApplicationDbContext
import com.example.delivery_society.model.entities.Cart
import com.example.delivery_society.model.entities.Dish
import com.example.delivery_society.model.entities.OrderDTO
import com.example.delivery_society.model.entities.OrderItem

class CartAdapter () : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var db = ApplicationDbContext()

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView = view.findViewById(R.id.text_title) as TextView
        var cardView: CardView = view.findViewById(R.id.card_view) as CardView
        var textDescription: TextView = view.findViewById(R.id.text_conditions) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.basic_card_view, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount() = Cart.orderItems.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = Cart.orderItems[position]
        val size = db.getSize(item.size_id)
        val dish = db.getDish(size!!.dish_id)
        viewHolder.textTitle.text = "${dish?.name} ${size?.size} см"
        viewHolder.textDescription.text = "Цена за ${item.quantity} шт.: ${item.quantity * size!!.price}\n"
    }
}