package com.example.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Layout
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import java.security.PrivateKey


const val YOUTUBE_VIDEO_ID ="k2cRfxWUFco"
const val YOUTUBE_PLAYLIST = "PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10"


class Youtube_Activity2 : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private val TAG="YoutubeActivity"
    private val DIALOG_REQUEST_CODE= 1
    val playerView by lazy {  YouTubePlayerView (this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_2)
        val layout = findViewById<ConstraintLayout>(R.id.activity_youtube)

//        val button1= Button(this)
//        button1.layoutParams=  ConstraintLayout.LayoutParams(600,180)
//        button1.text= "Button Added"
//        layout.addView(button1)


        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)


        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)

    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youTubePlayer : YouTubePlayer?,
        wasRestored: Boolean

    ) {
        Log.d(TAG, " onInitializationSucces: provider is ${provider?.javaClass}")
        Log.d(TAG , "onInitializationSucces: youTubePlayer ${youTubePlayer?.javaClass}")
        Toast.makeText(this, "initialized YouTube Player succesfully", Toast.LENGTH_SHORT ).show()
        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListner)
        youTubePlayer?.setPlaybackEventListener(playbackEventListener)
        if (!wasRestored){
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        }else{
            youTubePlayer?.play()
        }
//
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youTubeInitializationResult: YouTubeInitializationResult?
    ) {
         if( youTubeInitializationResult?.isUserRecoverableError== true){
             youTubeInitializationResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()

         }else {
             val errorMessage= "there was an error initializing the youtubePlayer($youTubeInitializationResult)"
             Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
         }



    }
    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener{
        override fun onSeekTo(p0: Int) {

        }

        override fun onBuffering(p0: Boolean) {

        }

        override fun onPlaying() {
            Toast.makeText(this@Youtube_Activity2, "good, video is playing okay", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
            Toast.makeText(this@Youtube_Activity2, "video has stopped", Toast.LENGTH_SHORT).show()

        }

        override fun onPaused() {
            Toast.makeText(this@Youtube_Activity2, "video has paused", Toast.LENGTH_SHORT).show()
        }
    }
    private val playerStateChangeListner =object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@Youtube_Activity2, "Click ad Now, make the video creator rich", Toast.LENGTH_SHORT).show()

        }

        override fun onLoading() {

        }

        override fun onVideoStarted() {
            Toast.makeText(this@Youtube_Activity2, "Video has started", Toast.LENGTH_SHORT).show()

        }

        override fun onLoaded(p0: String?) {

        }

        override fun onVideoEnded() {
            Toast.makeText(this@Youtube_Activity2, "congratulations you have completed another video", Toast.LENGTH_SHORT).show()

        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "OnActivityResult Called with Response code $resultCode for  request $requestCode ")
        if (requestCode==DIALOG_REQUEST_CODE){
            Log.d(TAG, intent.toString())
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
        }
    }
}