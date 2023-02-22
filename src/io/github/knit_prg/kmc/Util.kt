package io.github.knit_prg.kmc

import com.fasterxml.jackson.databind.JsonNode

class Util {
	companion object{
		public fun arrayNodeToStringArray(json:JsonNode):Array<String>{
			val arrayList = arrayListOf<String>()
			if(!json.isArray)return arrayList.toTypedArray()
			for(i in 0 until json.size()){
				json.get(i)?.asText()?.run{
					arrayList.add(this)
				}
			}
			return arrayList.toTypedArray()
		}
	}
}