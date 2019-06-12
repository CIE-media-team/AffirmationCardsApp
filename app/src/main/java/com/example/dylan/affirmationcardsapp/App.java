package com.example.dylan.affirmationcardsapp;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class App extends Application {

    private static App app;
    private static Box<Card> cardBox;
    private static List<Card> initialCards;
    private BoxStore boxStore;

    public static App getApp() {
        return app;
    }

    public static List<Card> getInitialCards() {
        return initialCards;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;


        // Initialize the main data access object
        boxStore = MyObjectBox.builder().androidContext(App.this).build();

        // Get the wrapper (Box) for the Book table that lets us store Book objects
        cardBox = boxStore.boxFor(Card.class);
        //if the database hasn't been populated already, populate it
        //cardBox.removeAll();
        //Ask Dr. Layman if he has any ideas on how to code this more intellectually***
        if (cardBox.count() == 0) {
            setNotif();
            initialCards = new ArrayList<>();
            initialCards.add(new Card(R.drawable.card1));
            initialCards.add(new Card(R.drawable.card2));
            initialCards.add(new Card(R.drawable.card3));
            initialCards.add(new Card(R.drawable.card4));
            initialCards.add(new Card(R.drawable.card5));
            initialCards.add(new Card(R.drawable.card6));
            initialCards.add(new Card(R.drawable.card7));
            initialCards.add(new Card(R.drawable.card8));
            initialCards.add(new Card(R.drawable.card9));
            initialCards.add(new Card(R.drawable.card10));
            initialCards.add(new Card(R.drawable.card11));
            initialCards.add(new Card(R.drawable.card12));
            initialCards.add(new Card(R.drawable.card13));
            initialCards.add(new Card(R.drawable.card14));
            initialCards.add(new Card(R.drawable.card15));
            initialCards.add(new Card(R.drawable.card16));
            initialCards.add(new Card(R.drawable.card17));
            initialCards.add(new Card(R.drawable.card18));
            initialCards.add(new Card(R.drawable.card19));
            initialCards.add(new Card(R.drawable.card20));
            initialCards.add(new Card(R.drawable.card21));
            initialCards.add(new Card(R.drawable.card22));
            initialCards.add(new Card(R.drawable.card23));
            initialCards.add(new Card(R.drawable.card24));
            initialCards.add(new Card(R.drawable.card25));
            initialCards.add(new Card(R.drawable.card26));
            initialCards.add(new Card(R.drawable.card27));
            initialCards.add(new Card(R.drawable.card28));
            initialCards.add(new Card(R.drawable.card29));
            initialCards.add(new Card(R.drawable.card30));
            initialCards.add(new Card(R.drawable.card31));
            initialCards.add(new Card(R.drawable.card32));
            initialCards.add(new Card(R.drawable.card33));
            initialCards.add(new Card(R.drawable.card34));
            initialCards.add(new Card(R.drawable.card35));
            initialCards.add(new Card(R.drawable.card36));
            initialCards.add(new Card(R.drawable.card37));
            initialCards.add(new Card(R.drawable.card38));
            initialCards.add(new Card(R.drawable.card39));
            initialCards.add(new Card(R.drawable.card40));
            initialCards.add(new Card(R.drawable.card41));
            initialCards.add(new Card(R.drawable.card42));
            initialCards.add(new Card(R.drawable.card43));
            initialCards.add(new Card(R.drawable.card44));
            initialCards.add(new Card(R.drawable.card45));
            initialCards.add(new Card(R.drawable.card46));
            initialCards.add(new Card(R.drawable.card47));
            initialCards.add(new Card(R.drawable.card48));
            initialCards.add(new Card(R.drawable.card49));
            initialCards.add(new Card(R.drawable.card50));
            initialCards.add(new Card(R.drawable.card51));
            initialCards.add(new Card(R.drawable.card52));
            initialCards.add(new Card(R.drawable.card53));
            initialCards.add(new Card(R.drawable.card54));
            initialCards.add(new Card(R.drawable.card55));
            initialCards.add(new Card(R.drawable.card56));


            // ObjectBox is smart enough to handle CRUD on Collections of entities
            cardBox.put(initialCards);


        }


    }

    public void setNotif() {
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("YOUR_CHANNEL_ID",
//                    "YOUR_CHANNEL_NAME",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
//            mNotificationManager.createNotificationChannel(channel);
//        }
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "YOUR_CHANNEL_ID")
//                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
//                .setContentTitle("Fertile Affirmations") // title for notification
//                .setContentText("Your daily affirmation is here!")// message for notification
//                .setAutoCancel(true); // clear notification after click
//        Intent intent = new Intent(getApplicationContext(), CardActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pi);
//        mNotificationManager.notify(0, mBuilder.build());


//        Calendar calendar = Calendar.getInstance();
//
//        calendar.set(Calendar.HOUR_OF_DAY,15);
//        calendar.set(Calendar.MINUTE,32);
//
//        Intent intent = new Intent(getApplicationContext(),Notification_receiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    public BoxStore getBoxStore() {
        return boxStore;
    }

}
