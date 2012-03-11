package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JfDbAdapter {
	
	//DB-Spalten
	public final static String UID = "_uid";
	public final static String EMAIL = "email";
	public final static String PASSWD = "passwd";

	private final static String TAG = "JfDbAdapter";
	private final static String DB_NAME = "jfDB";
	private final static String DB_TABLE = "user";
	private final static int DB_VERSION = 1;
	
	//String erzeugt SQLite Tabelle
	public static final String DB_CREATE = "create table "+ DB_TABLE + "( " +
			UID + " integer primary key autoincrement, " +
			EMAIL + " text not null, " +
			PASSWD + " text not null);";
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context ctx;
	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		
		DatabaseHelper(Context context){
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(DB_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			Log.w(TAG, "upgrade DB from Version " + oldVersion + " to " + newVersion);
			db.execSQL("drop table if exists " + DB_TABLE);
			onCreate(db);
		}
	}
	
	public JfDbAdapter(Context ctx){
		this.ctx = ctx;
	}
	
	public JfDbAdapter open() throws SQLException{
		mDbHelper = new DatabaseHelper(ctx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		mDb.close();
	}
	
	public long create(String email, String passwd){
		
		ContentValues cv = new ContentValues();
		cv.put(EMAIL, email);
		cv.put(PASSWD, passwd);
		return mDb.insert(DB_TABLE, null, cv);
	}
	
	public Cursor fetchUser(String email){
		Cursor cur = mDb.query(true, DB_TABLE, new String[]{UID, EMAIL, PASSWD}, EMAIL + " = " + email, null, null, null, null, null);
		if(cur != null){
			cur.moveToFirst();
		}
		return cur;
	}
}