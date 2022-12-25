package uz.salikhdev.seyg.ui.main.chat.voice

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.salikhdev.seyg.R
import uz.salikhdev.seyg.core.adapter.chat.ChatVoiceAdapter
import uz.salikhdev.seyg.core.app.App
import uz.salikhdev.seyg.core.base.BaseFragment
import uz.salikhdev.seyg.core.cache.LocalStorage
import uz.salikhdev.seyg.core.utils.showToast
import uz.salikhdev.seyg.databinding.ScreenVoiceBinding
import uz.salikhdev.seyg.ui.main.ChatViewModel
import java.io.File
import java.util.*


class VoiceScreen : BaseFragment(R.layout.screen_voice) {

    private val binding by viewBinding(ScreenVoiceBinding::bind)
    private val args: VoiceScreenArgs by navArgs()
    private val MICROPHONE_CODE = 200
    private var spech: TextToSpeech? = null
    private var isStart = false

    private val chatVM: ChatViewModel by viewModels()
    private val adapter by lazy { ChatVoiceAdapter() }
    private val localStorage = LocalStorage()
    private var audioPath: String? = null

    override fun onCreated(view: View, savedInstanceState: Bundle?) {

        setState()
        setSpach()
        setPermission()
        setListener()
        observe()
    }

    private fun setState() {
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(context)
    }

    private fun setPermission() {
        if (isMicrophonePresent()) {
            getMicrophonePermission()
        }
    }

    private fun observe() {

        callAsynchronousTask()

        chatVM.chatLD.observe(viewLifecycleOwner) {
            adapter.setData(it!!, localStorage.userID)
        }

    }

    fun callAsynchronousTask() {
        val handler = Handler()
        val timer = Timer()
        val doAsynchronousTask: TimerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    try {
                        chatVM.getChat(args.id)
                    } catch (e: Exception) {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        timer.schedule(doAsynchronousTask, 0, 1000) //execute in every 50000 ms
    }

    private fun setSpach() {

        spech = TextToSpeech(requireContext()) { status ->

            if (status == TextToSpeech.SUCCESS) {
                spech?.language = Locale.ENGLISH
                speach(args.title + " kantakti tanlandi")

            }

        }


    }

    private fun setListener() {

        binding.recorder.setOnClickListener {

            val recorder = MediaRecorder()

            if (isStart) {
                speach("Ovoz jo'natildi")
                binding.recorder.setImageResource(R.drawable.voic_bluee)

                try {
                    recorder.stop()
                    recorder.release()
                    showToast(audioPath.toString())
                } catch (e: Exception) {
                    showToast(e.toString())
                }

                isStart = false
            } else {
                speach("Ovoz yozilmoqda")
                binding.recorder.setImageResource(R.drawable.send)

                try {
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

                    val file = File(Environment.getExternalStorageDirectory().absolutePath,
                        "chatme/media/recording")

                    if (!file.exists())
                        file.mkdirs()

                    audioPath =
                        file.absolutePath + File.separator + System.currentTimeMillis() + "3gp"

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        recorder.setOutputFile(file)
                    }

                    recorder.prepare()
                    recorder.start()
                    showToast("Recording started")


                } catch (e: Exception) {
                    showToast(e.toString())
                }

                isStart = true
            }

        }

    }

    private fun speach(text: String) {
        spech?.speak(text, TextToSpeech.QUEUE_FLUSH, null)

    }


    private fun isMicrophonePresent(): Boolean {
        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            true
        } else {
            true
        }
    }

    private fun getMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MICROPHONE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                showToast("Yozildi")

            } else if (resultCode == RESULT_CANCELED) {
                // Oops! User has canceled the recording
            }
        }
    }

    private fun getRecordingFilePath(): String {
        val contextWrapper = ContextWrapper(App.instance)
        val musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val file = File(musicDirectory, "record" + ".mp3")
        return file.path
    }

    override fun onDestroy() {
        super.onDestroy()
        if (spech != null) {
            spech?.stop()
            spech?.shutdown()
        }
    }

}