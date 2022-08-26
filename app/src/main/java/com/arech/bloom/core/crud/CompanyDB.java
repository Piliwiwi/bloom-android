package com.arech.bloom.core.crud;

import com.arech.bloom.models.Company;
import com.arech.bloom.models.User;

import io.realm.Realm;

public class CompanyDB {
    //Create or Update
    public static void add(final Company company) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(company);
            }
        });
        realm.close();
    }

    //Get
    public static Company getMyCompany() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(Company.class).findFirst();
    }
}
