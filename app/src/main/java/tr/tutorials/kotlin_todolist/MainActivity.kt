package tr.tutorials.kotlin_todolist

import android.R
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import tr.tutorials.kotlin_todolist.databinding.ActivityMainBinding
import java.io.Console
import kotlin.math.log

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)


		val dbhandler: DatabaseHandler = DatabaseHandler(this)
		dbhandler.firstdata()

		refreshListview(dbhandler.viewTODO())


		binding.button.setOnClickListener {

			val errname = dbhandler.addTODO(contentModelClass(1, (binding.inputText.text).toString()))

			if (errname < 0) {
				val alerd = AlertDialog.Builder(this@MainActivity)
				alerd.setMessage("$errname : hata kodu")
				alerd.create().show()
			}
			refreshListview(dbhandler.viewTODO())
			binding.inputText.setText("")
		}

		binding.mylistviev.setOnItemClickListener { parent, _, position, _->
			val element = parent.getItemAtPosition(position)
			val areusure = AlertDialog.Builder(this@MainActivity)
			areusure.setMessage("Bu elemanı silmek istediğinizden emin misiniz : $element")
				.setPositiveButton("Evet"){ _, _ ->
					dbhandler.deleteTODO(contentModelClass(1, element.toString()))
					//refreshListview(dbhandler.viewTODO())
				}
				.setNegativeButton("Hayır") { dialog, _->
					dialog.dismiss()
				}
			areusure.create().show()
		}

	}

	private fun refreshListview(mylist: MutableList<String>)
	{
		if (mylist == null)
			return
		val listviewAdapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, mylist)
		binding.mylistviev.adapter = listviewAdapter
	}
}