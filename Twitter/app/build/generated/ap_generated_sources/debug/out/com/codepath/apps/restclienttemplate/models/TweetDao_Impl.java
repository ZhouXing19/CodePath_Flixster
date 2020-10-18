package com.codepath.apps.restclienttemplate.models;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TweetDao_Impl implements TweetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Tweet> __insertionAdapterOfTweet;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  public TweetDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTweet = new EntityInsertionAdapter<Tweet>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Tweet` (`body`,`id`,`createdAt`,`userId`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Tweet value) {
        if (value.body == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.body);
        }
        stmt.bindLong(2, value.id);
        if (value.createdAt == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.createdAt);
        }
        stmt.bindLong(4, value.userId);
      }
    };
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `User` (`id`,`name`,`screenName`,`profileImageUrl`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.id);
        if (value.name == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.name);
        }
        if (value.screenName == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.screenName);
        }
        if (value.profileImageUrl == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.profileImageUrl);
        }
      }
    };
  }

  @Override
  public void insertModel(final Tweet... tweets) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTweet.insert(tweets);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertModel(final User... users) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TweetWIthUser> recentItems() {
    final String _sql = "SELECT Tweet.body AS tweet_body, Tweet.createdAt AS tweet_createdAt, Tweet.id AS tweet_id, User.* FROM Tweet INNER JOIN User ON Tweet.userId = User.id ORDER BY createdAt DESC LIMIT 300 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfBody = CursorUtil.getColumnIndexOrThrow(_cursor, "tweet_body");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "tweet_createdAt");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "tweet_id");
      final int _cursorIndexOfId_1 = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfScreenName = CursorUtil.getColumnIndexOrThrow(_cursor, "screenName");
      final int _cursorIndexOfProfileImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUrl");
      final List<TweetWIthUser> _result = new ArrayList<TweetWIthUser>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final TweetWIthUser _item;
        final Tweet _tmpTweet;
        if (! (_cursor.isNull(_cursorIndexOfBody) && _cursor.isNull(_cursorIndexOfCreatedAt) && _cursor.isNull(_cursorIndexOfId))) {
          _tmpTweet = new Tweet();
          _tmpTweet.body = _cursor.getString(_cursorIndexOfBody);
          _tmpTweet.createdAt = _cursor.getString(_cursorIndexOfCreatedAt);
          _tmpTweet.id = _cursor.getLong(_cursorIndexOfId);
        }  else  {
          _tmpTweet = null;
        }
        final User _tmpUser;
        if (! (_cursor.isNull(_cursorIndexOfId_1) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfScreenName) && _cursor.isNull(_cursorIndexOfProfileImageUrl))) {
          _tmpUser = new User();
          _tmpUser.id = _cursor.getLong(_cursorIndexOfId_1);
          _tmpUser.name = _cursor.getString(_cursorIndexOfName);
          _tmpUser.screenName = _cursor.getString(_cursorIndexOfScreenName);
          _tmpUser.profileImageUrl = _cursor.getString(_cursorIndexOfProfileImageUrl);
        }  else  {
          _tmpUser = null;
        }
        _item = new TweetWIthUser();
        _item.tweet = _tmpTweet;
        _item.user = _tmpUser;
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
