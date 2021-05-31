package uz.texnopos.firestoredatatorecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.texnopos.firestoredatatorecyclerview.databinding.ItemBinding

class MyAdapter: RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun populateModel(user: User){
            binding.tvFirstName.text = user.firstName
            binding.tvLastName.text = user.lastName
            binding.tvAge.text = user.age.toString()
        }
    }

    var models: MutableList<User> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.populateModel(models[position])
    }

    override fun getItemCount(): Int {
        return models.size
    }

    fun add(it: User) {
        models.add(it)
        notifyItemInserted(models.lastIndex)
    }

    fun modify(it: User) {
        val curUser = models.filter { user ->
            user.id == it.id
        }[0]
        val index = models.indexOf(curUser)
        models[index] = it
        notifyItemChanged(index)
    }

    fun remove(it: User) {
        val index = models.indexOf(it)
        models.remove(it)
        notifyItemRemoved(index)
    }
}