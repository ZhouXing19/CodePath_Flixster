package com.codepath.apps.restclienttemplate;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.codepath.apps.restclienttemplate.models.SampleModelDao;
import com.codepath.apps.restclienttemplate.models.SampleModelDao_Impl;
import com.codepath.apps.restclienttemplate.models.TweetDao;
import com.codepath.apps.restclienttemplate.models.TweetDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDatabase_Impl extends MyDatabase {
  private volatile SampleModelDao _sampleModelDao;

  private volatile TweetDao _tweetDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SampleModel` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Tweet` (`body` TEXT, `id` INTEGER NOT NULL, `createdAt` TEXT, `userId` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`userId`) REFERENCES `User`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `User` (`id` INTEGER NOT NULL, `name` TEXT, `screenName` TEXT, `profileImageUrl` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '22a1da6a2538cc296f722d2f0c4330ff')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `SampleModel`");
        _db.execSQL("DROP TABLE IF EXISTS `Tweet`");
        _db.execSQL("DROP TABLE IF EXISTS `User`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSampleModel = new HashMap<String, TableInfo.Column>(2);
        _columnsSampleModel.put("id", new TableInfo.Column("id", "INTEGER", false, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSampleModel.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSampleModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSampleModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSampleModel = new TableInfo("SampleModel", _columnsSampleModel, _foreignKeysSampleModel, _indicesSampleModel);
        final TableInfo _existingSampleModel = TableInfo.read(_db, "SampleModel");
        if (! _infoSampleModel.equals(_existingSampleModel)) {
          return new RoomOpenHelper.ValidationResult(false, "SampleModel(com.codepath.apps.restclienttemplate.models.SampleModel).\n"
                  + " Expected:\n" + _infoSampleModel + "\n"
                  + " Found:\n" + _existingSampleModel);
        }
        final HashMap<String, TableInfo.Column> _columnsTweet = new HashMap<String, TableInfo.Column>(4);
        _columnsTweet.put("body", new TableInfo.Column("body", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTweet.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTweet.put("createdAt", new TableInfo.Column("createdAt", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTweet.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTweet = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysTweet.add(new TableInfo.ForeignKey("User", "NO ACTION", "NO ACTION",Arrays.asList("userId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTweet = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTweet = new TableInfo("Tweet", _columnsTweet, _foreignKeysTweet, _indicesTweet);
        final TableInfo _existingTweet = TableInfo.read(_db, "Tweet");
        if (! _infoTweet.equals(_existingTweet)) {
          return new RoomOpenHelper.ValidationResult(false, "Tweet(com.codepath.apps.restclienttemplate.models.Tweet).\n"
                  + " Expected:\n" + _infoTweet + "\n"
                  + " Found:\n" + _existingTweet);
        }
        final HashMap<String, TableInfo.Column> _columnsUser = new HashMap<String, TableInfo.Column>(4);
        _columnsUser.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("screenName", new TableInfo.Column("screenName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUser.put("profileImageUrl", new TableInfo.Column("profileImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUser = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUser = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUser = new TableInfo("User", _columnsUser, _foreignKeysUser, _indicesUser);
        final TableInfo _existingUser = TableInfo.read(_db, "User");
        if (! _infoUser.equals(_existingUser)) {
          return new RoomOpenHelper.ValidationResult(false, "User(com.codepath.apps.restclienttemplate.models.User).\n"
                  + " Expected:\n" + _infoUser + "\n"
                  + " Found:\n" + _existingUser);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "22a1da6a2538cc296f722d2f0c4330ff", "3dfdd47c6534712bc6f8c8e99b8d79d9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "SampleModel","Tweet","User");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `SampleModel`");
      _db.execSQL("DELETE FROM `Tweet`");
      _db.execSQL("DELETE FROM `User`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SampleModelDao sampleModelDao() {
    if (_sampleModelDao != null) {
      return _sampleModelDao;
    } else {
      synchronized(this) {
        if(_sampleModelDao == null) {
          _sampleModelDao = new SampleModelDao_Impl(this);
        }
        return _sampleModelDao;
      }
    }
  }

  @Override
  public TweetDao tweetDao() {
    if (_tweetDao != null) {
      return _tweetDao;
    } else {
      synchronized(this) {
        if(_tweetDao == null) {
          _tweetDao = new TweetDao_Impl(this);
        }
        return _tweetDao;
      }
    }
  }
}
