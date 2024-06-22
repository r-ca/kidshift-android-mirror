package one.nem.kidshift.data.room;

import android.content.Context;

import androidx.room.Room;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class KidShiftDatabaseModule {

    @Provides
    public static KidShiftDatabase provideKidShiftDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context,
                KidShiftDatabase.class,
                "cache.db")
                .fallbackToDestructiveMigration() // DEBUG_ONLY Migrationがない場合に破壊的なマイグレーションを行うことを許可
                .fallbackToDestructiveMigrationOnDowngrade() // DEBUG_ONLY ダウングレード時に破壊的なマイグレーションを行うことを許可
                .build();
    }
}
