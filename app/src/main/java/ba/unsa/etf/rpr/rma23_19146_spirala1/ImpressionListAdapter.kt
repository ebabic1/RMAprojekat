package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ImpressionListAdapter (
    private var impressions: List<UserImpression>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    inner class ReviewViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val movieReview : TextView = itemView.findViewById(R.id.review_textview)
        val userName : TextView = itemView.findViewById(R.id.username_textview)
    }
    inner class RatingViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val movieRating : RatingBar = itemView.findViewById(R.id.rating_bar)
        val userName : TextView = itemView.findViewById(R.id.username_textview)
    }
    override fun getItemViewType(position: Int): Int {
        if(impressions[position] is UserReview) return 0
        else if (impressions[position] is  UserRating) return 1
        return 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) return ReviewViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_impression,parent,false))
        else if (viewType == 1) return  RatingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rating,parent,false))
        else throw java.lang.IllegalArgumentException("Invalid item type")
    }
    override fun getItemCount(): Int = impressions.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (impressions[position] is UserRating){
            val rating: UserRating = impressions[position] as UserRating
            (holder as RatingViewHolder).userName.text = impressions[position].username
            (holder as RatingViewHolder).movieRating.rating = rating.rating.toFloat()
        }
        else if (impressions[position] is UserReview){
            val review : UserReview = impressions[position] as UserReview
            (holder as ReviewViewHolder).userName.text = impressions[position].username
            (holder as ReviewViewHolder).movieReview.text = review.review
        }
    }
    fun updateImpressions(impressions: List<UserImpression>){
        this.impressions = impressions.sortedByDescending { it.timestamp }
        notifyDataSetChanged()
    }
}