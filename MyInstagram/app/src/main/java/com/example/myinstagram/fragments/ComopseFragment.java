 package com.example.myinstagram.fragments;

 import android.content.Intent;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.net.Uri;
 import android.os.Bundle;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.core.content.FileProvider;
 import androidx.fragment.app.Fragment;

 import android.os.Environment;
 import android.provider.MediaStore;
 import android.util.Log;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.Toast;

 import com.example.myinstagram.MainActivity;
 import com.example.myinstagram.Post;
 import com.example.myinstagram.R;
 import com.parse.FindCallback;
 import com.parse.ParseException;
 import com.parse.ParseFile;
 import com.parse.ParseQuery;
 import com.parse.ParseUser;
 import com.parse.SaveCallback;

 import java.io.File;
 import java.util.List;

 import static android.app.Activity.RESULT_OK;
 import static android.content.ContentValues.TAG;

 /**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComopseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComopseFragment extends Fragment {

    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 77;
    private EditText etDescrip;
    private Button btnCaptureImage;
    private ImageView ivPostImage;
    private Button btnSubmit;


     private File photoFile;
     public String photoFileName = "photo.jpg";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComopseFragment() {
        // Required empty public constructor
    }

    public static ComopseFragment newInstance(String param1, String param2) {
        ComopseFragment fragment = new ComopseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comopse, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etDescrip = view.findViewById(R.id.etDescrip);
        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        ivPostImage = view.findViewById(R.id.ivPic);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCamera();
            }
        });

        // queryPosts();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = etDescrip.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(getContext(), "Descriptio cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivPostImage.getDrawable() == null){
                    Toast.makeText(getContext(), "There is no image", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile);
            }
        });
    }

     private void launchCamera() {
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         // Create a File reference for future access
         photoFile = getPhotoFileUri(photoFileName);

         // wrap File object into a content provider
         // required for API >= 24
         // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
         Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
         intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

         // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
         // So as long as the result is not null, it's safe to use the intent.
         if (intent.resolveActivity(getContext().getPackageManager()) != null) {
             // Start the image capture intent to take photo
             startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
         }
     }

     @Override
     public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
             if (resultCode == RESULT_OK) {
                 // by this point we have the camera photo on disk
                 Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                 // RESIZE BITMAP, see section below
                 // Load the taken image into a preview
                 ivPostImage.setImageBitmap(takenImage);
             } else { // Result was a failure
                 Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
             }
         }
     }

     // Returns the File for a photo stored on disk given the fileName
     public File getPhotoFileUri(String fileName) {
         // Get safe storage directory for photos
         // Use `getExternalFilesDir` on Context to access package-specific directories.
         // This way, we don't need to request external read/write runtime permissions.
         File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

         // Create the storage directory if it does not exist
         if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
             Log.d(TAG, "getPhotoFileUri: failed to create directory");
             Toast.makeText(getContext(),"Failed to save file", Toast.LENGTH_SHORT).show();
         }

         // Return the file target for the photo based on filename

         return new File(mediaStorageDir.getPath() + File.separator + fileName);
     }

     private void savePost(String description, ParseUser user, File photoFile) {
         Post post = new Post();
         post.setDescription(description);
         post.setImage(new ParseFile(photoFile));
         post.setUser(user);
         post.saveInBackground(new SaveCallback() {
             @Override
             public void done(ParseException e) {
                 if (e != null) {
                     Toast.makeText(getContext(), e.getLocalizedMessage(),
                             Toast.LENGTH_SHORT).show();
                 } else {
                     Toast.makeText(getContext(), "Posted successfully!",
                             Toast.LENGTH_SHORT).show();
                     etDescrip.setText("");
                     ivPostImage.setImageResource(0);
                 }
             }
         });
     }


}