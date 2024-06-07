package one.nem.kidshift.data.impl;

import com.github.javafaker.Faker;

import javax.inject.Inject;

import one.nem.kidshift.data.RewardData;

public class RewardDataDummyImpl implements RewardData {

    private Faker faker;

    @Inject
    public RewardDataDummyImpl() {
        faker = new Faker();
    }

    @Override
    public Integer getTotalReward() {
        return faker.number().numberBetween(0, 100000);
    }
}
