package tr.tutorials.kotlin_todolist

import android.R
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import tr.tutorials.kotlin_todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		val	myTodolist: MutableList<String> = mutableListOf()
		refreshListview(myTodolist)

		binding.button.setOnClickListener {
			myTodolist.add((binding.inputText.text).toString())
			refreshListview(myTodolist)
			binding.inputText.setText("")
		}

		binding.mylistviev.setOnItemClickListener { parent, _, position, _->
			val element = parent.getItemAtPosition(position)
			val areusure = AlertDialog.Builder(this@MainActivity)
			areusure.setMessage("Bu elemanı silmek istediğinizden emin misiniz : $element")
				.setPositiveButton("Evet"){ _, _ ->
					myTodolist.remove(element)
					refreshListview(myTodolist)
				}
				.setNegativeButton("Hayır") { dialog, _->
					dialog.dismiss()
				}
			areusure.create().show()
		}

	}

	private fun refreshListview(mylist:MutableList<String>)
	{
		val listviewAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, mylist)
		binding.mylistviev.adapter = listviewAdapter
	}
}