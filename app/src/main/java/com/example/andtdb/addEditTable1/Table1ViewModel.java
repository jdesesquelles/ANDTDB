package com.example.andtdb.addEditTable1;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.andtdb.data.Table1;
import com.example.andtdb.data.Table1Repository;
import java.util.List;

public class Table1ViewModel extends AndroidViewModel {

        private Table1Repository mRepository;
        private final LiveData<List<Table1>> mAllTable1;

        public Table1ViewModel (Application application) {
            super(application);
            mRepository = new Table1Repository(application);
            mAllTable1 = mRepository.getAllTable1();
        }

        public LiveData<List<Table1>> getAllTable1() { return mAllTable1; }

        public void insert(Table1 word) { mRepository.insertTable1(word); }

}
