package tr.tutorials.kotlin_todolist

class contentModelClass (val fromuser: Int, val content: String){
	fun convertlist(lst: ArrayList<contentModelClass>): MutableList<String>{
		val ret: MutableList<String> = mutableListOf()

		for (x in lst){
			ret.add(x.content)
		}
		return ret
	}
}