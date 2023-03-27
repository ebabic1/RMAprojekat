package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImpressionListAdapter (
    private var impressions: List<UserImpression>) : RecyclerView.Adapter<ImpressionListAdapter.ImpressionViewHolder>()
{
    inner class ImpressionViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val movieRating : RatingBar = itemView.findViewById(R.id.rating_bar)
        val movieReview : TextView = itemView.findViewById(R.id.review_textview)
        val userName : TextView = itemView.findViewById(R.id.username_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImpressionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_impression,parent,false)
        return ImpressionViewHolder(view)
    }
    override fun getItemCount(): Int = impressions.size

    override fun onBindViewHolder(holder: ImpressionViewHolder, position: Int) {
        holder.userName.text = impressions[position].username
        if (impressions[position] is UserRating){
            val rating: UserRating = impressions[position] as UserRating
            holder.movieReview.visibility = View.GONE
            holder.movieRating.visibility = View.VISIBLE
            holder.movieRating.rating = rating.rating.toFloat()
        }
        else{
            val review : UserReview = impressions[position] as UserReview
            holder.movieReview.visibility = View.VISIBLE
            holder.movieRating.visibility = View.GONE
            holder.movieReview.text = review.review
        }
    }
    fun updateImpressions(impressions: List<UserImpression>){
        this.impressions = impressions.sortedByDescending { it.timestamp }
        notifyDataSetChanged()
    }
}