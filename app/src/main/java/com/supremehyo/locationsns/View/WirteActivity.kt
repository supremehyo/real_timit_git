package com.supremehyo.locationsns.View

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.ncorti.slidetoact.SlideToActView
import com.supremehyo.locationsns.Base.BaseKotlinActivity
import com.supremehyo.locationsns.BuildConfig
import com.supremehyo.locationsns.DTO.ContentDTO
import com.supremehyo.locationsns.Dialog.SelectCameraDialog
import com.supremehyo.locationsns.R
import com.supremehyo.locationsns.Util.GlideUtil
import com.supremehyo.locationsns.Util.RxEventBusHelper
import com.supremehyo.locationsns.ViewModel.WriteViewModel
import com.supremehyo.locationsns.databinding.ActivityWirteBinding
import kotlinx.android.synthetic.main.activity_wirte.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class WirteActivity : BaseKotlinActivity<ActivityWirteBinding, WriteViewModel>() {

    private val REQ_GALLERY = 1
    private val REQ_STORAGE_PERMISSION = 2
    private val REQ_CAMERA_PERMISSION = 3
    private val REQ_IMAGE_CAPTURE = 4
    var imagePath : String = ""
    lateinit var content_write_iv : ImageView
    override val viewModel: WriteViewModel by viewModel() // Koin 으로 의존성 주입
    override val layoutResourceId: Int
        get() = R.layout.activity_wirte

    override fun initStartView() {

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        content_write_iv = findViewById(R.id.content_write_iv)
        content_write_iv.setOnClickListener {
            val selectCameraDialog = SelectCameraDialog(this)
            selectCameraDialog.show()
            //Rx로 이벤트 버스 적용
            RxEventBusHelper.mSubject
                .subscribe {
                    when (it) {
                        "카메라" -> selectCamera()
                    }
                    if(it.equals("갤러리")){
                        selectGallery()
                    }
                }
        }

        post_slide.onSlideCompleteListener =
            object : SlideToActView.OnSlideToActAnimationEventListener,
                SlideToActView.OnSlideCompleteListener {
               // var dto: ContentDTO = ContentDTO("sdf", content_write_et.text.toString(), "", "")
                override fun onSlideComplete(view: SlideToActView) {
                    Log.v("sfsdfs", "zzzz")
                //    viewModel.writeContent(dto)
                }

                override fun onSlideCompleteAnimationEnded(view: SlideToActView) {

                }

                override fun onSlideCompleteAnimationStarted(
                    view: SlideToActView,
                    threshold: Float
                ) {

                }

                override fun onSlideResetAnimationEnded(view: SlideToActView) {

                }

                override fun onSlideResetAnimationStarted(view: SlideToActView) {

                }
            }
    }

    //https://superwony.tistory.com/101
    private fun selectGallery() {
        Log.v("asdfsf" , "들어옴1")
        var writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (writePermission == PackageManager.PERMISSION_DENIED || readPermission == PackageManager.PERMISSION_DENIED) {
            // 권한 없어서 요청
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), REQ_STORAGE_PERMISSION)
        } else {
            // 권한 있음
            Log.v("asdfsf" , "들어옴else")
            var intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(intent, REQ_GALLERY)
        }
    }
    private fun selectCamera() {
        var permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA
        )
        if (permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQ_CAMERA_PERMISSION
            )
        } else { // 권한 있음
            var state = Environment.getExternalStorageState()
            if (TextUtils.equals(state, Environment.MEDIA_MOUNTED)) {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.resolveActivity(packageManager)?.let{
                    var photoFile: File? = createImageFile()
                    photoFile?.let{
                        Log.v("sdfasd", "222")
                        var photoUri = FileProvider.getUriForFile(this, "com.supremehyo.locationsns.provider", it)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(intent, REQ_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }
    private fun getRealPathFromURI(uri: Uri): String? {
        Log.v("asdfsf" , "들어옴3")
        var buildName = Build.MANUFACTURER
        if (buildName.equals("Xiaomi")) {
            return uri.path
        }
        var columnIndex = 0
        var proj = arrayOf(MediaStore.Images.Media.DATA)
        var cursor = contentResolver.query(uri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            }
        }
        return cursor?.getString(columnIndex)
    }

    private fun getResizePicture(imagePath: String): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imagePath, options)
        var resize = 1000
        var width = options.outWidth
        var height = options.outHeight
        var sampleSize = 1
        while (true) {
            if (width / 2 < resize || height / 2 < resize)
                break
            width /= 2
            height /= 2
            sampleSize *= 2
        }
        options.inSampleSize = sampleSize
        options.inJustDecodeBounds = false
        var resizeBitmap = BitmapFactory.decodeFile(
            imagePath,
            options
        ) // 회전값 조정
        var exit = ExifInterface(imagePath)
        var exifDegree = 0
        exit?.let {
            var exifOrientation =
                it.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            exifDegree = exifOreintationToDegrees(exifOrientation)
        }
        return roteateBitmap(resizeBitmap, exifDegree)
    }

    private fun exifOreintationToDegrees(exifOrientation: Int): Int {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270
        }
        return 0;
    }

    private fun roteateBitmap(src: Bitmap, degree: Int): Bitmap { // Matrix 객체 생성
        var matrix: Matrix = Matrix()
        // 회전 각도 셋팅
        matrix.postRotate(degree.toFloat());
        //이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    private fun galleryAddPic() {
        Log.i("galleryAddPic", "Call");
        val mediaScanIntent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        val f: File = File(imagePath);
        val contentUri: Uri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }
    private fun createImageFile(): File
    { // 사진이 저장될 폴더 있는지 체크
        var file = File("/storage/emulated/0/Android/data/com.supremehyo.locationsns/", "/files/Pictures")
        if (!file.exists()){
            Log.v("sdfasd", "333")
            file.mkdir()
        }
        val calendar:Calendar = Calendar.getInstance()
        var date : String = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z",Locale.ENGLISH).format(calendar.timeInMillis)
        var imageName = date+".jpeg"
        var imageFile = File("/storage/emulated/0/Android/data/com.supremehyo.locationsns/files/Pictures/", "$imageName")
        imagePath = imageFile.absolutePath
        return imageFile
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)

            when (requestCode)
            {
                REQ_IMAGE_CAPTURE->
                {
                        Glide.with(applicationContext)
                            .load(imagePath)
                            .into(content_write_iv)
                    galleryAddPic()
                }
                REQ_GALLERY ->{
                    Log.v("asdfsf" , "들어옴2")
                    data?.data?.let { it ->
                        imagePath = getRealPathFromURI(it)!!
                        Glide.with(applicationContext)
                            .load(imagePath)
                            .into(content_write_iv)
                    }
                }
            }

    }
}