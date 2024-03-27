import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_society.R
import com.example.delivery_society.model.entities.Dish

class DishListAdapter (var list: ArrayList<Dish>, val callback: Callback, val context: Context) : RecyclerView.Adapter<DishListAdapter.ViewHolder>() {
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view)
    {
        var textTitle: TextView = view.findViewById(R.id.text_title) as TextView
        var cardView: CardView = view.findViewById(R.id.card_view) as CardView
        var textDescription: TextView = view.findViewById(R.id.text_conditions) as TextView
        var imageView: ImageView = view.findViewById(R.id.image) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_with_image_view, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var item = list[position]
        viewHolder.textTitle.text = item.name
        viewHolder.cardView.setOnClickListener { callback.onItemClicked(item) }
        viewHolder.textDescription.text = item.description
        val imageResource = stringToImageResource(context, item.photo)
        viewHolder.imageView.setImageResource(imageResource)
        val layoutParams = viewHolder.imageView.layoutParams
        layoutParams.height = 500
        layoutParams.width = 500

        if (!item.available) {
            viewHolder.textDescription.text = "${viewHolder.textDescription.text}\nНет в наличии"
        }

        viewHolder.cardView.isEnabled = item.available
    }

    interface Callback {
        fun onItemClicked(item: Dish)
    }

    fun stringToImageResource(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }
}