package tr.tutorials.kotlin_todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tr.tutorials.kotlin_todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)



	}
}