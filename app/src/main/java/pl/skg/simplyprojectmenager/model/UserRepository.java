package pl.skg.simplyprojectmenager.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by RENT on 2017-05-24.
 */

public class UserRepository {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    public void saveUser(User user) {
        myRef = database.getReference("usersi");
        String key = user.getEmail();
        myRef.child(key).setValue(user);
    }

    public User getUser(String userEmail) {
        myRef = database.getReference("usersi");
        final User[] user = new User[1];


            myRef.child(userEmail).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.i("TAG", dataSnapshot.getChildrenCount() + "");
                    user[0] = dataSnapshot.getValue(User.class);
                    user[0].setEmail(dataSnapshot.getKey());
                    System.out.println(user[0].getUserName());
                    System.out.println(user[0].getId());
                    System.out.println(user[0].getPassword());
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("TAG", "Value is: " + value);
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Error");
                }
            });
        return user[0];
    }
}
