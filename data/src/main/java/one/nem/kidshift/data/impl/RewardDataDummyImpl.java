package one.nem.kidshift.data.impl;

import com.github.javafaker.Faker;

import javax.inject.Inject;

import one.nem.kidshift.data.RewardData;
import one.nem.kidshift.utils.KSLogger;

public class RewardDataDummyImpl implements RewardData {

    private Faker faker;

    @Inject
    KSLogger logger;

    @Inject
    public RewardDataDummyImpl() {
        faker = new Faker();
        //logger.setTag("RewardDataDummyImpl");

    }

    @Override
    public Integer getTotalReward() {
        //logger.info("getTotalReward called");
        Integer reward = faker.number().numberBetween(0, 10000);
        //logger.info("Returning reward: " + reward);
        return reward;
    }
}
