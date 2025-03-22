package com.devby.yemekkitabi.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.room.Room
import com.devby.yemekkitabi.databinding.FragmentTarifBinding
import com.devby.yemekkitabi.model.Tarif
import com.devby.yemekkitabi.roomdb.TarifDAO
import com.devby.yemekkitabi.roomdb.TarifDatabase
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.IOException

class TarifFragment : Fragment() {
    private var _binding: FragmentTarifBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private var secilenGorsel: Uri? = null
    private var secilenBitmap: Bitmap? = null
    private lateinit var db: TarifDatabase
    private lateinit var tarifDAO: TarifDAO
    private val mDisposable = CompositeDisposable()
    private var secilenTarif: Tarif? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
        db = Room.databaseBuilder(requireContext(), TarifDatabase::class.java, "Tarifler").build()
        //.allowMainThreadQueries()
        tarifDAO = db.tarifDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTarifBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView.setOnClickListener { gorselSec(it) }
        binding.kaydetButton.setOnClickListener { kaydet(it) }
        binding.silButton.setOnClickListener { sil(it) }
        arguments?.let {
            val bilgi = TarifFragmentArgs.fromBundle(it).bilgi
            if (bilgi == "yeni") {
                //Yeni Tarif Ekle
                secilenTarif = null
                binding.silButton.isEnabled = false
                binding.kaydetButton.isEnabled = true
                binding.isimText.setText("")
                binding.malzemeText.setText("")
            } else {
                //Eski Eklenmiş Tarif gösteriliyor
                binding.silButton.isEnabled = true
                binding.kaydetButton.isEnabled = false
                val id = TarifFragmentArgs.fromBundle(it).id
                mDisposable.add(
                    tarifDAO.findById(id).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponse)
                )
            }
        }
    }

    fun kaydet(view: View) {
        val isim = binding.isimText.text.toString()
        val malzeme = binding.malzemeText.text.toString()
        if (secilenBitmap != null) {
            val kucukBitmp = kucukBitmapOlustur(secilenBitmap!!, 300)
            val outputStream = ByteArrayOutputStream()
            kucukBitmp.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byteDizisi = outputStream.toByteArray()

            val tarif = Tarif(isim, malzeme, byteDizisi)
            //Threading (background thread) /for kotlin -> RxJava or Couroutines  java -> Guava/LiveData
            //RXJAVA
            mDisposable.add(
                tarifDAO.insert(tarif).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleReponseForInsert)
            )
        }
    }

    private fun handleResponse(tarif: Tarif) {
        binding.isimText.setText(tarif.isim)
        binding.malzemeText.setText(tarif.malzeme)
        val bitmap = BitmapFactory.decodeByteArray(tarif.gorsel, 0, tarif.gorsel.size)
        binding.imageView.setImageBitmap(bitmap)
        secilenTarif = tarif
    }

    private fun handleReponseForInsert() {
        //bir önceki fragment'a dön
        val action = TarifFragmentDirections.actionTarifFragmentToListeFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    fun sil(view: View) {
        if (secilenTarif != null) {
            mDisposable.add(
                tarifDAO.delete(tarif = secilenTarif!!).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleReponseForInsert)
            )
        }
    }

    fun gorselSec(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) { //İZİN VERİLMEMİŞ, İZİN İSTEMEMİZ LAZIM
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_MEDIA_IMAGES
                    )
                ) {
                    //SNACKBAR GOSTERMEK LAZIM , KULLANICIDAN NEDEN IZIN ISTEDIGIMIZI BIR KEZ DAHA SOYLEYEREK IZIN ISTEMEMIZ LAZIM
                    Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("İzin Ver", View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) //İZİN İSTEYECEĞIZ
                    }
                    ).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES) //İZİN İSTEYECEĞIZ
                }
            } else {
                val intentToGallery = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ) //İZİN VERİLMİŞ GALERİYE GİDEBİLİRİZ
                activityResultLauncher.launch(intentToGallery)
            }

        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) { //İZİN VERİLMEMİŞ, İZİN İSTEMEMİZ LAZIM
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                ) {
                    //SNACKBAR GOSTERMEK LAZIM , KULLANICIDAN NEDEN IZIN ISTEDIGIMIZI BIR KEZ DAHA SOYLEYEREK IZIN ISTEMEMIZ LAZIM
                    Snackbar.make(
                        view,
                        "Galeriye ulaşıp görsel seçmemiz lazım!",
                        Snackbar.LENGTH_INDEFINITE
                    ).setAction("İzin Ver", View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) //İZİN İSTEYECEĞIZ
                    }
                    ).show()
                } else {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE) //İZİN İSTEYECEĞIZ
                }
            } else {
                val intentToGallery = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                ) //İZİN VERİLMİŞ GALERİYE GİDEBİLİRİZ
                activityResultLauncher.launch(intentToGallery)
            }
        }
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    val intentFromResult = result.data
                    try {
                        if (intentFromResult != null) {
                            secilenGorsel = intentFromResult.data
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(
                                    requireActivity().contentResolver,
                                    secilenGorsel!!
                                )
                                secilenBitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(secilenBitmap)
                            } else {
                                secilenBitmap = MediaStore.Images.Media.getBitmap(
                                    requireActivity().contentResolver,
                                    secilenGorsel
                                )
                                binding.imageView.setImageBitmap(secilenBitmap)
                            }
                        }
                    } catch (e: IOException) {
                        println(e.localizedMessage)
                    }
                }
            }


        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intentToGallery = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ) //izin verildi galeriye gidebiliriz
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    Toast.makeText(requireContext(), "İzin Verilmedi!", Toast.LENGTH_LONG)
                        .show() //izin verilmedi
                }
            }
    }

    private fun kucukBitmapOlustur(choosenBitmap: Bitmap, maximumBoyut: Int): Bitmap {
        var width = choosenBitmap.width
        var height = choosenBitmap.height

        val bitmapOrani: Double = width.toDouble() / height.toDouble()
        if (bitmapOrani > 1) {
            //gorsel yatay
            width = maximumBoyut
            val smallerHeight = width / bitmapOrani
            height = smallerHeight.toInt()
        } else {
            //gorsel dikey
            height = maximumBoyut
            val smallerWidth = height * bitmapOrani
            width = smallerWidth.toInt()
        }
        return Bitmap.createScaledBitmap(choosenBitmap, width, height, true)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }
}