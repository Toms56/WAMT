package dao;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import com.wamt.data.WamtDatabase;
import com.wamt.data.entity.UserEntity;
import testUtil.LiveDataTestUtil;


@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private WamtDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                WamtDatabase.class)
                .allowMainThreadQueries()
                .build();
    }


    @After
    public void closeDb(){
        database.close();
    }


    @Test
    public void testShouldInsertUserIntoDatabaseSuccessfully() throws InterruptedException{
        // Given
        UserEntity user = new UserEntity("Test1");

        // When
        database.userDao().insertUser(user);

        // Then
        UserEntity insertedUser =
                LiveDataTestUtil.getOrAwaitValue(
                        database.userDao().getUserById(1)
                );

        assertEquals("Test1", insertedUser.getPseudo());
    }

    @Test
    public void testGetAllUsersShouldReturnEmptyList()
            throws InterruptedException {

        final List<UserEntity> userEntities = LiveDataTestUtil.getOrAwaitValue(database.userDao().getAllUsers());
        assertTrue("Retrieved list of users must be empty", userEntities.isEmpty());

    }

    @Test
    public void testGetAllUsersShouldReturnNonEmptyList()
            throws InterruptedException {

        // Given
        final List<UserEntity> users = new ArrayList<>();
        UserEntity user1 = new UserEntity("Jean");
        UserEntity user2 = new UserEntity("Patrick");
        users.add(user1);
        users.add(user2);

        for (final UserEntity user : users){
            database.userDao().insertUser(user);
        }

        //When
        final List<UserEntity> retrievedUsers = LiveDataTestUtil.getOrAwaitValue(database.userDao().getAllUsers());

        // Then
        assertEquals("Insert and retrieve list must have the same size",users.size(), retrievedUsers.size());
    }


}