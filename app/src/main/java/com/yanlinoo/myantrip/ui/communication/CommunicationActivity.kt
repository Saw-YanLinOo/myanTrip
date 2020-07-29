package com.yanlinoo.myantrip.ui.communication

import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import co.metalab.asyncawait.async
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yanlinoo.myantrip.R
import com.yanlinoo.myantrip.api.Api
import com.yanlinoo.myantrip.api.ServiceGenerator
import com.yanlinoo.myantrip.model.WordItem
import com.yanlinoo.myantrip.util.unzip
import kotlinx.android.synthetic.main.activity_communication.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class CommunicationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_communication)

        val gson:Gson = GsonBuilder().create()
        val word = gson.fromJson<WordItem>(intent.getStringExtra("Word"),WordItem::class.java)
        if (word != null){
            Toast.makeText(this,word.eng+"==>"+word.myn,Toast.LENGTH_SHORT).show()
        }
//        tv_eng.text = word.eng
//        tv_myn.text = word.myn

        btn_speak.setOnClickListener {
            //progress_bar.visibility = View.VISIBLE
            Log.e(this.toString(),"Speak!! vyuZe4eeeedrc v v [[v v v v v v v v v v v v v veee e ")
            if(File("/storage/emulated/0/Android/data/com.yanlinoo.myantrip/files/movice").exists()){
                Log.e("File!!!!!!!!!","File exit")
            }else{
                if (isStoragePermissionGranted()){
                    async{
                        rl_progress.visibility = View.VISIBLE
                        await { downloadFile() }
                    }
                }
            }

        }

        val adapter = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,CommunicationFragment().languageList)
        spinner_languages.adapter = adapter
        spinner_languages.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }
        }

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }

    fun downloadFile() {
        Log.d("downloadFile", "start")

        val restApi = ServiceGenerator.buildService(Api::class.java)
        val call = restApi.downloadFileWithFixedUrl()

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?,response: Response<ResponseBody>?) {
                try {
                    if (response?.isSuccessful == true) {
                        val execute = object : AsyncTask<Void, Void, Void>() {
                            override fun doInBackground(vararg voids: Void): Void? {
                                val writtenToDisk = writeResponseBodyToDisk(response.body(), "movice.zip")
                                Log.d("download", "file download was a success? $writtenToDisk")
                                Log.d("downloadFile", "sucess")

                                val zipFile =File(File(getExternalFilesDir(null).toString() + File.separator + "movice.zip").path.toString())
                                zipFile.unzip()
                                Log.d("downloadFile","extra success!!!")
                                return null
                            }

                            override fun onPostExecute(result: Void?) {
                                super.onPostExecute(result)
                                rl_progress.visibility = View.GONE
                            }
                        }.execute()
                    }

                    Log.d("onResponse", "Response came from server")

                } catch (e: IOException) {
                    Log.d("onResponse", "There is an error")
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Log.d("onFailure", t.toString())
            }
        })

    }

    private fun writeResponseBodyToDisk(body: ResponseBody?, fileName: String): Boolean {
        try {
            // todo change the file location/name according to your needs

            var futureStudioIconFile = File(getExternalFilesDir(null).toString() + File.separator + fileName)
            Log.e("futureStudioIconFile", futureStudioIconFile.path)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body?.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body?.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)
                    //Nó được sử dụng để trả về một ký tự trong mẫu ASCII. Nó trả về -1 vào cuối tập tin.
                    if (read == -1) {
                        break
                    }
                    outputStream!!.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                    Log.d("writeResponseBodyToDisk", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream!!.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream!!.close()
                }

                if (outputStream != null) {
                    outputStream!!.close()
                }
            }
        } catch (e: IOException) {
            return false
        }
    }

    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                false
            }
        } else {
            true
        }
    }
}