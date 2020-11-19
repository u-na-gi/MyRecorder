package com.example.ru_1218.myrecoder


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter

class PlayBackActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_back)

        //録音メイン画面から取得したファイルを表示
        val fileVoice = intent.getSerializableExtra("voice_file") as ArrayList<*>


        val voiceName = findViewById<ListView>(R.id.voiceListMenu)
        voiceName.onItemClickListener = ListItemClickListener(fileVoice)

        val VoiceList: MutableList<MutableMap<String,String>> = mutableListOf()
        //simpleadapterで使用するMutableObjectを用意



        fileVoice.forEach { path ->
            print(path)
            var path = path as String
            var voiceMenu = getFileName(path)
            VoiceList.add(voiceMenu)
        }


        val from = arrayOf("name")

        val to = intArrayOf(android.R.id.text1)

        //simple adapter
        val adapter = SimpleAdapter(applicationContext, VoiceList, android.R.layout.simple_list_item_2, from, to)
        //アダプタの登録
        voiceName.adapter = adapter

    }

    private fun getFileName(path: String): MutableMap<String, String> {
        var file: List<String> = path.split("/")
        var fileName = file.takeLast(1).first()
        var uuid: String = fileName.replace(".3gp", "")
        var voiceMenu = mutableMapOf("name" to uuid)
        return voiceMenu
    }

    private fun checkFileName(path: String): String{
        var file: List<String> = path.split("/")
        var fileName = file.takeLast(1).first()
        var uuid: String = fileName.replace(".3gp", "")
        return uuid
    }



    private inner class ListItemClickListener(dataList: ArrayList<*>) : AdapterView.OnItemClickListener {
        private var list: ArrayList<*> = dataList

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val item= parent?.getItemAtPosition(position) as MutableMap<String, String>

            val uuid = item["name"] as String
            val dataPath = sameNameFilePath(list, uuid)
            //ダイアログフラグメントオブジェクトの作成
            val dialogFragment = DialogEvent(dataPath,uuid)
            dialogFragment.show(supportFragmentManager, "DialogEvent")

        }

        private fun sameNameFilePath(datas: ArrayList<*>, fileName: String): String? {
            datas.forEach { path ->
                print(path)
                var path = path
                var voiceMenu: String = checkFileName(path as String)
                if (voiceMenu == fileName) {
                    return path
                }

            }
            return "teset"
        }

    }

}